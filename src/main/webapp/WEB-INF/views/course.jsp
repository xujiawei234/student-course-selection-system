<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.cs.entity.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>课表页面</title>
    <style>
        /* 整体布局：左侧菜单固定宽度，右侧内容自适应 */
        .container { display: flex; }
        .content { flex: 1; padding: 20px; }
        .course-item { border: 1px solid #ddd; margin-bottom: 10px; padding: 10px; }
    </style>
</head>
<body>
    
    
    <div class="container">
        <!-- 引入左侧菜单 -->
        <%@ include file="/WEB-INF/views/sidebar.jsp" %>
        
        <!-- 右侧内容区 -->
        <div class="content">
            <h2>所有课程</h2>
            <%
                List<Course> courses = (List<Course>) request.getAttribute("courseList");
                if (courses == null || courses.isEmpty()) {
            %>
                <p>📚 暂无课程</p>
            <%
                } else {
                    for (Course c : courses) {
            %>
                <div class="course-item">
                    <strong><%= c.getName() %></strong>（<%= c.getTeacher() %>）
                    <span style="float: right;">
                        已选：<%= c.getSelected() %> / <%= c.getCapacity() %>
                        <!-- ✅ 选课表单（POST 方式） -->
                        <form action="${pageContext.request.contextPath}/selectCourse" method="post" style="display: inline;">
                            <input type="hidden" name="courseId" value="<%= c.getId() %>">
                            <button type="submit">选课</button>
                        </form>
                    </span>
                </div>
            <%
                    }
                }
            %>
        </div>
    </div>
    
    <!-- 放在页面最顶部，用于检测 URL 参数并弹窗 -->
    <%
        String msg = request.getParameter("msg");
        if (msg != null && !msg.isEmpty()) {
            // 防止 XSS 攻击，转义一下
            msg = msg.replace("\"", "\\\"");
    %>
    <script>
        alert("<%= msg %>");
        // 弹窗后，去除 URL 中的 ?msg=xxx，防止刷新页面重复弹窗
        if (window.history && window.history.replaceState) {
            var cleanUrl = window.location.href.split('?')[0];
            window.history.replaceState({}, document.title, cleanUrl);
        }
    </script>
    <%
        }
    %>
</body>
</html>