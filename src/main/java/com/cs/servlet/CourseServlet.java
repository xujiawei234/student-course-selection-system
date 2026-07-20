package com.cs.servlet;

import java.io.IOException;
import java.util.List;

import com.cs.entity.Course;
import com.cs.service.CourseService;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {
    private CourseService courseService = new CourseService();

    //处理用户操作
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("student") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Course> courses = courseService.getAllCourses();
        req.setAttribute("courseList", courses);

        req.getRequestDispatcher("/WEB-INF/views/course.jsp").forward(req, resp);
    }
}
