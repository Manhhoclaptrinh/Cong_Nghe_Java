package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> students = StudentStore.findAll();
        Map<String, Integer> classCounts = new LinkedHashMap<>();

        for (Student student : students) {
            classCounts.merge(student.getClassName(), 1, Integer::sum);
        }

        request.setAttribute("totalStudents", students.size());
        request.setAttribute("classCounts", classCounts);

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
