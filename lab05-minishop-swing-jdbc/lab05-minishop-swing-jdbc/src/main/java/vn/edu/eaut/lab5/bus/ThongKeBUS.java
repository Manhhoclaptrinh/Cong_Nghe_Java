package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeBUS {
    private final ThongKeDAL dal = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        if (tuNgay == null || denNgay == null) throw new IllegalArgumentException("Ngay khong hop le");
        if (tuNgay.isAfter(denNgay)) throw new IllegalArgumentException("Tu ngay phai <= den ngay");
        return dal.tinhDoanhThu(tuNgay, denNgay);
    }

    public String hoaDonCaoNhat() throws SQLException { return dal.hoaDonCaoNhat(); }
    public String sanPhamBanChay() throws SQLException { return dal.sanPhamBanChay(); }
}
