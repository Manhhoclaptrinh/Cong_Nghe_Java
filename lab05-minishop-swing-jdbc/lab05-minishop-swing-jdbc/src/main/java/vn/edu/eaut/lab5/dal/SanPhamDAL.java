package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.SanPham;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class SanPhamDAL {
    private SanPham map(ResultSet r)throws SQLException{
        return new SanPham(r.getInt("ma_sp"),r.getString("ten_sp"),r.getBigDecimal("don_gia"),
                r.getInt("so_luong"),r.getInt("ma_dm"),r.getString("ten_dm"));
    }
    private String base(){return " FROM san_pham sp LEFT JOIN danh_muc dm ON sp.ma_dm=dm.ma_dm WHERE 1=1 ";}
    public List<SanPham> findAll()throws SQLException{return search(null,null,null,null,null,null,0,100000,"ma_sp","ASC");}
    public List<SanPham> searchByName(String k)throws SQLException{return search(k,null,null,null,null,null,0,100000,"ma_sp","ASC");}
    public List<SanPham> search(String name,BigDecimal min,BigDecimal max,Integer minQty,Integer maxQty,Integer maDm,
                                int offset,int limit,String sort,String dir)throws SQLException{
        String allowed="ma_sp,ten_sp,don_gia,so_luong";
        if(!allowed.contains(sort))sort="ma_sp";
        dir="DESC".equalsIgnoreCase(dir)?"DESC":"ASC";
        String sql="SELECT sp.ma_sp,sp.ten_sp,sp.don_gia,sp.so_luong,sp.ma_dm,dm.ten_dm"+base();
        List<Object> args=new ArrayList<>();
        if(name!=null&&!name.isBlank()){sql+=" AND sp.ten_sp LIKE ?";args.add("%"+name.trim()+"%");}
        if(min!=null){sql+=" AND sp.don_gia>=?";args.add(min);}
        if(max!=null){sql+=" AND sp.don_gia<=?";args.add(max);}
        if(minQty!=null){sql+=" AND sp.so_luong>=?";args.add(minQty);}
        if(maxQty!=null){sql+=" AND sp.so_luong<=?";args.add(maxQty);}
        if(maDm!=null&&maDm>0){sql+=" AND sp.ma_dm=?";args.add(maDm);}
        sql+=" ORDER BY sp."+sort+" "+dir+" LIMIT ? OFFSET ?";
        args.add(limit);args.add(offset);
        List<SanPham> list=new ArrayList<>();
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            for(int i=0;i<args.size();i++)p.setObject(i+1,args.get(i));
            try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}
        }return list;
    }
    public int count(String name,BigDecimal min,BigDecimal max,Integer minQty,Integer maxQty,Integer maDm)throws SQLException{
        String sql="SELECT COUNT(*)"+base();List<Object> args=new ArrayList<>();
        if(name!=null&&!name.isBlank()){sql+=" AND sp.ten_sp LIKE ?";args.add("%"+name.trim()+"%");}
        if(min!=null){sql+=" AND sp.don_gia>=?";args.add(min);}
        if(max!=null){sql+=" AND sp.don_gia<=?";args.add(max);}
        if(minQty!=null){sql+=" AND sp.so_luong>=?";args.add(minQty);}
        if(maxQty!=null){sql+=" AND sp.so_luong<=?";args.add(maxQty);}
        if(maDm!=null&&maDm>0){sql+=" AND sp.ma_dm=?";args.add(maDm);}
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            for(int i=0;i<args.size();i++)p.setObject(i+1,args.get(i));
            try(ResultSet r=p.executeQuery()){r.next();return r.getInt(1);}
        }
    }
    public boolean insert(SanPham sp)throws SQLException{
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO san_pham(ten_sp,don_gia,so_luong,ma_dm) VALUES(?,?,?,?)")){
            p.setString(1,sp.getTenSp());p.setBigDecimal(2,sp.getDonGia());p.setInt(3,sp.getSoLuong());
            if(sp.getMaDm()>0)p.setInt(4,sp.getMaDm());else p.setNull(4,Types.INTEGER);return p.executeUpdate()>0;
        }
    }
    public boolean update(SanPham sp)throws SQLException{
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("UPDATE san_pham SET ten_sp=?,don_gia=?,so_luong=?,ma_dm=? WHERE ma_sp=?")){
            p.setString(1,sp.getTenSp());p.setBigDecimal(2,sp.getDonGia());p.setInt(3,sp.getSoLuong());
            if(sp.getMaDm()>0)p.setInt(4,sp.getMaDm());else p.setNull(4,Types.INTEGER);p.setInt(5,sp.getMaSp());return p.executeUpdate()>0;
        }
    }
    public boolean delete(int id)throws SQLException{
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM san_pham WHERE ma_sp=?")){
            p.setInt(1,id);return p.executeUpdate()>0;
        }
    }
    public int getStock(int id)throws SQLException{
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("SELECT so_luong FROM san_pham WHERE ma_sp=?")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Khong tim thay san pham");return r.getInt(1);}
        }
    }
}
