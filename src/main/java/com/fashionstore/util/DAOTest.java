package com.fashionstore.util;

import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductVariant;

public class DAOTest {

    public static void main(String[] args) {

        CategoryDAO categoryDAO = new CategoryDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();

        // ================= CATEGORY TEST =================
        System.out.println("========= CATEGORY TEST =========");
        List<Category> categories = categoryDAO.getAllCategories();

        for (Category category : categories) {
            System.out.println(
                category.getCategoryId() + " | " +
                category.getCategoryName() + " | " +
                category.getDescription()
            );
        }

        // ================= PRODUCT TEST =================
        System.out.println("\n========= PRODUCT TEST =========");
        List<Product> products = productDAO.getAllActiveProducts();

        for (Product product : products) {
            System.out.println(
                product.getProductId() + " | " +
                product.getProductName() + " | " +
                product.getBrand() + " | " +
                product.getPrice() + " | " +
                product.getCategoryId()
            );
        }

        // ================= PRODUCT BY CATEGORY TEST =================
        System.out.println("\n========= PRODUCT BY CATEGORY TEST (MEN = 1) =========");
        List<Product> menProducts = productDAO.getProductsByCategory(1);

        for (Product product : menProducts) {
            System.out.println(
                product.getProductId() + " | " +
                product.getProductName() + " | " +
                product.getPrice()
            );
        }

        // ================= SEARCH TEST =================
        System.out.println("\n========= SEARCH TEST ('shirt') =========");
        List<Product> searchedProducts = productDAO.searchProducts("shirt");

        for (Product product : searchedProducts) {
            System.out.println(
                product.getProductId() + " | " +
                product.getProductName() + " | " +
                product.getPrice()
            );
        }

        // ================= PRODUCT VARIANT TEST =================
        System.out.println("\n========= PRODUCT VARIANT TEST (PRODUCT ID = 1) =========");
        List<ProductVariant> variants = productVariantDAO.getVariantsByProductId(1);

        for (ProductVariant variant : variants) {
            System.out.println(
                variant.getVariantId() + " | " +
                variant.getProductId() + " | " +
                variant.getSize() + " | " +
                variant.getStockQuantity()
            );
        }
        // ================= STOCK CHECK TEST =================
        System.out.println("\n========= STOCK CHECK TEST =========");
        boolean isAvailable = productVariantDAO.isStockAvailable(2, 3);
        System.out.println("Variant ID 2 has stock for quantity 3: " + isAvailable);
    }
}