package vn.edu.eaut.lab8.bean;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.SinhVien;
import vn.edu.eaut.lab8.repository.SinhVienRepository;

@Named("sinhVienBean")
@SessionScoped
public class SinhVienBean implements Serializable {

    private SinhVien sinhVien = new SinhVien();

    private final SinhVienRepository repo =
            new SinhVienRepository();

    private String keyword;

    private boolean editing = false;

    public String save() {

        repo.add(sinhVien);

        addMessage(
            FacesMessage.SEVERITY_INFO,
            "Thành công",
            "Đã lưu sinh viên"
        );

        sinhVien = new SinhVien();

        return "sinhvien-list?faces-redirect=true";
    }

    public void delete(int id) {

        repo.delete(id);

        addMessage(
            FacesMessage.SEVERITY_INFO,
            "Thành công",
            "Đã xóa sinh viên"
        );
    }

    public String edit(int id) {

        SinhVien sv = repo.findById(id);

        if (sv != null) {

            sinhVien = new SinhVien(
                sv.getId(),
                sv.getMaSinhVien(),
                sv.getHoTen(),
                sv.getEmail(),
                sv.getLop()
            );

            editing = true;
        }

        return "sinhvien-form?faces-redirect=true";
    }

    public String update() {

        repo.update(sinhVien);

        addMessage(
            FacesMessage.SEVERITY_INFO,
            "Thành công",
            "Đã cập nhật sinh viên"
        );

        sinhVien = new SinhVien();
        editing = false;

        return "sinhvien-list?faces-redirect=true";
    }

    public List<SinhVien> getDsSinhVien() {

        if (keyword == null || keyword.trim().isEmpty()) {
            return repo.findAll();
        }

        return repo.search(keyword);
    }

    public void search() {
        // JSF sẽ gọi lại getter getDsSinhVien()
    }

    private void addMessage(
            FacesMessage.Severity severity,
            String summary,
            String detail) {

        FacesContext.getCurrentInstance()
                .addMessage(
                    null,
                    new FacesMessage(
                        severity,
                        summary,
                        detail
                    )
                );
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
}