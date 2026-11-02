package com.fashionstore.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    // ================= SQL QUERIES =================

    private static final String INSERT_CART_ITEM_SQL =
            "INSERT INTO cart_items " +
            "(cart_id, variant_id, quantity) " +
            "VALUES (?, ?, ?)";

    private static final String UPDATE_CART_ITEM_QUANTITY_SQL =
            "UPDATE cart_items " +
            "SET quantity = ? " +
            "WHERE cart_item_id = ?";

    private static final String UPDATE_CART_ITEM_BY_CART_AND_VARIANT_SQL =
            "UPDATE cart_items " +
            "SET quantity = ? " +
            "WHERE cart_id = ? " +
            "AND variant_id = ?";

    private static final String DELETE_CART_ITEM_SQL =
            "DELETE FROM cart_items " +
            "WHERE cart_item_id = ?";

    private static final String DELETE_CART_ITEM_BY_CART_AND_VARIANT_SQL =
            "DELETE FROM cart_items " +
            "WHERE cart_id = ? " +
            "AND variant_id = ?";

    private static final String GET_CART_ITEM_BY_ID_SQL =
            "SELECT * FROM cart_items " +
            "WHERE cart_item_id = ?";

    private static final String GET_CART_ITEM_BY_CART_AND_VARIANT_SQL =
            "SELECT * FROM cart_items " +
            "WHERE cart_id = ? " +
            "AND variant_id = ?";

    private static final String CLEAR_CART_SQL =
            "DELETE FROM cart_items " +
            "WHERE cart_id = ?";

    private static final String GET_CART_ITEM_COUNT_SQL =
            "SELECT COUNT(*) AS item_count " +
            "FROM cart_items " +
            "WHERE cart_id = ?";

    private static final String GET_CART_TOTAL_SQL =
            "SELECT SUM(p.price * ci.quantity) AS total " +
            "FROM cart_items ci " +
            "JOIN product_variants pv " +
            "ON ci.variant_id = pv.variant_id " +
            "JOIN products p " +
            "ON pv.product_id = p.product_id " +
            "WHERE ci.cart_id = ?";

    // ================= IMPORTANT QUERY =================

    private static final String GET_CART_ITEMS_WITH_PRODUCT_SQL =

            "SELECT " +

            "ci.cart_item_id, " +
            "ci.cart_id, " +
            "ci.variant_id, " +
            "ci.quantity, " +

            "p.product_id, " +
            "p.product_name, " +
            "p.price, " +
            "p.image_url " +

            "FROM cart_items ci " +

            "JOIN product_variants pv " +
            "ON ci.variant_id = pv.variant_id " +

            "JOIN products p " +
            "ON pv.product_id = p.product_id " +

            "WHERE ci.cart_id = ?";

    // ================= ADD CART ITEM =================

    @Override
    public boolean addCartItem(CartItem cartItem) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            INSERT_CART_ITEM_SQL)
        ) {

            ps.setInt(1, cartItem.getCartId());

            ps.setInt(2, cartItem.getVariantId());

            ps.setInt(3, cartItem.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= GET CART ITEMS =================

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {

        List<CartItem> list =
                new ArrayList<>();

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            GET_CART_ITEMS_WITH_PRODUCT_SQL)
        ) {

            ps.setInt(1, cartId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                CartItem item =
                        new CartItem();

                item.setCartItemId(
                        rs.getInt("cart_item_id"));

                item.setCartId(
                        rs.getInt("cart_id"));

                item.setVariantId(
                        rs.getInt("variant_id"));

                item.setQuantity(
                        rs.getInt("quantity"));

                // ================= PRODUCT DATA =================

                item.setProductId(
                        rs.getInt("product_id"));

                item.setProductName(
                        rs.getString("product_name"));

                item.setPrice(
                        rs.getDouble("price"));

                item.setImageUrl(
                        rs.getString("image_url"));

                list.add(item);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }

    // ================= UPDATE QUANTITY =================

    @Override
    public boolean updateCartItemQuantity(
            int cartItemId,
            int quantity) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            UPDATE_CART_ITEM_QUANTITY_SQL)
        ) {

            ps.setInt(1, quantity);

            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= UPDATE BY CART + VARIANT =================

    @Override
    public boolean updateCartItemQuantityByCartAndVariant(
            int cartId,
            int variantId,
            int quantity) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            UPDATE_CART_ITEM_BY_CART_AND_VARIANT_SQL)
        ) {

            ps.setInt(1, quantity);

            ps.setInt(2, cartId);

            ps.setInt(3, variantId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= REMOVE =================

    @Override
    public boolean removeCartItem(int cartItemId) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            DELETE_CART_ITEM_SQL)
        ) {

            ps.setInt(1, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean removeCartItemByCartAndVariant(
            int cartId,
            int variantId) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            DELETE_CART_ITEM_BY_CART_AND_VARIANT_SQL)
        ) {

            ps.setInt(1, cartId);

            ps.setInt(2, variantId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= GET ITEM =================

    @Override
    public CartItem getCartItemById(int cartItemId) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            GET_CART_ITEM_BY_ID_SQL)
        ) {

            ps.setInt(1, cartItemId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapResultSetToCartItem(rs);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CartItem getCartItemByCartIdAndVariantId(
            int cartId,
            int variantId) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            GET_CART_ITEM_BY_CART_AND_VARIANT_SQL)
        ) {

            ps.setInt(1, cartId);

            ps.setInt(2, variantId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapResultSetToCartItem(rs);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // ================= CLEAR CART =================

    @Override
    public boolean clearCart(int cartId) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            CLEAR_CART_SQL)
        ) {

            ps.setInt(1, cartId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= COUNT =================

    @Override
    public int getCartItemCount(int cartId) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            GET_CART_ITEM_COUNT_SQL)
        ) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt("item_count");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    // ================= TOTAL =================

    @Override
    public BigDecimal getCartTotal(int cartId) {

        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(
                            GET_CART_TOTAL_SQL)
        ) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getBigDecimal("total");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    // ================= HELPER =================

    private CartItem mapResultSetToCartItem(
            ResultSet rs) throws Exception {

        CartItem item =
                new CartItem();

        item.setCartItemId(
                rs.getInt("cart_item_id"));

        item.setCartId(
                rs.getInt("cart_id"));

        item.setVariantId(
                rs.getInt("variant_id"));

        item.setQuantity(
                rs.getInt("quantity"));

        return item;
    }

    // ================= UNUSED METHOD =================

    @Override
    public List<CartItem> getCartItemsByCartId(
            Cart cartId) {

        return null;
    }
}