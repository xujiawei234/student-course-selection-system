package com.cs.servlet;

import java.io.IOException;

import com.cs.dto.LoginResult;
import com.cs.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 处理登录请求的控制器
 * 路径映射：/login（对应 form 表单的 action）
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    //实例化对象
    private StudentService studentService = new StudentService();

    //处理用户输入
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, 
    IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        LoginResult loginResult = studentService.login(username, password);

        if(loginResult.isSuccess()) {
            req.getSession().setAttribute("student", loginResult.getStudent());
            resp.sendRedirect(req.getContextPath() + "/home");
        }
        else {
            req.setAttribute("error", loginResult.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) 
    throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
