<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật sinh viên</title>
</head>
<body>
    <h2>Cập nhật sinh viên</h2>

    <p style="color:red">${error}</p>

    <form action="${pageContext.request.contextPath}/edit" method="post">
        <label>Mã sinh viên:</label><br>
        <input type="text" name="id" value="${student.id}" readonly><br><br>

        <label>Họ tên:</label><br>
        <input type="text" name="name" value="${student.name}" required><br><br>

        <label>Lớp:</label><br>
        <input type="text" name="className" value="${student.className}" required><br><br>

        <label>Email:</label><br>
        <input type="email" name="email" value="${student.email}" required><br><br>

        <button type="submit">Cập nhật</button>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/students">Quay lại</a>
</body>
</html>
