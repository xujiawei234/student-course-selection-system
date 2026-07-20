package com.cs.service;

import java.util.List;

import com.cs.dao.CourseDao;
import com.cs.entity.Course;

/**
 * 课程业务逻辑层
 * 处理展示课程的业务规则
 */

public class CourseService {
    private CourseDao courseDao = new CourseDao();

    public List<Course> getAllCourses() {
        return courseDao.selectAll();
    }
}
