package com.cs.servlet;

import java.io.IOException;
import java.util.List;

import com.cs.entity.Course;
import com.cs.entity.Student;
import com.cs.service.MyCourseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/mycourses")
public class MyCourseServlet extends HttpServlet {
    private MyCourseService myCourseService = new MyCourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 会话验证
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. 获取学生ID
        Student student = (Student) session.getAttribute("student");
        Integer studentId = student.getId();

        // 3. 调用 Service 获取已选课程
        List<Course> myCourses = myCourseService.getMyCourses(studentId);
        req.setAttribute("myCourses", myCourses);

        // 4. 转发到 mycourse.jsp
        req.getRequestDispatcher("/WEB-INF/views/mycourse.jsp").forward(req, resp);
    }
}