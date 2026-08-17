package vn.edu.eaut.lab7.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.SanPham;
import vn.edu.eaut.lab7.repository.SanPhamRepository;
import vn.edu.eaut.lab7.util.PaginationUtil;

@WebServlet("/san-pham")
public class SanPhamController extends HttpServlet {

    private final SanPhamRepository repo =
            new SanPhamRepository();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if ("new".equals(action)) {

            req.getRequestDispatcher(
                    "/views/sanpham/form.jsp"
            ).forward(req, resp);

            return;
        }

        if ("edit".equals(action)) {

            int id = Integer.parseInt(
                    req.getParameter("id")
            );

            req.setAttribute(
                    "sanPham",
                    repo.findById(id)
            );

            req.getRequestDispatcher(
                    "/views/sanpham/form.jsp"
            ).forward(req, resp);

            return;
        }

        if ("detail".equals(action)) {

            int id = Integer.parseInt(
                    req.getParameter("id")
            );

            req.setAttribute(
                    "sanPham",
                    repo.findById(id)
            );

            req.getRequestDispatcher(
                    "/views/sanpham/detail.jsp"
            ).forward(req, resp);

            return;
        }

        if ("delete".equals(action)) {

            int id = Integer.parseInt(
                    req.getParameter("id")
            );

            repo.delete(id);

            resp.sendRedirect(
                    req.getContextPath() + "/san-pham"
            );

            return;
        }

        String keyword =
                req.getParameter("keyword");

        List<SanPham> all =
                repo.search(keyword);

        int page = 1;

        try {
            page = Integer.parseInt(
                    req.getParameter("page")
            );
        } catch (Exception ignored) {
        }

        if (page < 1) {
            page = 1;
        }

        int pageSize = 5;

        List<SanPham> ds =
                PaginationUtil.paginate(
                        all,
                        page,
                        pageSize
                );

        int totalPages =
                PaginationUtil.totalPages(
                        all.size(),
                        pageSize
                );

        req.setAttribute("dsSanPham", ds);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("keyword", keyword);

        req.getRequestDispatcher(
                "/views/sanpham/list.jsp"
        ).forward(req, resp);

        req.getRequestDispatcher(
                "/views/sanpham/list.jsp"
        ).forward(req, resp);
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");

        int id = (idStr == null || idStr.isBlank())
                ? 0
                : Integer.parseInt(idStr);

        String ma = req.getParameter("ma");
        String ten = req.getParameter("ten");
        String moTa = req.getParameter("moTa");

        double gia;
        int soLuong;

        try {
            gia = Double.parseDouble(
                    req.getParameter("gia")
            );

            soLuong = Integer.parseInt(
                    req.getParameter("soLuong")
            );
        } catch (NumberFormatException e) {

            resp.sendRedirect(
                    req.getContextPath()
                            + "/san-pham?action=new&error=invalid"
            );

            return;
        }

        // Validate giá
        if (gia <= 0) {

            resp.sendRedirect(
                    req.getContextPath()
                            + "/san-pham?action=new&error=gia"
            );

            return;
        }

        // Validate số lượng
        if (soLuong < 0) {

            resp.sendRedirect(
                    req.getContextPath()
                            + "/san-pham?action=new&error=soLuong"
            );

            return;
        }

        SanPham sp = new SanPham(
                id,
                ma,
                ten,
                moTa,
                gia,
                soLuong
        );

        if (id == 0) {
            repo.add(sp);
        } else {
            repo.update(sp);
        }

        resp.sendRedirect(
                req.getContextPath() + "/san-pham"
        );
    }
}