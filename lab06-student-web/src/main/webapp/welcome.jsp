<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang quản trị</title>
</head>
<body>
    <h2>Xin chào, ${sessionScope.username}</h2>

    <p>Quyền: <strong>${sessionScope.role}</strong></p>
    <p>Thời gian đăng nhập: ${sessionScope.loginTime}</p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/students">Quản lý sinh viên</a></li>
        <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
    </ul>
</body>
</html>
