package com.cs.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库工具类：负责获取和关闭数据库连接
 * 以后所有 DAO 类都通过这个类来操作数据库，避免重复代码
 */

public class DBUtil {
    
   // 1. 数据库连接 URL（不含敏感信息，可以保留在代码中）
    private static final String URL = "jdbc:mysql://localhost:3306/course_selection?useSSL=false&serverTimezone=Asia/Shanghai";
    
    // 2. 用户名和密码从配置文件读取（声明为 final，在静态块中赋值）
    private static final String USERNAME;
    private static final String PASSWORD;

    // 3. 静态块：加载 config.properties 并赋值
    static {
        Properties props = new Properties();
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("找不到 config.properties 文件，请确认文件在 src/main/resources/ 目录下");
            }
            props.load(input);
            USERNAME = props.getProperty("db.username");
            PASSWORD = props.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("加载数据库配置文件失败", e);
        }

        // 4. 校验配置是否为空（防止因配置文件缺失导致后续 NPE）
        if (USERNAME == null || USERNAME.isEmpty() || PASSWORD == null || PASSWORD.isEmpty()) {
            throw new RuntimeException("config.properties 中 db.username 或 db.password 未正确配置");
        }
    }


    // 静态代码块：在类加载时只执行一次，用于加载驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL 驱动加载失败！", e);
        }
    }

    /**
     * 获取数据库连接
     * @return Connection 对象
     * @throws SQLException 连接失败时抛出
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * 关闭连接（释放资源）
     * @param conn 要关闭的连接
     */
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try{
                conn.close();
            }
            catch(SQLException e) {
                e.printStackTrace();;
            }
        }
    }
    
}
