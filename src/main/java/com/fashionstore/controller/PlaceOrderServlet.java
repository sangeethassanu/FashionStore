package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.OrderItemDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CartDAO cartDAO =
            new CartDAOImpl();

    private final CartItemDAO cartItemDAO =
            new CartItemDAOImpl();
    
    private final OrderDAO orderDAO =
            new OrderDAOImpl();

    private final OrderItemDAO orderItemDAO =
            new OrderItemDAOImpl();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // LOGIN CHECK
        if (session == null ||
            session.getAttribute("loggedUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login");

            return;
        }

        try {

            User user =
                    (User) session.getAttribute(
                            "loggedUser");

            // GET FORM DATA
            String name =
                    request.getParameter("name");

            String phone =
                    request.getParameter("phone");

            String address =
                    request.getParameter("address");

            String paymentMethod =
                    request.getParameter(
                            "paymentMethod");

            // GET CART
            Cart cart =
                    cartDAO.getCartByUserId(
                            user.getUserId());

            if (cart == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/cart");

                return;
            }

            // GET CART ITEMS
            List<CartItem> cartItems =
                    cartItemDAO.getCartItemsByCartId(
                            cart.getCartId());

            // TOTAL
            BigDecimal totalAmount =
                    cartItemDAO.getCartTotal(
                            cart.getCartId());

            // CREATE ORDER
            Order order =
                    new Order();

            order.setUserId(
                    user.getUserId());

            order.setTotalAmount(
                    totalAmount);

            order.setPaymentMethod(
                    paymentMethod);

            order.setOrderStatus(
                    "PLACED");

            order.setDeliveryName(
                    name);

            order.setDeliveryPhone(
                    phone);

            order.setDeliveryAddressLine1(
                    address);

            // SAVE ORDER
            orderDAO.placeOrder(order);

            int orderId =
                    order.getOrderId();

            // SAVE ORDER ITEMS
            for (CartItem cartItem : cartItems) {

                OrderItem item =
                        new OrderItem();

                item.setOrderId(orderId);

                item.setVariantId(
                        cartItem.getVariantId());

                item.setQuantity(
                        cartItem.getQuantity());

                item.setPrice(
                        BigDecimal.valueOf(
                                cartItem.getPrice()));

                orderItemDAO.addOrderItem(item);
            }

            // CLEAR CART
            cartItemDAO.clearCart(
                    cart.getCartId());

            // SUCCESS
            request.setAttribute(
                    "successMessage",
                    "Order placed successfully!");

            request.getRequestDispatcher(
                    "/WEB-INF/views/order-success.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(e);
        }
    }
}