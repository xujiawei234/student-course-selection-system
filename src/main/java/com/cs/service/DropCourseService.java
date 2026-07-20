package com.cs.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.cs.dao.CourseDao;
import com.cs.dao.SelectDao;
import com.cs.exception.BusinessException;
import com.cs.util.DBUtil;

public class DropCourseService {
    private SelectDao selectDao = new SelectDao();
    private CourseDao courseDao = new CourseDao();

    public void dropCourse(Integer studentId, Integer courseId) {
        // 1. 参数校验
        if(studentId == null || courseId == null || studentId < 1 || courseId < 1) {
            throw new BusinessException("无效的选课参数");
        }
        // 2. 获取连接，关闭自动提交
        Connection conn = null;
        try{
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            if(selectDao.deleteByStudentAndCourse(studentId, courseId, conn)) {
                courseDao.decreasedById(courseId, conn);
            }
            else {
                throw new BusinessException("不存在该选课记录");
            }

            conn.commit();
        }
        catch(SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {
                    // 记录日志（可以使用 Logger）
                    ex.printStackTrace();
                }
            }
            throw new BusinessException("数据库连接出现问题");
        }
        finally {
            // 关闭连接
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    
    }
}
