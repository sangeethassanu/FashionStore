package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.util.DBConnection;


public class CartDAOImpl implements CartDAO {

    // ================= SQL QUERIES =================
    private static final String INSERT_CART_SQL =
            "INSERT INTO cart (user_id, created_at) VALUES (?, CURRENT_TIMESTAMP)";

    private static final String GET_CART_BY_ID_SQL =
            "SELECT * FROM cart WHERE cart_id = ?";

    private static final String GET_CART_BY_USER_SQL =
            "SELECT * FROM cart WHERE user_id = ?";

    private static final String DELETE_CART_SQL =
            "DELETE FROM cart WHERE user_id = ?";

    private static final String CART_EXISTS_SQL =
            "SELECT 1 FROM cart WHERE user_id = ?";

    // ================= CREATE CART =================
    @Override
    public boolean createCart(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_CART_SQL)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= GET CART BY ID =================
    @Override
    public Cart getCartById(int cartId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_CART_BY_ID_SQL)) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToCart(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= GET CART BY USER =================
    @Override
    public Cart getCartByUserId(int userId) throws Exception{

        String sql =
                "SELECT * FROM cart WHERE user_id = ?";

        Connection conn =
                DBConnection.getConnection();

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs =
                ps.executeQuery();

        if (rs.next()) {

            Cart cart = new Cart();

            cart.setCartId(
                    rs.getInt("cart_id"));

            cart.setUserId(
                    rs.getInt("user_id"));

            return cart;
        }

        return null;
    }
    
    @Override
    public int addCart(Cart cart) throws Exception {

        String sql =
                "INSERT INTO cart(user_id, created_at) " +
                "VALUES(?, CURRENT_TIMESTAMP)";

        Connection conn =
                DBConnection.getConnection();

        PreparedStatement ps =
                conn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, cart.getUserId());

        ps.executeUpdate();

        ResultSet rs =
                ps.getGeneratedKeys();

        if (rs.next()) {

            return rs.getInt(1);
        }

        return 0;
    }

    // ================= GET OR CREATE CART =================
    @Override
    public Cart getOrCreateCartByUserId(int userId) throws Exception {

        Cart cart = getCartByUserId(userId);

        if (cart == null) {
            createCart(userId);
            cart = getCartByUserId(userId);
        }

        return cart;
    }

    // ================= DELETE CART =================
    @Override
    public boolean deleteCart(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_CART_SQL)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= CHECK CART EXISTS =================
    @Override
    public boolean cartExistsByUserId(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(CART_EXISTS_SQL)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= HELPER METHOD =================
    private Cart mapResultSetToCart(ResultSet rs) throws Exception {

        Cart cart = new Cart();

        cart.setCartId(rs.getInt("cart_id"));
        cart.setUserId(rs.getInt("user_id"));
        cart.setCreatedAt(rs.getTimestamp("created_at"));

        return cart;
    }
}