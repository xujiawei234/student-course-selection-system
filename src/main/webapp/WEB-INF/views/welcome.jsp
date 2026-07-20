<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主页面</title>
    <style>
        .container { display: flex; }
        .content { flex: 1; padding: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <%@ include file="/WEB-INF/views/sidebar.jsp" %>
        <div class="content">
            <h1>🎉 欢迎来到学生选课系统</h1>
            <%
                Object student = request.getAttribute("student");
                if (student != null) {
            %>
                <p>您好，<%= student.getClass().getMethod("getName").invoke(student) %>！请点击左侧菜单进行操作。</p>
            <%
                } else {
            %>
                <p>请先 <a href="login.jsp">登录</a></p>
            <%
                }
            %>
            <!-- 可以放一些系统公告或动态 -->
            <p>当前系统状态：正常运行</p>
        </div>
    </div>
</body>
</html>