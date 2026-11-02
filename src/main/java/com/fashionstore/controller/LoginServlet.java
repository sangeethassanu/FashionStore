package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserDAO userDAO = new UserDAOImpl();

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/login.jsp")
               .forward(request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // ===== VALIDATION =====
        if (email == null || password == null ||
            email.trim().isEmpty() || password.trim().isEmpty()) {

            request.setAttribute("error", "Email and Password are required");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                   .forward(request, response);
            return;
        }

        // ===== AUTHENTICATION =====
        User user = userDAO.loginUser(email, password);

        if (user != null) {

            // ✅ CREATE SESSION
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);

            // ✅ OPTIONAL: session timeout (30 min)
            session.setMaxInactiveInterval(30 * 60);

            // ✅ REDIRECT (choose one)
            response.sendRedirect(request.getContextPath() + "/products");
            // OR:
            // response.sendRedirect(request.getContextPath() + "/home");

        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                   .forward(request, response);
        }
    }
}