package com.fashionstore.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

public  class ProductDAOImpl implements ProductDAO {

    // ---------------- SQL ----------------
	private static final String INSERT_PRODUCT_SQL =
	        "INSERT INTO products (category_id, product_name, brand, description, price, image_url, is_active) " +
	        "VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_PRODUCT_SQL =
	        "UPDATE products SET category_id = ?, product_name = ?, brand = ?, description = ?, " +
	        "price = ?, image_url = ?, is_active = ? WHERE product_id = ?";

	private static final String DELETE_PRODUCT_SQL =
	        "DELETE FROM products WHERE product_id = ?";

	private static final String GET_PRODUCT_BY_ID_SQL =
	        "SELECT * FROM products WHERE product_id = ?";

	private static final String GET_ALL_PRODUCTS_SQL =
	        "SELECT * FROM products ORDER BY product_id DESC";

	private static final String GET_ALL_ACTIVE_PRODUCTS_SQL =
	        "SELECT * FROM products WHERE is_active = TRUE ORDER BY product_id DESC";

    // ---------------- ADD ----------------
    @Override
    public boolean addProduct(Product product) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PRODUCT_SQL)) {

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getBrand());
            ps.setString(4, product.getDescription());
            ps.setBigDecimal(5, product.getPrice());
            ps.setString(6, product.getImageUrl());
            ps.setBoolean(7, product.isActive());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- UPDATE ----------------
    @Override
    public boolean updateProduct(Product product) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PRODUCT_SQL)) {

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getBrand());
            ps.setString(4, product.getDescription());
            ps.setBigDecimal(5, product.getPrice());
            ps.setString(6, product.getImageUrl());
            ps.setBoolean(7, product.isActive());
            ps.setInt(8, product.getProductId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- DELETE ----------------
    @Override
    public boolean deleteProduct(int productId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_PRODUCT_SQL)) {

            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- GET BY ID ----------------
    @Override
    public Product getProductById(int productId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_PRODUCT_BY_ID_SQL)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- GET ALL ----------------
    @Override
    public List<Product> getAllProducts() {
        return executeQuery(GET_ALL_PRODUCTS_SQL);
    }

    // ---------------- GET ALL ACTIVE ----------------
    @Override
    public List<Product> getAllActiveProducts() {
        return executeQuery(GET_ALL_ACTIVE_PRODUCTS_SQL);
    }

    // ---------------- CATEGORY ----------------
    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql =
                "SELECT * FROM products " +
                "WHERE category_id = ? " +
                "ORDER BY product_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------- SEARCH ----------------
    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();

        String sql =
                "SELECT * FROM products " +
                "WHERE product_name LIKE ? OR brand LIKE ? OR description LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------- PRICE RANGE ----------------
    @Override
    public List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, min);
            ps.setBigDecimal(2, max);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------- SORT ----------------
    @Override
    public List<Product> getProductsSortedByPriceAsc() {
        return executeQuery("SELECT * FROM products WHERE is_active = TRUE ORDER BY price ASC");
    }

    @Override
    public List<Product> getProductsSortedByPriceDesc() {
        return executeQuery("SELECT * FROM products WHERE is_active = TRUE ORDER BY price DESC");
    }

    // ---------------- LATEST ----------------
    @Override
    public List<Product> getLatestProducts(int limit) {
        List<Product> list = new ArrayList<>();

        String sql =
                "SELECT * FROM products " +
                "WHERE is_active = TRUE " +
                "ORDER BY created_at DESC " +
                "LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------- FILTER ----------------
    @Override
    public List<Product> searchProductsByCategory(String keyword, int categoryId) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE is_active = true " +
                     "AND LOWER(product_name) LIKE ? AND category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ps.setInt(2, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setBrand(rs.getString("brand"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategoryId(rs.getInt("category_id"));

                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
    
    @Override
    public List<Product> getProductsByCategory(String category)
            throws Exception {

        List<Product> products =
                new ArrayList<>();
        int categoryId = 0;

        switch(category.toLowerCase()) {

            case "men":
                categoryId = 1;
                break;

            case "women":
                categoryId = 2;
                break;

            case "kids":
                categoryId = 3;
                break;

            case "accessories":
                categoryId = 4;
                break;
        }
        String sql =
                "SELECT * FROM products WHERE category_id = ?";

        Connection conn =
                DBConnection.getConnection();

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, categoryId);

        ResultSet rs =
                ps.executeQuery();

        while (rs.next()) {

        	Product product = new Product();

        	product.setProductId(
        	        rs.getInt("product_id"));

        	product.setCategoryId(
        	        rs.getInt("category_id"));

        	product.setProductName(
        	        rs.getString("product_name"));

        	product.setBrand(
        	        rs.getString("brand"));

        	product.setDescription(
        	        rs.getString("description"));

        	product.setPrice(
        	        rs.getBigDecimal("price"));

        	product.setImageUrl(
        	        rs.getString("image_url"));

        	product.setActive(
        	        rs.getBoolean("is_active"));

        	product.setCreatedAt(
        	        rs.getTimestamp("created_at"));

        	products.add(product);
        	
        }

        return products;
    }
    
    public List<Product> getFilteredProducts(Integer categoryId, String keyword,
                                             BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE is_active = TRUE");
        List<Object> params = new ArrayList<>();

        if (categoryId != null) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (product_name LIKE ? OR brand LIKE ?)");
            String key = "%" + keyword + "%";
            params.add(key);
            params.add(key);
        }

        if (minPrice != null && maxPrice != null) {
            sql.append(" AND price BETWEEN ? AND ?");
            params.add(minPrice);
            params.add(maxPrice);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------- RELATED ----------------
    @Override
    public List<Product> getRelatedProducts(int categoryId, int excludeProductId, int limit) {
        List<Product> list = new ArrayList<>();

        String sql =
                "SELECT * FROM products " +
                "WHERE category_id = ? " +
                "AND product_id <> ? " +
                "AND is_active = TRUE " +
                "ORDER BY created_at DESC " +
                "LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setInt(2, excludeProductId);
            ps.setInt(3, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------- COMMON HELPERS ----------------
    private List<Product> executeQuery(String sql) {
        List<Product> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Product map(ResultSet rs) throws Exception {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setProductName(rs.getString("product_name"));
        p.setBrand(rs.getString("brand"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setImageUrl(rs.getString("image_url"));
        p.setActive(rs.getBoolean("is_active"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        return p;
    }
}