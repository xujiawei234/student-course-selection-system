# 🎓 Student Course Selection System (原生Java版)

> 一个基于 **原生Java Web技术栈** 构建的学生选课系统。从零搭建，不依赖Spring框架，直面Servlet、JSP、JDBC等底层技术，旨在理解Web应用的运行机制与分层架构思想。

---

## 📖 项目简介

本项目是一个完整的"学生选课系统"，包含**注册、登录、课程浏览、选课/退课、我的课表**等核心功能。项目采用**三层架构（表现层-业务层-数据访问层）+ MVC设计模式**，严格按照"自底向上"的开发顺序，确保每一层职责清晰、解耦彻底。

**核心亮点**：
- ✅ 手动实现 **JDBC事务管理**（选课/退课原子性保证）
- ✅ 自定义 **BusinessException** 业务异常 + **LoginResult** DTO 数据传输对象
- ✅ 基于 **Jakarta EE（Tomcat 10）** 规范，而非旧版 javax.servlet
- ✅ 侧边栏导航布局 + WEB-INF 安全目录结构
- ✅ 完整的**会话管理**（Session）与**越权访问拦截**

---

## 🛠️ 技术栈

| 层级 | 技术选型 |
| :--- | :--- |
| **前端视图** | JSP + HTML + CSS（JSP置于 `WEB-INF/views/` 安全目录） |
| **控制器** | Servlet（Jakarta EE 规范） |
| **业务层** | 原生 Java（手动事务控制） |
| **数据访问层** | JDBC + `PreparedStatement`（防SQL注入） |
| **数据库** | MySQL 8.0（InnoDB引擎，支持外键约束） |
| **构建工具** | Maven（多环境配置，依赖管理） |
| **Web容器** | Apache Tomcat 10.1 |
| **开发环境** | JDK 21 + VSCode |

---

## 📦 核心功能模块

| 功能模块 | 对应路径 | 描述 |
| :--- | :--- | :--- |
| **用户注册** | `/register` | 注册新账号，校验用户名唯一性 |
| **用户登录** | `/login` | 验证身份，建立 Session 会话 |
| **退出登录** | `/logout` | 销毁 Session，重定向至登录页 |
| **课表展示** | `/course` | 展示所有课程，显示已选人数/容量 |
| **选课操作** | `/selectCourse` (POST) | 事务：插入选课记录 + 课程人数+1 |
| **我的课表** | `/mycourses` | 展示当前学生已选课程列表 |
| **退课操作** | `/dropCourse` (POST) | 事务：删除选课记录 + 课程人数-1 |

---

## 🗂️ 项目目录结构
```
src/main/
├── java/com/cs/
│ ├── dao/ # 数据访问层（StudentDao, CourseDao, SelectionDao）
│ ├── service/ # 业务逻辑层（含事务控制）
│ ├── servlet/ # 控制器层（LoginServlet, CourseServlet, SelectServlet...）
│ ├── entity/ # 实体类（Student, Course, Selection）
│ ├── dto/ # 数据传输对象（LoginResult）
│ ├── exception/ # 自定义业务异常（BusinessException）
│ └── util/ # 工具类（DBUtil）
├── resources/ # 资源文件
├── webapp/
│ ├── WEB-INF/
│ │ ├── views/ # JSP视图（侧边栏、课程页、我的课表等）
│ │ └── web.xml # 部署描述符（空文件，Tomcat 10采用注解方式）
│ └── login.jsp # 登录页（可直接访问）
└── ...

```

---

## 🚀 如何运行

### 前置条件
- JDK 21+
- MySQL 8.0+
- Apache Tomcat 10.1+
- Maven 3.9+

### 1. 克隆项目
```bash
git clone https://github.com/你的用户名/student-course-selection-system.git
cd student-course-selection-system
2. 创建数据库
执行 docs/schema.sql 脚本（项目根目录下），创建 course_selection 数据库及三张表。

3. 修改数据库配置
编辑 src/main/java/com/cs/util/DBUtil.java，修改 URL、USERNAME、PASSWORD 为你的实际配置。

4. 打包部署
bash
mvn clean package
将生成的 target/student_course_select_system-1.0-SNAPSHOT.war 复制到 tomcat/webapps/ 目录下，启动 Tomcat。

5. 访问系统
打开浏览器访问：http://localhost:8080/项目名/login.jsp

默认测试账号：2021001 / 123456

```


🧠 项目核心设计思想
```
1. 分层架构（严格自底向上）
Util → Entity → DAO → Service → Servlet → JSP

下层不依赖上层，上层只调用下层公开接口。

2. 事务管理（在 Service 层控制）
java
conn.setAutoCommit(false);
try {
    courseDao.increacedById(courseId, conn);
    selectionDao.insert(courseId, studentId, conn);
    conn.commit();
} catch (SQLException e) {
    conn.rollback();
    throw new BusinessException("选课失败");
} finally {
    conn.close();
}
3. 自定义异常与 DTO
BusinessException：区分业务异常（如"课程已满"）与系统异常。

LoginResult：避免返回 null 导致调用方无法区分失败原因。

4. 会话管理与安全
所有 Servlet（除 LoginServlet 外）均进行 Session 校验，防止未登录越权访问。

密码采用 明文存储（此为已知技术债，重构时将引入 BCrypt 加密）。
```

📚 学习收获与反思
```
深刻理解了 Web 容器原理（Tomcat 如何解析 war 包、加载 Servlet）。

亲手实现 JDBC 事务，明白了 Spring 的 @Transactional 为何存在。

积累了大量踩坑经验：Maven 依赖冲突、Tomcat 版本适配、HTTP 中文编码、forward 与 redirect 的区别。

体会了分层设计的价值：即使是一个简单系统，分层带来的可维护性也远超"面条式代码"。
```
🔮 下一步重构计划
```
本项目将作为 "Java Web 演进之路" 系列的第一阶段。后续计划：

重构为 Spring MVC + JdbcTemplate（简化 JDBC 操作）

重构为 Spring Boot + JPA（配置外部化、内置 Tomcat）

引入前端框架（Vue/React），实现前后端分离
```
🤝 贡献与反馈
```
本项目是个人学习实践项目，如有建议或疑问，欢迎提 Issue 或联系我。
"知其然，更知其所以然"——这就是我们写原生 Java 项目的意义。
