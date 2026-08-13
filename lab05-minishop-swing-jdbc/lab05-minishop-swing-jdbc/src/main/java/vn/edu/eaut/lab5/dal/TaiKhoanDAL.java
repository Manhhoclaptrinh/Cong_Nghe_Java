package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.TaiKhoan;
import java.sql.*;
import java.util.*;

public class TaiKhoanDAL {
    private TaiKhoan map(ResultSet r)throws SQLException{
        return new TaiKhoan(r.getString("username"),r.getString("password"),r.getString("ho_ten"),r.getString("vai_tro"));
    }
    public TaiKhoan login(String u,String p)throws SQLException{
        String sql="SELECT username,password,ho_ten,vai_tro FROM tai_khoan WHERE username=? AND password=?";
        try(Connection c=DBHelper.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,u);ps.setString(2,p);
            try(ResultSet r=ps.executeQuery()){return r.next()?map(r):null;}
        }
    }
    public List<TaiKhoan> findAll()throws SQLException{
        List<TaiKhoan> l=new ArrayList<>();
        try(Connection c=DBHelper.getConnection();PreparedStatement ps=c.prepareStatement("SELECT username,password,ho_ten,vai_tro FROM tai_khoan ORDER BY username");ResultSet r=ps.executeQuery()){
            while(r.next())l.add(map(r));
        } return l;
    }
    public boolean save(TaiKhoan a)throws SQLException{
        try(Connection c=DBHelper.getConnection();PreparedStatement ps=c.prepareStatement(
                "INSERT INTO tai_khoan(username,password,ho_ten,vai_tro) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE password=VALUES(password),ho_ten=VALUES(ho_ten),vai_tro=VALUES(vai_tro)")){
            ps.setString(1,a.getUsername());ps.setString(2,a.getPassword());ps.setString(3,a.getHoTen());ps.setString(4,a.getVaiTro());return ps.executeUpdate()>0;
        }
    }
    public boolean delete(String u)throws SQLException{
        try(Connection c=DBHelper.getConnection();PreparedStatement ps=c.prepareStatement("DELETE FROM tai_khoan WHERE username=?")){
            ps.setString(1,u);return ps.executeUpdate()>0;
        }
    }
}
