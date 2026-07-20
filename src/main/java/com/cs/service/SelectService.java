package com.cs.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.cs.dao.CourseDao;
import com.cs.dao.SelectDao;
import com.cs.entity.Course;
import com.cs.exception.BusinessException;
import com.cs.util.DBUtil;

/**
 * SelectService层用于处理选择课程时发生的业务逻辑
 * 
 */

public class SelectService {
    private CourseDao courseDao = new CourseDao();
    private SelectDao selectDao = new SelectDao();

    /**
     * 接受前端传来的coureId、studentId参数执行选课操作
     */
    public void selectById(Integer courseId, Integer studentId) {
        //声明资源
        Connection conn = null;

        try {
            // 1. 参数校验
            if (courseId == null || studentId == null) {
                throw new BusinessException("参数错误");
            }

            // 2. 检查课程是否存在（调用 CourseDao 查一下）
            Course course = courseDao.selectById(courseId);
            if (course == null) {
                throw new BusinessException("课程不存在");
            }

            // 3. 检查是否已满
            if (course.getSelected() >= course.getCapacity()) {
                throw new BusinessException("课程已满，选课失败");
            }

            // 4. 检查是否已选过（调用 SelectDao 检查是否存在）
            if (selectDao.exists(studentId, courseId)) {
                throw new BusinessException("您已选过该课程，请勿重复选课");
            }

            // 5. 执行事务（开启事务、更新人数、插入记录）
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            courseDao.increacedById(courseId, conn);
            selectDao.insertSelection(courseId, studentId, conn);
            conn.commit();

        } catch (BusinessException e) {
            // 业务异常直接抛出，让上层（Servlet）处理
            throw e;
        } catch (SQLException e) {
            // 数据库异常，回滚事务
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            throw new BusinessException("数据库异常，选课失败，请重试");
        }
            
    }
}
