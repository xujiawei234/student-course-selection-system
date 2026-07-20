/**
 * 我选用的是创建dto包来编写LoginResult类。
 * 因为我认为这是业务层和表现层之间的数据交换，
 * 完美符合DTO的定义。而且这个LoginResult是不参与
 * 和数据库的映射关系的，放在Entity层不合适
 */
package com.cs.dto;

import com.cs.entity.Student;

/**
 * 登录结果对象，用于封装登录操作的返回信息
 * 解决返回 null 无法区分失败原因的问题
 */

public class LoginResult {
    private boolean success;
    private String message;
    private Student student;

    private LoginResult(boolean success, String message, Student student) {//这里为什么要用私有构造器是为了避免外界构造这个类吗？
        this.success = success;
        this.message = message;
        this.student = student;
    }

    public static LoginResult success(Student student) {//为什么这里要用静态方法？
        return new LoginResult(true, "登录成功", student);
    }

    public static LoginResult fail(String message) {
        return new LoginResult(false, message, null);
    }

    // Getter（只提供获取，不提供 Setter，保证不可变性）
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Student getStudent() { return student; }
}
