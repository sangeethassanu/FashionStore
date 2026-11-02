package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;

import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;

import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CartDAO cartDAO =
            new CartDAOImpl();

    private final CartItemDAO cartItemDAO =
            new CartItemDAOImpl();

    @Override
    protected void doGet(
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

            // GET ITEMS
            List<CartItem> cartItems =
                    cartItemDAO.getCartItemsByCartId(
                            cart.getCartId());

            // TOTAL
            BigDecimal totalAmount =
                    cartItemDAO.getCartTotal(
                            cart.getCartId());

            // SET ATTRIBUTES
            
            int totalItems = 0;

            for (CartItem item : cartItems) {

                totalItems += item.getQuantity();
            }

            request.setAttribute(
                    "totalItems",
                    totalItems);
            request.setAttribute(
                    "cartItems",
                    cartItems);

            request.setAttribute(
                    "totalAmount",
                    totalAmount);

            // FORWARD
            request.getRequestDispatcher(
                    "/WEB-INF/views/checkout.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(e);
        }
    }
}	