package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.impl.CartItemDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cart/remove")
public class RemoveCartItemServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CartItemDAO cartItemDAO =
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

            cartItemDAO.removeCartItem(
                    cartItemId);

            response.sendRedirect(
                    request.getContextPath()
                    + "/cart");

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/cart");
        }
    }
}