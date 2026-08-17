package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AccessLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        long start = System.currentTimeMillis();

        chain.doFilter(request, response);

        long elapsed = System.currentTimeMillis() - start;

        HttpSession session = req.getSession(false);
        String user = "anonymous";

        if (session != null && session.getAttribute("username") != null) {
            user = String.valueOf(session.getAttribute("username"));
        }

        System.out.println(
                "[ACCESS] URI=" + req.getRequestURI()
                        + " | METHOD=" + req.getMethod()
                        + " | USER=" + user
                        + " | TIME=" + elapsed + "ms"
        );
    }
}
