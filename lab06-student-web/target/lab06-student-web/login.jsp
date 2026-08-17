<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
</head>
<body>
    <h2>Đăng nhập hệ thống</h2>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Tên đăng nhập:</label><br>
        <input type="text" name="username" required><br><br>

        <label>Mật khẩu:</label><br>
        <input type="password" name="password" required><br><br>

        <button type="submit">Đăng nhập</button>
    </form>

    <p style="color:red">${error}</p>

    <hr>
    <p><b>Admin:</b> admin / 123456</p>
    <p><b>User:</b> user / 123456 (dùng để kiểm tra Bài 9)</p>
</body>
</html>
