package vn.edu.eaut.lab5.util;

import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public final class HoaDonExporter {
    private HoaDonExporter(){}
    public static Path exportTxt(HoaDon hd,KhachHang kh,List<ChiTietHoaDon> details)throws IOException{
        Path p=Paths.get("HoaDon_"+hd.getMaHd()+".txt");
        StringBuilder b=new StringBuilder();
        b.append("HOA DON BAN HANG\n").append("Ma hoa don: ").append(hd.getMaHd()).append('\n');
        b.append("Ngay lap: ").append(hd.getNgayLap()).append('\n');
        b.append("Khach hang: ").append(kh.getTenKh()).append(" | SDT: ").append(kh.getSdt()).append('\n');
        b.append("----------------------------------------\n");
        b.append("San pham\tSo luong\tDon gia\tThanh tien\n");
        for(ChiTietHoaDon c:details)b.append(c.getTenSp()).append('\t').append(c.getSoLuong()).append('\t').append(c.getDonGia()).append('\t').append(c.getThanhTien()).append('\n');
        b.append("----------------------------------------\nTong tien: ").append(hd.getTongTien()).append(" VND\n");
        Files.writeString(p,b.toString(),StandardCharsets.UTF_8);return p;
    }
    public static Path exportCsv(HoaDon hd,KhachHang kh,List<ChiTietHoaDon> details)throws IOException{
        Path p=Paths.get("HoaDon_"+hd.getMaHd()+".csv");
        StringBuilder b=new StringBuilder("\uFEFF");
        b.append("Ma hoa don,").append(hd.getMaHd()).append('\n');
        b.append("Ngay lap,").append(hd.getNgayLap()).append('\n');
        b.append("Khach hang,").append(csv(kh.getTenKh())).append('\n');
        b.append("So dien thoai,").append(csv(kh.getSdt())).append('\n');
        b.append("Ten san pham,So luong,Don gia,Thanh tien\n");
        for(ChiTietHoaDon c:details)b.append(csv(c.getTenSp())).append(',').append(c.getSoLuong()).append(',').append(c.getDonGia()).append(',').append(c.getThanhTien()).append('\n');
        b.append("Tong tien,,,").append(hd.getTongTien()).append('\n');
        Files.writeString(p,b.toString(),StandardCharsets.UTF_8);return p;
    }
    private static String csv(String s){return "\""+(s==null?"":s.replace("\"","\"\""))+"\"";}
}
