package com.cs.service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.cs.dao.StudentDao;
import com.cs.dto.LoginResult;
import com.cs.entity.Student;
import com.cs.exception.BusinessException;

/**
 * 学生业务逻辑层
 * 处理与登录、注册相关的业务规则
 */

public class StudentService {
    private StudentDao studentDao = new StudentDao();//这里为什么不能放在login函数内部？

    /**
     * 学生登录业务逻辑
     * @param username 用户名
     * @param password 明文密码
     * @return 登录成功返回 Student 对象，失败返回 null（或者你可以返回一个自定义对象，我们先简化）
     */
    public LoginResult login(String username, String password) {
        if(username == null || username.trim().isBlank() || password == null || password.trim().isBlank()) {
            return LoginResult.fail("用户名或密码不能为空");//在业务层进行防护，做防止输入不合法校验，不在其他层做符合业务处理的逻辑，但是为什么没有做限制用户输入长度的校验？
        }

        Student student = studentDao.selectByUsername(username);
        
        if (student == null) {
            return LoginResult.fail("用户不存在");
        }
        if (!student.getPassword().equals(password)) {
            return LoginResult.fail("密码错误");
        }
        return LoginResult.success(student);
    }

    // 在 com.cs.service.StudentService 中新增

    public void register(String username, String password, String name) {
        // 1. 校验非空
        if(username == null || username.isBlank() || password == null || password.isBlank() || name == null || name.isBlank()) {
            throw new BusinessException("无效的登录参数");
        }
        if (username.length() < 3) {
            throw new BusinessException("用户名长度不能少于3个字符");
        }
        if (password.length() < 6) {
            throw new BusinessException("密码长度不能少于6个字符");
        }
        // 2. 直接插入，依靠数据库唯一约束保证用户名不重复（避免 SELECT + INSERT 的并发竞态条件）
        Student student = new Student();
        student.setName(name);
        student.setPassword(password);
        student.setUsername(username);

        try{
            studentDao.insert(student);
        }
        catch(SQLIntegrityConstraintViolationException e) {
            throw new BusinessException("已经存在相同的账户名");
        }
        catch(SQLException exception) {
            throw new BusinessException("数据库访问发生问题");
        }
        
    }
}
