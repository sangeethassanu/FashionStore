package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== OPTIONAL: CHECK LOGIN SESSION =====
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // ===== FETCH DATA =====
            List<Category> categories = categoryDAO.getAllCategories();
            List<Product> latestProducts = productDAO.getLatestProducts(8);

            // ===== NULL SAFETY =====
            if (categories == null) {
                categories = List.of();
            }

            if (latestProducts == null) {
                latestProducts = List.of();
            }

            // ===== SET ATTRIBUTES =====
            request.setAttribute("categories", categories);
            request.setAttribute("latestProducts", latestProducts);

            // ===== FORWARD TO JSP =====
            request.getRequestDispatcher("/WEB-INF/views/home.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            // ===== ERROR HANDLING =====
            request.setAttribute("error", "Something went wrong while loading home page");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp")
                   .forward(request, response);
        }
    }
}