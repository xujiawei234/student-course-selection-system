package com.cs.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/home")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("student") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 从 session 中取出登录的学生信息
        Object student = req.getSession().getAttribute("student");
        req.setAttribute("student", student);
        // 转发到 welcome.jsp
        req.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(req, resp);
    }
}
