package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.impl.OrderDAOImpl;

import com.fashionstore.model.Order;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/orders")
public class OrdersServlet
        extends HttpServlet {

    private static final long
            serialVersionUID = 1L;

    private final OrderDAO orderDAO =
            new OrderDAOImpl();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // LOGIN CHECK
        if (session == null ||
            session.getAttribute(
                    "loggedUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login");

            return;
        }

        try {

            User user =
                    (User) session.getAttribute(
                            "loggedUser");

            // GET ORDERS
            List<Order> orders =
                    orderDAO.getOrdersByUserId(
                            user.getUserId());

            // SET ATTRIBUTE
            request.setAttribute(
                    "orders",
                    orders);

            // FORWARD
            request.getRequestDispatcher(
                    "/WEB-INF/views/orders.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(e);
        }
    }
}