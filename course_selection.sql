-- 第1步：删掉旧的数据库（如果存在），重新创建干净的库
DROP DATABASE IF EXISTS course_selection;
CREATE DATABASE course_selection;
USE course_selection;

-- 第2步：建表（如果存在则先删掉，再新建）
DROP TABLE IF EXISTS selection;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS student;

CREATE TABLE student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    teacher VARCHAR(50),
    capacity INT DEFAULT 30,
    selected INT DEFAULT 0
);

CREATE TABLE selection (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    score DECIMAL(5, 2) DEFAULT NULL,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES course(id),
    UNIQUE KEY (student_id, course_id)
);

-- 插入测试数据（先删干净再插入，避免重复）
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE selection;
TRUNCATE TABLE student;
TRUNCATE TABLE course;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO student (username, password, name) VALUES ('root', 123456, '管理员');
INSERT INTO student (username, password, name) VALUES ('2021001', '123456', '张三');
INSERT INTO course (name, teacher, capacity) VALUES ('高等数学', '李老师', 30);
INSERT INTO course (name, teacher, capacity) VALUES ('Java程序设计', '王老师', 25);
