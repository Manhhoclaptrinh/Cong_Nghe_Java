package vn.edu.eaut.lab7.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.LopHoc;
import vn.edu.eaut.lab7.repository.LopHocRepository;

@WebServlet("/lop-hoc")
public class LopHocController extends HttpServlet {

    private final LopHocRepository repo =
            new LopHocRepository();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if ("new".equals(action)) {

            req.getRequestDispatcher(
                    "/views/lophoc/form.jsp"
            ).forward(req, resp);

            return;
        }

        if ("edit".equals(action)) {

            int id = Integer.parseInt(
                    req.getParameter("id")
            );

            req.setAttribute(
                    "lopHoc",
                    repo.findById(id)
            );

            req.getRequestDispatcher(
                    "/views/lophoc/form.jsp"
            ).forward(req, resp);

            return;
        }

        if ("detail".equals(action)) {

            int id = Integer.parseInt(
                    req.getParameter("id")
            );

            req.setAttribute(
                    "lopHoc",
                    repo.findById(id)
            );

            req.getRequestDispatcher(
                    "/views/lophoc/detail.jsp"
            ).forward(req, resp);

            return;
        }

        if ("delete".equals(action)) {

            int id = Integer.parseInt(
                    req.getParameter("id")
            );

            repo.delete(id);

            resp.sendRedirect(
                    req.getContextPath() + "/lop-hoc"
            );

            return;
        }

        String keyword =
                req.getParameter("keyword");

        req.setAttribute(
                "dsLopHoc",
                repo.search(keyword)
        );

        req.getRequestDispatcher(
                "/views/lophoc/list.jsp"
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

        int soLuong = Integer.parseInt(
                req.getParameter("soLuongSinhVien")
        );

        if (soLuong < 0) {
            resp.sendRedirect(
                    req.getContextPath()
                            + "/lop-hoc?action=new&error=soLuong"
            );
            return;
        }

        LopHoc lopHoc = new LopHoc(
                id,
                req.getParameter("maLop"),
                req.getParameter("tenLop"),
                req.getParameter("coVanHocTap"),
                soLuong
        );

        if (id == 0) {
            repo.add(lopHoc);
        } else {
            repo.update(lopHoc);
        }

        resp.sendRedirect(
                req.getContextPath() + "/lop-hoc"
        );
    }
}