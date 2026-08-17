<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
</head>
<body>
    <h2>Dashboard</h2>

    <p>Người dùng: <strong>${sessionScope.username}</strong></p>
    <p>Quyền: <strong>${sessionScope.role}</strong></p>
    <p>Thời gian đăng nhập: <strong>${sessionScope.loginTime}</strong></p>
    <p>Tổng số sinh viên: <strong>${totalStudents}</strong></p>

    <h3>Số sinh viên theo lớp</h3>

    <c:choose>
        <c:when test="${empty classCounts}">
            <p>Chưa có dữ liệu.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>Lớp</th>
                    <th>Số lượng</th>
                </tr>
                <c:forEach var="item" items="${classCounts}">
                    <tr>
                        <td>${item.key}</td>
                        <td>${item.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="${pageContext.request.contextPath}/students">Quản lý sinh viên</a>
    &nbsp;|&nbsp;
    <a href="${pageContext.request.contextPath}/welcome.jsp">Trang chủ</a>
</body>
</html>
