package com.cs.service;

import java.util.List;

import com.cs.dao.SelectDao;
import com.cs.entity.Course;
import com.cs.exception.BusinessException;

public class MyCourseService {
    private SelectDao selectDao = new SelectDao();

    public List<Course> getMyCourses(Integer studentId) {

        if (studentId == null || studentId < 1) {
            throw new IllegalArgumentException("无效的学生ID");
        }
        try {
            // 调用 DAO 层（可能抛出 SQLException）
            return selectDao.selectByStudent(studentId);
        } catch (Exception e) {
            // 包装成业务异常（非检查异常），抛出给 Web 层
            throw new BusinessException("查询课程失败，请稍后重试");
        }
    }
}
