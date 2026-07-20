package com.cs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cs.entity.Course;
import com.cs.util.DBUtil;


/**
 * 数据访问层的SelectDao
 * 用于实现插入选课记录到selection表中
 */

public class SelectDao {
    public void insertSelection(Integer courseId, Integer studentId, Connection conn) throws SQLException {
        String sql = "INSERT INTO selection(course_id, student_id) VALUES (?, ?)";

        //声明资源
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, courseId);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        }
        finally {
            if(pstmt != null) {
                try{
                    pstmt.close();
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean exists(Integer studentId, Integer courseId) throws SQLException {
        String sql = "SELECT 1 FROM selection WHERE student_id = ? AND course_id = ? LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // 有数据返回 true，否则 false
            }
        }
    }

    public List<Course> selectByStudent(Integer studentId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.id, c.name, c.teacher, c.capacity, c.selected " +
                 "FROM course c JOIN selection s ON c.id = s.course_id " +
                 "WHERE s.student_id = ?";

        try(Connection conn = DBUtil.getConnection();
        PreparedStatement prst = conn.prepareStatement(sql);) {
            prst.setInt(1, studentId);
            try(ResultSet rs = prst.executeQuery()) {
                while(rs.next()) {
                    Course course = new Course();

                    course.setId(rs.getInt("id"));
                    course.setName(rs.getString("name"));
                    course.setTeacher(rs.getString("teacher"));
                    course.setCapacity(rs.getInt("capacity"));
                    course.setSelected(rs.getInt("selected"));

                    courses.add(course);
                }
            }
        }

        return courses;
    }

    /**
     * 删除选课记录
     * @param studentId 学生ID
     * @param courseId  课程ID
     * @return true 表示删除了至少一条记录，false 表示没有找到对应的选课记录
     * @throws SQLException 如果数据库操作发生错误（连接失败、SQL语法错误等）
     */
    public boolean deleteByStudentAndCourse(Integer studentId, Integer courseId, Connection conn) throws SQLException {
        String sql = "DELETE FROM selection WHERE student_id = ? AND course_id = ?";
        try (
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;  // 根据实际影响行数返回
        }
        // 注意：没有 catch 块！任何 SQLException 都会直接向上抛出
    }
}
