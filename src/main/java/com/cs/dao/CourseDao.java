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
 * 课程表（course）的数据访问对象
 * 负责所有与 Course 实体相关的数据库操作
 */

public class CourseDao {

    // 1.查询所有课程信息,用于让学生获取所有所有的课程信息
    public List<Course> selectAll() {
        String sql = "SELECT id, name, teacher, capacity, selected FROM course";

        //声明资源
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

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
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(rs != null) {
                try{
                    rs.close();
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try{
                    pstmt.close();
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null) {
                DBUtil.closeConnection(conn);
            }
        }

        return courses;
    }

    // 2.用于在选课成功操作后增加课程已选人数
    public void increacedById(Integer id, Connection conn) throws SQLException {
        String sql = "UPDATE course set selected = selected + 1 WHERE id = ? AND selected < capacity";

        //声明资源
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
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

    // 3.根据课程id查询课程
    public Course selectById(Integer courseId) throws SQLException {
        String sql = "SELECT * FROM course WHERE id = ?";
        Course course = null;

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setInt(1, courseId);

            try (ResultSet rs = prst.executeQuery()) {
                if(rs.next()) {
                    course = new Course();
                    course.setId(rs.getInt("id"));
                    course.setName(rs.getString("name"));
                    course.setTeacher(rs.getString("teacher"));
                    course.setCapacity(rs.getInt("capacity"));
                    course.setSelected(rs.getInt("selected"));
                }
            }
        }

        return course;
    }

    // 4.根据课程id减少课程已选人数
    public void decreasedById(Integer id, Connection conn) throws SQLException {
        String sql = "UPDATE course set selected = selected - 1 WHERE id = ? AND selected > 0";

        //声明资源
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        finally {
            if(pstmt != null) {
                pstmt.close();
            }
        }

    }
}
