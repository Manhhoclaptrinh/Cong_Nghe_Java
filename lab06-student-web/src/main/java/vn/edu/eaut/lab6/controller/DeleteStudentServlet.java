package vn.edu.eaut.lab6.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String id = request.getParameter("id");
        StudentStore.delete(id);

        response.sendRedirect(request.getContextPath() + "/students");
    }
}
