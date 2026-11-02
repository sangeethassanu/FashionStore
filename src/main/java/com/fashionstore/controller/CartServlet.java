package com.fashionstore.controller;

import java.io.IOException;
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
import jakarta.servlet.http.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CartDAO cartDAO =
            new CartDAOImpl();

    private final CartItemDAO cartItemDAO =
            new CartItemDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // CHECK LOGIN
        if (session == null ||
            session.getAttribute("loggedUser") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        User user =
                (User) session.getAttribute("loggedUser");

        try {

            // GET USER CART
            Cart cart =
                    cartDAO.getCartByUserId(
                            user.getUserId());

            // EMPTY CART
            if (cart == null) {

                request.setAttribute(
                        "cartItems",
                        null);

                request.getRequestDispatcher(
                        "/WEB-INF/views/cart.jsp")
                        .forward(request, response);

                return;
            }

            // GET CART ITEMS
            List<CartItem> cartItems =
                    cartItemDAO.getCartItemsByCartId(
                            cart.getCartId());

            // SEND TO JSP
            request.setAttribute(
                    "cartItems",
                    cartItems);

            request.getRequestDispatcher(
                    "/WEB-INF/views/cart.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(e);
        }
    }
}