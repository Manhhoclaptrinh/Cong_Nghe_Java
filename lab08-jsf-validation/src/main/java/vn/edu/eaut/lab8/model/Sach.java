package vn.edu.eaut.lab8.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Sach {

    private int id;

    private String maSach;

    @NotBlank(message = "Tên sách không được để trống")
    private String tenSach;

    @NotBlank(message = "Tác giả không được để trống")
    private String tacGia;

    private String nhaXuatBan;

    @Min(value = 1000, message = "Năm xuất bản không hợp lệ")
    @Max(value = 2026, message = "Năm xuất bản không được lớn hơn 2026")
    private int namXuatBan;

    public Sach() {
    }

    public Sach(
            int id,
            String maSach,
            String tenSach,
            String tacGia,
            String nhaXuatBan,
            int namXuatBan) {

        this.id = id;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }
}