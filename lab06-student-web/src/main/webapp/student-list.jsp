<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sinh viên</title>
</head>
<body>
    <h2>Danh sách sinh viên</h2>

    <p>
        Xin chào <strong>${sessionScope.username}</strong>
        - Quyền: <strong>${sessionScope.role}</strong>
    </p>

    <form action="${pageContext.request.contextPath}/students" method="get">
        <input type="text" name="keyword"
               value="${keyword}"
               placeholder="Tìm theo họ tên...">
        <button type="submit">Tìm kiếm</button>
        <a href="${pageContext.request.contextPath}/students">Hiển thị tất cả</a>
    </form>

    <br>

    <c:if test="${sessionScope.role == 'ADMIN'}">
        <a href="${pageContext.request.contextPath}/student-form.jsp">
            Thêm sinh viên
        </a>
        &nbsp;|&nbsp;
    </c:if>

    <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
    &nbsp;|&nbsp;
    <a href="${pageContext.request.contextPath}/welcome.jsp">Trang chủ</a>
    &nbsp;|&nbsp;
    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>

    <br><br>

    <c:choose>
        <c:when test="${empty students}">
            <p style="color:red">
                Không tìm thấy sinh viên phù hợp.
            </p>
        </c:when>

        <c:otherwise>
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>Mã SV</th>
                    <th>Họ tên</th>
                    <th>Lớp</th>
                    <th>Email</th>
                    <th>Chức năng</th>
                </tr>

                <c:forEach var="sv" items="${students}">
                    <tr>
                        <td>${sv.id}</td>
                        <td>${sv.name}</td>
                        <td>${sv.className}</td>
                        <td>${sv.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.role == 'ADMIN'}">
                                    <a href="${pageContext.request.contextPath}/edit?id=${sv.id}">
                                        Sửa
                                    </a>
                                    |
                                    <a href="${pageContext.request.contextPath}/delete?id=${sv.id}"
                                       onclick="return confirm('Bạn có chắc muốn xóa sinh viên này?')">
                                        Xóa
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    Chỉ được xem
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
