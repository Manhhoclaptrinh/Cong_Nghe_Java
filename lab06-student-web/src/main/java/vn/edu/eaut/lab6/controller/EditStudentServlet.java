package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;

@WebServlet("/edit")
public class EditStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        Student student = StudentStore.findById(id);

        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/students");
            return;
        }

        request.setAttribute("student", student);
        request.getRequestDispatcher("/student-edit.jsp").forward(request, response);
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
            Student student = StudentStore.findById(id);
            request.setAttribute("student", student);
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin.");
            request.getRequestDispatcher("/student-edit.jsp").forward(request, response);
            return;
        }

        StudentStore.update(id, name, className, email);
        response.sendRedirect(request.getContextPath() + "/students");
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
