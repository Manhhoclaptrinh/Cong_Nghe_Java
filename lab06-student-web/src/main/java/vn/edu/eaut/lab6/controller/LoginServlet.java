package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String role = null;

        // Tai khoan theo de bai:
        // admin / 123456
        // Them user / 123456 de kiem tra chuc nang phan quyen Bai 9.
        if ("admin".equals(username) && "123456".equals(password)) {
            role = "ADMIN";
        } else if ("user".equals(username) && "123456".equals(password)) {
            role = "USER";
        }

        if (role != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", role);
            session.setAttribute(
                    "loginTime",
                    LocalDateTime.now().format(FORMATTER)
            );

            response.sendRedirect(request.getContextPath() + "/welcome.jsp");
            return;
        }

        request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
