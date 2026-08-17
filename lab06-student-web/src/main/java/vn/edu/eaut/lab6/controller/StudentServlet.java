package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        if (keyword != null && !keyword.trim().isEmpty()) {
            request.setAttribute("students", StudentStore.search(keyword));
            request.setAttribute("keyword", keyword);
        } else {
            request.setAttribute("students", StudentStore.findAll());
        }

        request.getRequestDispatcher("/student-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = trim(request.getParameter("id"));
        String name = trim(request.getParameter("name"));
        String className = trim(request.getParameter("className"));
        String email = trim(request.getParameter("email"));

        if (id.isEmpty() || name.isEmpty() || className.isEmpty() || email.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin.");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        if (StudentStore.exists(id)) {
            request.setAttribute("error", "Mã sinh viên đã tồn tại.");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        StudentStore.add(new Student(id, name, className, email));
        response.sendRedirect(request.getContextPath() + "/students");
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
