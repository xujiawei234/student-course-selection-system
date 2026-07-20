<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="width: 200px; height: 100vh; background-color: #f0f2f5; padding: 20px; float: left;">
    <h3>选课系统</h3>
    <ul style="list-style: none; padding: 0;">
        <li style="margin-bottom: 10px;">
            <a href="${pageContext.request.contextPath}/home">🏠 主页面</a>
        </li>
        <li style="margin-bottom: 10px;">
            <a href="${pageContext.request.contextPath}/course">📚 课表页面</a>
        </li>
        <li style="margin-bottom: 10px;">
            <a href="${pageContext.request.contextPath}/mycourses">📖 我的课表</a>
        </li>
    </ul>
    <!-- 退出登录按钮可以放在这里 -->
    <div style="margin-top: 50px;">
        <!-- 不要再用 <a href="${pageContext.request.contextPath}/logout">  -->
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit">退出登录</button>
        </form>
    </div>
</div>