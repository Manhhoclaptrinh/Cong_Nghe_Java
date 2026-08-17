<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm sinh viên</title>
</head>
<body>
    <h2>Thêm sinh viên</h2>

    <p style="color:red">${error}</p>

    <form action="${pageContext.request.contextPath}/students" method="post">
        <label>Mã sinh viên:</label><br>
        <input type="text" name="id" required><br><br>

        <label>Họ tên:</label><br>
        <input type="text" name="name" required><br><br>

        <label>Lớp:</label><br>
        <input type="text" name="className" required><br><br>

        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <button type="submit">Lưu sinh viên</button>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/students">Quay lại danh sách</a>
</body>
</html>
