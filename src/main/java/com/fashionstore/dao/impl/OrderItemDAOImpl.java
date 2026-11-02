package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

public class OrderItemDAOImpl
        implements OrderItemDAO {

    // ================= SQL =================

    private static final String
            INSERT_ORDER_ITEM_SQL =

            "INSERT INTO order_items "
            + "(order_id, variant_id, "
            + "quantity, price) "
            + "VALUES (?, ?, ?, ?)";

    private static final String
            GET_ORDER_ITEM_BY_ID_SQL =

            "SELECT * FROM order_items "
            + "WHERE order_item_id = ?";

    private static final String
            GET_ORDER_ITEMS_BY_ORDER_SQL =

            "SELECT * FROM order_items "
            + "WHERE order_id = ?";

    private static final String
            DELETE_ORDER_ITEM_SQL =

            "DELETE FROM order_items "
            + "WHERE order_item_id = ?";

    private static final String
            DELETE_ORDER_ITEMS_BY_ORDER_SQL =

            "DELETE FROM order_items "
            + "WHERE order_id = ?";

    // ================= ADD SINGLE ITEM =================

    @Override
    public boolean addOrderItem(
            OrderItem orderItem) {

        try (Connection conn =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     conn.prepareStatement(
                             INSERT_ORDER_ITEM_SQL)) {

            ps.setInt(
                    1,
                    orderItem.getOrderId());

            ps.setInt(
                    2,
                    orderItem.getVariantId());

            ps.setInt(
                    3,
                    orderItem.getQuantity());

            ps.setBigDecimal(
                    4,
                    orderItem.getPrice());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= ADD MULTIPLE ITEMS =================

    @Override
    public boolean addOrderItems(
            List<OrderItem> orderItems) {

        try (Connection conn =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     conn.prepareStatement(
                             INSERT_ORDER_ITEM_SQL)) {

            for (OrderItem item : orderItems) {

                ps.setInt(
                        1,
                        item.getOrderId());

                ps.setInt(
                        2,
                        item.getVariantId());

                ps.setInt(
                        3,
                        item.getQuantity());

                ps.setBigDecimal(
                        4,
                        item.getPrice());

                ps.addBatch();
            }

            int[] results =
                    ps.executeBatch();

            for (int r : results) {

                if (r <= 0) {

                    return false;
                }
            }

            return true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= GET BY ID =================

    @Override
    public OrderItem getOrderItemById(
            int orderItemId) {

        try (Connection conn =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     conn.prepareStatement(
                             GET_ORDER_ITEM_BY_ID_SQL)) {

            ps.setInt(
                    1,
                    orderItemId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (rs.next()) {

                    return mapResultSetToOrderItem(
                            rs);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // ================= GET ITEMS BY ORDER =================

    @Override
    public List<OrderItem> getOrderItemsByOrderId(
            int orderId) {

        List<OrderItem> items =
                new ArrayList<>();

        try (Connection conn =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     conn.prepareStatement(
                             GET_ORDER_ITEMS_BY_ORDER_SQL)) {

            ps.setInt(
                    1,
                    orderId);

            try (ResultSet rs =
                         ps.executeQuery()) {

                while (rs.next()) {

                    items.add(
                            mapResultSetToOrderItem(
                                    rs));
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return items;
    }

    // ================= DELETE SINGLE =================

    @Override
    public boolean deleteOrderItem(
            int orderItemId) {

        try (Connection conn =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     conn.prepareStatement(
                             DELETE_ORDER_ITEM_SQL)) {

            ps.setInt(
                    1,
                    orderItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= DELETE BY ORDER =================

    @Override
    public boolean deleteOrderItemsByOrderId(
            int orderId) {

        try (Connection conn =
                     DBConnection.getConnection();

             PreparedStatement ps =
                     conn.prepareStatement(
                             DELETE_ORDER_ITEMS_BY_ORDER_SQL)) {

            ps.setInt(
                    1,
                    orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ================= MAPPER =================

    private OrderItem mapResultSetToOrderItem(
            ResultSet rs)
            throws Exception {

        OrderItem item =
                new OrderItem();

        item.setOrderItemId(
                rs.getInt(
                        "order_item_id"));

        item.setOrderId(
                rs.getInt(
                        "order_id"));

        item.setVariantId(
                rs.getInt(
                        "variant_id"));

        item.setQuantity(
                rs.getInt(
                        "quantity"));

        item.setPrice(
                rs.getBigDecimal(
                        "price"));

        return item;
    }
}