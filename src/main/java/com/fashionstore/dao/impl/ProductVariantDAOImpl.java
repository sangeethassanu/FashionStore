package com.fashionstore.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    // ================= SQL =================

    private static final String INSERT_PRODUCT_VARIANT_SQL =
            "INSERT INTO product_variants (product_id, size, color, stock_quantity) VALUES (?, ?, ?, ?)";

    private static final String GET_PRODUCT_VARIANT_BY_ID_SQL =
            "SELECT * FROM product_variants WHERE variant_id = ?";

    private static final String GET_VARIANTS_BY_PRODUCT_ID_SQL =
            "SELECT * FROM product_variants WHERE product_id = ? ORDER BY variant_id DESC";

    private static final String UPDATE_PRODUCT_VARIANT_SQL =
            "UPDATE product_variants SET product_id=?, size=?, color=?, stock_quantity=? WHERE variant_id=?";

    private static final String DELETE_PRODUCT_VARIANT_SQL =
            "DELETE FROM product_variants WHERE variant_id=?";

    private static final String GET_ALL_PRODUCT_VARIANTS_SQL =
            "SELECT * FROM product_variants ORDER BY variant_id DESC";

    // ================= INSERT =================
    @Override
    public boolean addProductVariant(ProductVariant productVariant) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_PRODUCT_VARIANT_SQL)) {

            ps.setInt(1, productVariant.getProductId());
            ps.setString(2, productVariant.getSize());
            ps.setString(3, productVariant.getColor());
            ps.setInt(4, productVariant.getStockQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= GET BY ID =================
    @Override
    public ProductVariant getVariantById(int variantId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_PRODUCT_VARIANT_BY_ID_SQL)) {

            ps.setInt(1, variantId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToProductVariant(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= GET BY PRODUCT =================
    @Override
    public List<ProductVariant> getVariantsByProductId(int productId) {
        List<ProductVariant> variants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_VARIANTS_BY_PRODUCT_ID_SQL)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                variants.add(mapResultSetToProductVariant(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variants;
    }

    // ================= GET ALL =================
    @Override
    public List<ProductVariant> getAllVariants() {
        List<ProductVariant> variants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_ALL_PRODUCT_VARIANTS_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                variants.add(mapResultSetToProductVariant(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variants;
    }

    // ================= UPDATE =================
    @Override
    public boolean updateProductVariant(ProductVariant productVariant) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_PRODUCT_VARIANT_SQL)) {

            ps.setInt(1, productVariant.getProductId());
            ps.setString(2, productVariant.getSize());
            ps.setString(3, productVariant.getColor());
            ps.setInt(4, productVariant.getStockQuantity());
            ps.setInt(5, productVariant.getVariantId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= DELETE =================
    @Override
    public boolean deleteProductVariant(int variantId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_PRODUCT_VARIANT_SQL)) {

            ps.setInt(1, variantId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= MAPPER =================
    private ProductVariant mapResultSetToProductVariant(ResultSet rs) throws Exception {
        ProductVariant variant = new ProductVariant();

        variant.setVariantId(rs.getInt("variant_id"));
        variant.setProductId(rs.getInt("product_id"));
        variant.setSize(rs.getString("size"));
        variant.setColor(rs.getString("color"));
        variant.setStockQuantity(rs.getInt("stock_quantity"));

        return variant;
    }

    // ================= OPTIONAL =================
    @Override
    public ProductVariant getVariantByProductIdAndSize(int productId, String size) {
        return null;
    }

    @Override
    public boolean updateStock(int variantId, int stockQuantity) {
        return false;
    }

    @Override
    public boolean isStockAvailable(int variantId, int requiredQuantity) {
        return false;
    }

    @Override
    public List<ProductVariant> getFilteredProducts(Integer categoryId, String keyword, BigDecimal minPrice) {
        return null;
    }

    @Override
    public List<ProductVariant> getRelatedProducts(int categoryId, int excludeProductId, int limit) {
        return null;
    }
}