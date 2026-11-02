package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.impl.CartItemDAOImpl;

import com.fashionstore.model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cart/update")
public class UpdateCartQuantityServlet
        extends HttpServlet {

    private static final long
            serialVersionUID = 1L;

    private final CartItemDAO
            cartItemDAO =
            new CartItemDAOImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int cartItemId =
                    Integer.parseInt(
                            request.getParameter(
                                    "cartItemId"));

            String action =
                    request.getParameter(
                            "action");

            CartItem item =
                    cartItemDAO.getCartItemById(
                            cartItemId);

            if (item != null) {

                int quantity =
                        item.getQuantity();

                // INCREASE
                if ("increase".equals(action)) {

                    quantity++;
                }

                // DECREASE
                else if ("decrease".equals(action)
                        && quantity > 1) {

                    quantity--;
                }

                cartItemDAO
                        .updateCartItemQuantity(
                                cartItemId,
                                quantity);
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/cart");

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(e);
        }
    }
}