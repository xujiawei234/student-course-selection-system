package com.cs.servlet;

import java.io.IOException;

import com.cs.entity.Student;
import com.cs.service.SelectService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/selectCourse")
public class SelectServlet extends HttpServlet{
    private SelectService selectService = new SelectService();
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse rspn) throws IOException,ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            rspn.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            rspn.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Integer studentId = student.getId();
        String courseIdStr = req.getParameter("courseId");
        if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
            // 处理参数缺失的情况
            String msg = java.net.URLEncoder.encode("参数错误", "UTF-8");
            rspn.sendRedirect(req.getContextPath() + "/course?error=" + msg);
            return;
        }
        Integer courseId;
        try {
            courseId = Integer.parseInt(courseIdStr);
        } catch (NumberFormatException e) {
            // 处理参数不是数字的情况
            String msg = java.net.URLEncoder.encode("无效课程ID", "UTF-8");
            rspn.sendRedirect(req.getContextPath() + "/course?error=" + msg);
            return;
        }

        // 在 try-catch 块中修改

        try {
            selectService.selectById(courseId, studentId);
            // 选课成功，携带 success 参数重定向
            String msg = java.net.URLEncoder.encode("选课成功", "UTF-8");
            rspn.sendRedirect(req.getContextPath() + "/course?msg=" + msg);
        } catch (Exception e) {
            // 选课失败，把异常信息（比如“课程已满”或“重复选课”）带回
            // 注意：e.getMessage() 里最好是你提前定义好的友好提示
            rspn.sendRedirect(req.getContextPath() + "/course?msg=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
