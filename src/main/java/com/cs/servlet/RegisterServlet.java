package com.cs.servlet;

import java.io.IOException;

import com.cs.exception.BusinessException;
import com.cs.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private StudentService studentService = new StudentService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        try {
            studentService.register(username, password, name);
            resp.sendRedirect(req.getContextPath() + "/login?msg=" + java.net.URLEncoder.encode("注册成功", "UTF-8"));
        } catch (BusinessException e) {
            // 业务异常（用户名已存在等），友好提示
            resp.sendRedirect(req.getContextPath() + "/register?msg=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            // 系统异常（数据库连接失败等），打印日志并返回通用错误
            e.printStackTrace(); // 生产环境应使用 log
            resp.sendRedirect(req.getContextPath() + "/register?msg=" + java.net.URLEncoder.encode("系统繁忙，请稍后重试", "UTF-8"));
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 直接转发到 register.jsp
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }
}
