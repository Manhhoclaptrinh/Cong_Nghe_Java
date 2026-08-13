package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.KhachHang;
import java.sql.*;
import java.util.*;

public class KhachHangDAL {
    private KhachHang map(ResultSet r)throws SQLException{return new KhachHang(r.getInt("ma_kh"),r.getString("ten_kh"),r.getString("sdt"),r.getString("dia_chi"));}
    public List<KhachHang> findAll()throws SQLException{return search(null,null,null,0,100000,"ma_kh","ASC");}
    public List<KhachHang> search(String name,String sdt,String address,int offset,int limit,String sort,String dir)throws SQLException{
        if(!Set.of("ma_kh","ten_kh","sdt","dia_chi").contains(sort))sort="ma_kh";
        dir="DESC".equalsIgnoreCase(dir)?"DESC":"ASC";
        String sql="SELECT ma_kh,ten_kh,sdt,dia_chi FROM khach_hang WHERE 1=1";List<Object>a=new ArrayList<>();
        if(name!=null&&!name.isBlank()){sql+=" AND ten_kh LIKE ?";a.add("%"+name.trim()+"%");}
        if(sdt!=null&&!sdt.isBlank()){sql+=" AND sdt LIKE ?";a.add("%"+sdt.trim()+"%");}
        if(address!=null&&!address.isBlank()){sql+=" AND dia_chi LIKE ?";a.add("%"+address.trim()+"%");}
        sql+=" ORDER BY "+sort+" "+dir+" LIMIT ? OFFSET ?";a.add(limit);a.add(offset);
        List<KhachHang> l=new ArrayList<>();
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            for(int i=0;i<a.size();i++)p.setObject(i+1,a.get(i));
            try(ResultSet r=p.executeQuery()){while(r.next())l.add(map(r));}
        }return l;
    }
    public int count(String name,String sdt,String address)throws SQLException{
        String sql="SELECT COUNT(*) FROM khach_hang WHERE 1=1";List<Object>a=new ArrayList<>();
        if(name!=null&&!name.isBlank()){sql+=" AND ten_kh LIKE ?";a.add("%"+name.trim()+"%");}
        if(sdt!=null&&!sdt.isBlank()){sql+=" AND sdt LIKE ?";a.add("%"+sdt.trim()+"%");}
        if(address!=null&&!address.isBlank()){sql+=" AND dia_chi LIKE ?";a.add("%"+address.trim()+"%");}
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){for(int i=0;i<a.size();i++)p.setObject(i+1,a.get(i));try(ResultSet r=p.executeQuery()){r.next();return r.getInt(1);}}
    }
    public boolean insert(KhachHang k)throws SQLException{try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO khach_hang(ten_kh,sdt,dia_chi) VALUES(?,?,?)")){p.setString(1,k.getTenKh());p.setString(2,k.getSdt());p.setString(3,k.getDiaChi());return p.executeUpdate()>0;}}
    public boolean update(KhachHang k)throws SQLException{try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("UPDATE khach_hang SET ten_kh=?,sdt=?,dia_chi=? WHERE ma_kh=?")){p.setString(1,k.getTenKh());p.setString(2,k.getSdt());p.setString(3,k.getDiaChi());p.setInt(4,k.getMaKh());return p.executeUpdate()>0;}}
    public boolean delete(int id)throws SQLException{try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM khach_hang WHERE ma_kh=?")){p.setInt(1,id);return p.executeUpdate()>0;}}
}
