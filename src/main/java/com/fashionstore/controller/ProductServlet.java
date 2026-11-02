package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.ArrayList;
@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAOImpl();
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔹 GET PARAMETERS
        String keyword = request.getParameter("keyword");
        String categoryIdStr = request.getParameter("categoryId");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String sort = request.getParameter("sort");
        String category =
                request.getParameter("category");

        // 🔹 CONVERT VALUES
        Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty())
                ? Integer.parseInt(categoryIdStr) : null;

        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty())
                ? Double.parseDouble(minPriceStr) : null;

        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty())
                ? Double.parseDouble(maxPriceStr) : null;

        // 🔹 START WITH ALL PRODUCTS
        List<Product> products;

        if (category != null &&
            !category.isEmpty()) {

        	try {

        	    products =
        	        productDAO.getProductsByCategory(category);

        	} catch (Exception e) {

        	    e.printStackTrace();

        	    products =
        	        new ArrayList<>();
        	}

        } else {

            products =
                productDAO.getAllProducts();
        }
        // 🔥 APPLY FILTERS STEP-BY-STEP (IMPORTANT)

        // 1. KEYWORD FILTER
        if (keyword != null && !keyword.trim().isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // 2. CATEGORY FILTER
        if (categoryId != null) {
            products = products.stream()
                    .filter(p -> p.getCategoryId() == categoryId)
                    .collect(Collectors.toList());
        }

        // 3. MIN PRICE
        if (minPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice().doubleValue() >= minPrice)
                    .collect(Collectors.toList());
        }

        // 4. MAX PRICE
        if (maxPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice().doubleValue() <= maxPrice)
                    .collect(Collectors.toList());
        }

        // 🔥 SORTING
        if ("low".equals(sort)) {
            products.sort((a, b) -> a.getPrice().compareTo(b.getPrice()));
        } else if ("high".equals(sort)) {
            products.sort((a, b) -> b.getPrice().compareTo(a.getPrice()));
        }

        // 🔹 LOAD CATEGORIES
        List<Category> categories = categoryDAO.getAllCategories();

        // 🔹 SEND DATA TO JSP
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("/WEB-INF/views/products.jsp")
               .forward(request, response);
    }
}