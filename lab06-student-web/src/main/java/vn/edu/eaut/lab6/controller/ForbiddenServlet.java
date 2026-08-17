package vn.edu.eaut.lab6.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/403")
public class ForbiddenServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        request.getRequestDispatcher("/403.jsp")
               .forward(request, response);
    }
}