package com.fashionstore.dao;

import java.math.BigDecimal;
import java.util.List;
import com.fashionstore.model.Product;

public interface ProductDAO {

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    Product getProductById(int productId);

    List<Product> getAllProducts();

    List<Product> getAllActiveProducts();

    List<Product> getProductsByCategory(int categoryId);

    List<Product> searchProducts(String keyword);

    List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> searchProductsByCategory(String keyword, int categoryId);
    List<Product> getProductsSortedByPriceAsc();
    List<Product> getProductsByCategory(String category)
            throws Exception;
    List<Product> getProductsSortedByPriceDesc();

    List<Product> getLatestProducts(int limit);

    List<Product> getFilteredProducts(Integer categoryId, String keyword, BigDecimal minPrice,BigDecimal maxPrice);

    List<Product> getRelatedProducts(int categoryId, int excludeProductId, int limit);
}
