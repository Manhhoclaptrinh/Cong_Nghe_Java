package vn.edu.eaut.lab7.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.Sach;
import vn.edu.eaut.lab7.repository.SachRepository;
import vn.edu.eaut.lab7.util.PaginationUtil;

@WebServlet("/sach")
public class SachController extends HttpServlet {

    private final SachRepository repo = new SachRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.getRequestDispatcher("/views/sach/form.jsp")
                    .forward(req, resp);
            return;
        }

        if ("edit".equals(action) || "detail".equals(action)) {

            int id = Integer.parseInt(req.getParameter("id"));

            req.setAttribute("sach", repo.findById(id));

            String page = "detail".equals(action)
                    ? "detail.jsp"
                    : "form.jsp";

            req.getRequestDispatcher("/views/sach/" + page)
                    .forward(req, resp);

            return;
        }

        if ("delete".equals(action)) {

            int id = Integer.parseInt(req.getParameter("id"));

            repo.delete(id);

            resp.sendRedirect(req.getContextPath() + "/sach");
            return;
        }

        String keyword = req.getParameter("keyword");

        List<Sach> all = repo.search(keyword);

        int page = 1;

        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception ignored) {
        }

        if (page < 1) {
            page = 1;
        }

        int size = 5;

        req.setAttribute(
                "dsSach",
                PaginationUtil.paginate(all, page, size)
        );

        req.setAttribute("currentPage", page);

        req.setAttribute(
                "totalPages",
                PaginationUtil.totalPages(all.size(), size)
        );

        req.setAttribute("keyword", keyword);

        req.getRequestDispatcher("/views/sach/list.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {

            String idS = req.getParameter("id");

            int id = (idS == null || idS.isBlank())
                    ? 0
                    : Integer.parseInt(idS);

            String maSach = req.getParameter("maSach");
            String tenSach = req.getParameter("tenSach");
            String tacGia = req.getParameter("tacGia");
            String nhaXuatBan = req.getParameter("nhaXuatBan");

            int namXuatBan = Integer.parseInt(
                    req.getParameter("namXuatBan")
            );

            Sach sach = new Sach(
                    id,
                    maSach,
                    tenSach,
                    tacGia,
                    nhaXuatBan,
                    namXuatBan
            );

            if (id == 0) {
                repo.add(sach);
            } else {
                repo.update(sach);
            }

            resp.sendRedirect(req.getContextPath() + "/sach");

        } catch (Exception e) {

            e.printStackTrace();

            resp.sendRedirect(
                    req.getContextPath() + "/sach?error=invalid"
            );
        }
    }
}