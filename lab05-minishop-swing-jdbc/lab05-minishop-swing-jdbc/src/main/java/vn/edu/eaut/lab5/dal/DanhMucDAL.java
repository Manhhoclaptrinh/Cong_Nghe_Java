package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.DanhMuc;
import java.sql.*;
import java.util.*;

public class DanhMucDAL {
    private DanhMuc map(ResultSet rs) throws SQLException {
        return new DanhMuc(rs.getInt("ma_dm"), rs.getString("ten_dm"));
    }
    public List<DanhMuc> findAll() throws SQLException {
        List<DanhMuc> list=new ArrayList<>();
        String sql="SELECT ma_dm,ten_dm FROM danh_muc ORDER BY ma_dm";
        try(Connection c=DBHelper.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
            while(r.next()) list.add(map(r));
        }
        return list;
    }
    public boolean insert(DanhMuc dm) throws SQLException {
        try(Connection c=DBHelper.getConnection(); PreparedStatement p=c.prepareStatement("INSERT INTO danh_muc(ten_dm) VALUES(?)")){
            p.setString(1,dm.getTenDm()); return p.executeUpdate()>0;
        }
    }
    public boolean update(DanhMuc dm) throws SQLException {
        try(Connection c=DBHelper.getConnection(); PreparedStatement p=c.prepareStatement("UPDATE danh_muc SET ten_dm=? WHERE ma_dm=?")){
            p.setString(1,dm.getTenDm()); p.setInt(2,dm.getMaDm()); return p.executeUpdate()>0;
        }
    }
    public boolean delete(int maDm) throws SQLException {
        String check="SELECT COUNT(*) FROM san_pham WHERE ma_dm=?";
        try(Connection c=DBHelper.getConnection(); PreparedStatement p=c.prepareStatement(check)){
            p.setInt(1,maDm); try(ResultSet r=p.executeQuery()){ r.next(); if(r.getInt(1)>0) throw new SQLException("Khong the xoa danh muc vi dang co san pham thuoc danh muc."); }
        }
        try(Connection c=DBHelper.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM danh_muc WHERE ma_dm=?")){
            p.setInt(1,maDm); return p.executeUpdate()>0;
        }
    }
}
