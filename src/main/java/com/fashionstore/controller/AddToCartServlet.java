package com.fashionstore.controller;

import java.io.IOException;

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

@WebServlet("/cart/add")
public class AddToCartServlet extends HttpServlet {

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

        // CHECK LOGIN
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

            // ================= PRODUCT ID =================

            int productId =
                    Integer.parseInt(
                            request.getParameter(
                                    "productId"));

            // ================= VARIANT ID =================

            String variantParam =
                    request.getParameter("variantId");

            if (variantParam == null ||
                variantParam.isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/products");

                return;
            }

            int variantId =
                    Integer.parseInt(variantParam);

            // ================= GET USER CART =================

            Cart cart =
                    cartDAO.getCartByUserId(
                            user.getUserId());

            // ================= CREATE CART =================

            if (cart == null) {

                cart =
                        new Cart();

                cart.setUserId(
                        user.getUserId());

                int cartId =
                        cartDAO.addCart(cart);

                cart.setCartId(cartId);
            }

            // ================= CREATE CART ITEM =================

            CartItem item =
                    new CartItem();

            item.setCartId(
                    cart.getCartId());

            item.setVariantId(
                    variantId);

            item.setProductId(
                    productId);

            item.setQuantity(1);

            // ================= SAVE =================

            cartItemDAO.addCartItem(item);

            // ================= REDIRECT =================

            response.sendRedirect(
                    request.getContextPath()
                    + "/product-details?productId="
                    + productId);
            } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(e);
        }
    }
}