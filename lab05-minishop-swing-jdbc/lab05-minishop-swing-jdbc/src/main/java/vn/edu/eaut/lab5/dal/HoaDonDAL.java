package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class HoaDonDAL {
    public int insertHoaDon(int maKh,List<ChiTietHoaDon> list)throws SQLException{
        if(list==null||list.isEmpty())throw new IllegalArgumentException("Hoa don phai co it nhat mot san pham");
        Connection c=null;
        try{
            c=DBHelper.getConnection();c.setAutoCommit(false);
            BigDecimal total=tinhTong(list);
            int hd;
            try(PreparedStatement p=c.prepareStatement("INSERT INTO hoa_don(ngay_lap,ma_kh,tong_tien) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS)){
                p.setDate(1,java.sql.Date.valueOf(LocalDate.now()));p.setInt(2,maKh);p.setBigDecimal(3,total);p.executeUpdate();
                try(ResultSet r=p.getGeneratedKeys()){if(!r.next())throw new SQLException("Khong lay duoc ma hoa don");hd=r.getInt(1);}
            }
            try(PreparedStatement p=c.prepareStatement("UPDATE san_pham SET so_luong=so_luong-? WHERE ma_sp=? AND so_luong>=?")){
                for(ChiTietHoaDon ct:list){p.setInt(1,ct.getSoLuong());p.setInt(2,ct.getMaSp());p.setInt(3,ct.getSoLuong());if(p.executeUpdate()==0)throw new SQLException("San pham khong du ton kho: "+ct.getTenSp());}
            }
            try(PreparedStatement p=c.prepareStatement("INSERT INTO chi_tiet_hoa_don(ma_hd,ma_sp,so_luong,don_gia,thanh_tien) VALUES(?,?,?,?,?)")){
                for(ChiTietHoaDon ct:list){p.setInt(1,hd);p.setInt(2,ct.getMaSp());p.setInt(3,ct.getSoLuong());p.setBigDecimal(4,ct.getDonGia());p.setBigDecimal(5,ct.getThanhTien());p.addBatch();}p.executeBatch();
            }
            c.commit();return hd;
        }catch(SQLException|RuntimeException e){if(c!=null)try{c.rollback();}catch(SQLException ignored){}throw e;}
        finally{if(c!=null)try{c.setAutoCommit(true);c.close();}catch(SQLException ignored){}}
    }
    private BigDecimal tinhTong(List<ChiTietHoaDon> l){BigDecimal t=BigDecimal.ZERO;for(ChiTietHoaDon c:l)t=t.add(c.getThanhTien());return t;}
    public List<HoaDon> findAll()throws SQLException{return search(null,null,null,null,null,0,100000,"ma_hd","DESC");}
    public List<HoaDon> search(LocalDate from,LocalDate to,Integer maKh,BigDecimal min,BigDecimal max,int offset,int limit,String sort,String dir)throws SQLException{
        if(!Set.of("ma_hd","ngay_lap","ma_kh","tong_tien").contains(sort))sort="ma_hd";
        dir="DESC".equalsIgnoreCase(dir)?"DESC":"ASC";
        String sql="SELECT hd.ma_hd,hd.ngay_lap,hd.ma_kh,kh.ten_kh,kh.sdt,hd.tong_tien FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh=kh.ma_kh WHERE 1=1";
        List<Object>a=new ArrayList<>();
        if(from!=null){sql+=" AND hd.ngay_lap>=?";a.add(java.sql.Date.valueOf(from));}
        if(to!=null){sql+=" AND hd.ngay_lap<=?";a.add(java.sql.Date.valueOf(to));}
        if(maKh!=null&&maKh>0){sql+=" AND hd.ma_kh=?";a.add(maKh);}
        if(min!=null){sql+=" AND hd.tong_tien>=?";a.add(min);}
        if(max!=null){sql+=" AND hd.tong_tien<=?";a.add(max);}
        sql+=" ORDER BY hd."+sort+" "+dir+" LIMIT ? OFFSET ?";a.add(limit);a.add(offset);
        List<HoaDon> l=new ArrayList<>();
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            for(int i=0;i<a.size();i++)p.setObject(i+1,a.get(i));
            try(ResultSet r=p.executeQuery()){while(r.next())l.add(new HoaDon(r.getInt("ma_hd"),r.getDate("ngay_lap").toLocalDate(),r.getInt("ma_kh"),r.getString("ten_kh"),r.getBigDecimal("tong_tien")));}
        }return l;
    }
    public int count(LocalDate from,LocalDate to,Integer maKh,BigDecimal min,BigDecimal max)throws SQLException{
        String sql="SELECT COUNT(*) FROM hoa_don WHERE 1=1";List<Object>a=new ArrayList<>();
        if(from!=null){sql+=" AND ngay_lap>=?";a.add(java.sql.Date.valueOf(from));}if(to!=null){sql+=" AND ngay_lap<=?";a.add(java.sql.Date.valueOf(to));}
        if(maKh!=null&&maKh>0){sql+=" AND ma_kh=?";a.add(maKh);}if(min!=null){sql+=" AND tong_tien>=?";a.add(min);}if(max!=null){sql+=" AND tong_tien<=?";a.add(max);}
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){for(int i=0;i<a.size();i++)p.setObject(i+1,a.get(i));try(ResultSet r=p.executeQuery()){r.next();return r.getInt(1);}}
    }
    public List<ChiTietHoaDon> findDetails(int maHd)throws SQLException{
        String sql="SELECT ct.ma_hd,ct.ma_sp,sp.ten_sp,ct.so_luong,ct.don_gia,ct.thanh_tien FROM chi_tiet_hoa_don ct JOIN san_pham sp ON ct.ma_sp=sp.ma_sp WHERE ct.ma_hd=? ORDER BY ct.ma_sp";
        List<ChiTietHoaDon> l=new ArrayList<>();
        try(Connection c=DBHelper.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,maHd);try(ResultSet r=p.executeQuery()){while(r.next()){ChiTietHoaDon ct=new ChiTietHoaDon();ct.setMaHd(r.getInt("ma_hd"));ct.setMaSp(r.getInt("ma_sp"));ct.setTenSp(r.getString("ten_sp"));ct.setDonGia(r.getBigDecimal("don_gia"));ct.setSoLuong(r.getInt("so_luong"));ct.setThanhTien(r.getBigDecimal("thanh_tien"));l.add(ct);}}}return l;
    }
    public HoaDon findOne(int maHd)throws SQLException{
        List<HoaDon> l=search(null,null,null,null,null,0,100000,"ma_hd","DESC");for(HoaDon h:l)if(h.getMaHd()==maHd)return h;return null;
    }
}
