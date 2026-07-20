package com.cs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cs.entity.Student;
import com.cs.util.DBUtil;

/**
 * 学生表（student）的数据访问对象
 * 负责所有与 Student 实体相关的数据库操作
 */

public class StudentDao {
    /**
     * 根据用户名查询学生信息
     * @param username 页面传入的用户名
     * @return 如果找到对应的学生，返回 Student 对象；否则返回 null
     */
    public Student selectByUsername(String username) {
        // 1. SQL 语句（使用 ? 占位符防止 SQL 注入，这是规范写法）
        String sql = "SELECT id, username, password, name FROM student WHERE username = ?";

        // 2.声明资源
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Student student = null;

        try{
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                student = new Student();

                student.setId(rs.getInt("id"));
                student.setUsername(rs.getString("username"));
                student.setPassword(rs.getString("password"));
                student.setName(rs.getString("name"));
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

        return student;
    }

    public void insert(Student student) throws SQLException {
        String sql = "INSERT INTO student (username, password, name) VALUES (?, ?, ?)";
        // ... 使用 PreparedStatement 执行
        // 注意：id 是自增的，不需要手动设置

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, student.getUsername());
            pstm.setString(2, student.getPassword());
            pstm.setString(3, student.getName());

            pstm.executeUpdate();
        }
    }
}
