package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    // SQL Queries
	private static final String INSERT_ORDER_SQL =

			"INSERT INTO orders "
			+ "(user_id, order_date, total_amount, "
			+ "payment_method, order_status, "
			+ "delivery_name, delivery_phone, "
			+ "delivery_address_line1, "
			+ "delivery_city, delivery_state, "
			+ "delivery_pincode, delivery_country) "

			+ "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ORDER_BY_ID_SQL =
	        "SELECT * FROM orders WHERE order_id = ?";

	private static final String GET_ORDERS_BY_USER_SQL =
	        "SELECT * FROM orders WHERE user_id = ? ORDER BY order_id DESC";

	private static final String GET_ALL_ORDERS_SQL =
	        "SELECT * FROM orders ORDER BY order_id DESC";

	private static final String UPDATE_ORDER_STATUS_SQL =
	        "UPDATE orders SET order_status = ? WHERE order_id = ?";

	private static final String DELETE_ORDER_SQL =
	        "DELETE FROM orders WHERE order_id = ?";

    // DAO Methods
	@Override
	public boolean placeOrder(Order order) {

	    try (Connection conn =
	                 DBConnection.getConnection();

	         PreparedStatement ps =
	                 conn.prepareStatement(
	                         INSERT_ORDER_SQL,
	                         PreparedStatement.RETURN_GENERATED_KEYS)) {

	    	ps.setInt(
	    	        1,
	    	        order.getUserId());

	    	ps.setBigDecimal(
	    	        2,
	    	        order.getTotalAmount());

	    	ps.setString(
	    	        3,
	    	        order.getPaymentMethod());

	    	ps.setString(
	    	        4,
	    	        order.getOrderStatus());

	    	ps.setString(
	    	        5,
	    	        order.getDeliveryName());

	    	ps.setString(
	    	        6,
	    	        order.getDeliveryPhone());

	    	ps.setString(
	    	        7,
	    	        order.getDeliveryAddressLine1());

	    	ps.setString(
	    	        8,
	    	        "Chennai");

	    	ps.setString(
	    	        9,
	    	        "Tamil Nadu");

	    	ps.setString(
	    	        10,
	    	        "600001");

	    	ps.setString(
	    	        11,
	    	        "India");
	        int rows =
	                ps.executeUpdate();

	        // GET GENERATED ORDER ID
	        ResultSet rs =
	                ps.getGeneratedKeys();

	        if (rs.next()) {

	            order.setOrderId(
	                    rs.getInt(1));
	        }

	        return rows > 0;

	    } catch (Exception e) {

	        e.printStackTrace();
	    }

	    return false;
	}

    @Override
    public Order getOrderById(int orderId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ORDER_BY_ID_SQL)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToOrder(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ORDERS_BY_USER_SQL)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) orders.add(mapResultSetToOrder(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ALL_ORDERS_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) orders.add(mapResultSetToOrder(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String orderStatus) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ORDER_STATUS_SQL)) {
            ps.setString(1, orderStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean deleteOrder(int orderId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ORDER_SQL)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // Helper method to map ResultSet to Order object
    private Order mapResultSetToOrder(ResultSet rs) throws Exception {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        return order;
    }
}