<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.cs.entity.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的课表</title>
    <style>
        .container { display: flex; }
        .content { flex: 1; padding: 20px; }
        .course-item { border: 1px solid #ddd; margin-bottom: 10px; padding: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <%@ include file="/WEB-INF/views/sidebar.jsp" %>
        <div class="content">
            <h2>我的课表</h2>
            <%
                List<Course> myCourses = (List<Course>) request.getAttribute("myCourses");
                if (myCourses == null || myCourses.isEmpty()) {
            %>
                <p>📚 你还没有选课，去 <a href="${pageContext.request.contextPath}/course">课表页面</a> 看看吧。</p>
            <%
                } else {
                    for (Course c : myCourses) {
            %>
                <div class="course-item">
                    <strong><%= c.getName() %></strong>（<%= c.getTeacher() %>）
                    <span style="float: right;">
                        <!-- 退课表单 -->
                        <form action="${pageContext.request.contextPath}/dropCourse" method="post" style="display: inline;">
                            <input type="hidden" name="courseId" value="<%= c.getId() %>">
                            <button type="submit" style="color: red;">退课</button>
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