package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/students",
        "/student-form.jsp",
        "/edit",
        "/delete",
        "/dashboard",
        "/welcome.jsp"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean loggedIn = session != null
                && session.getAttribute("username") != null;

        if (!loggedIn) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String role = String.valueOf(session.getAttribute("role"));
        String path = req.getServletPath();

        boolean adminOnly = path.equals("/student-form.jsp")
                || path.equals("/edit")
                || path.equals("/delete");

        if (adminOnly && !"ADMIN".equals(role)) {
            resp.sendRedirect(req.getContextPath() + "/403");
            return;
        }

        chain.doFilter(request, response);
    }
}
