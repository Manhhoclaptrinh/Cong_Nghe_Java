package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.TaiKhoanDAL;
import vn.edu.eaut.lab5.model.TaiKhoan;
import java.sql.SQLException;
import java.util.List;

public class TaiKhoanBUS {
    private final TaiKhoanDAL dal=new TaiKhoanDAL();
    public TaiKhoan login(String u,String p)throws SQLException{
        if(u==null||u.isBlank()||p==null||p.isBlank()) throw new IllegalArgumentException("Vui long nhap day du tai khoan va mat khau");
        return dal.login(u.trim(),p);
    }
    public List<TaiKhoan> findAll()throws SQLException{return dal.findAll();}
    public boolean save(TaiKhoan a)throws SQLException{
        if(a.getUsername()==null||a.getUsername().isBlank())throw new IllegalArgumentException("Username khong duoc rong");
        if(a.getPassword()==null||a.getPassword().isBlank())throw new IllegalArgumentException("Mat khau khong duoc rong");
        if(a.getHoTen()==null||a.getHoTen().isBlank())throw new IllegalArgumentException("Ho ten khong duoc rong");
        if(!a.getVaiTro().matches("ADMIN|NHANVIEN|KETOAN"))throw new IllegalArgumentException("Vai tro khong hop le");
        return dal.save(a);
    }
    public boolean delete(String u)throws SQLException{return dal.delete(u);}
}
