package com.cs.servlet;

import java.io.IOException;

import com.cs.entity.Student;
import com.cs.service.DropCourseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dropCourse")
public class DropCourseServlet extends HttpServlet {
    private DropCourseService dropCourseService = new DropCourseService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 会话验证（从 Session 中取 studentId）
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("student") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // 2. 获取 courseId 参数并校验
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Integer studentId = student.getId();
        String courseIdStr = req.getParameter("courseId");
        if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
            // 处理参数缺失的情况
            String msg = java.net.URLEncoder.encode("参数缺失", "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/mycourses?error=" + msg);
            return;
        }
        Integer courseId;
        try {
            courseId = Integer.parseInt(courseIdStr);
        } catch (NumberFormatException e) {
            // 处理参数不是数字的情况
            String msg = java.net.URLEncoder.encode("无效课程ID", "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/mycourses?error=" + msg);
            return;
        }
        // 3. try-catch 调用 service.dropCourse()
        // 4. 成功：重定向到 /mycourses?msg=退课成功
        // 5. 失败（BusinessException）：重定向到 /mycourses?msg=错误信息
        try {
            dropCourseService.dropCourse(studentId, courseId);
            // 成功，携带 success 参数重定向
            String msg = java.net.URLEncoder.encode("退课成功", "UTF-8");
            resp.sendRedirect(req.getContextPath() + "/mycourses?msg=" + msg);
            
        } catch (Exception e) {
            // 失败，把异常信息带回
            // 注意：e.getMessage() 里最好是你提前定义好的友好提示
            resp.sendRedirect(req.getContextPath() + "/mycourses?msg=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
