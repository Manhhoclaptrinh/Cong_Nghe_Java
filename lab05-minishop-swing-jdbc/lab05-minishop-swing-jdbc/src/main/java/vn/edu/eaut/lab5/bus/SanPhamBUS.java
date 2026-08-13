package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.SanPhamDAL;
import vn.edu.eaut.lab5.model.SanPham;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SanPhamBUS {
    private final SanPhamDAL dal=new SanPhamDAL();
    public List<SanPham> findAll()throws SQLException{return dal.findAll();}
    public List<SanPham> searchByName(String k)throws SQLException{return dal.searchByName(k);}
    public List<SanPham> search(String name,BigDecimal min,BigDecimal max,Integer minQ,Integer maxQ,Integer maDm,int page,int size,String sort,String dir)throws SQLException{
        return dal.search(name,min,max,minQ,maxQ,maDm,Math.max(0,page)*size,size,sort,dir);
    }
    public int count(String name,BigDecimal min,BigDecimal max,Integer minQ,Integer maxQ,Integer maDm)throws SQLException{return dal.count(name,min,max,minQ,maxQ,maDm);}
    public boolean save(SanPham sp)throws SQLException{validate(sp);return sp.getMaSp()==0?dal.insert(sp):dal.update(sp);}
    public boolean delete(int id)throws SQLException{if(id<=0)throw new IllegalArgumentException("Ma san pham khong hop le");return dal.delete(id);}
    public int getStock(int id)throws SQLException{return dal.getStock(id);}
    private void validate(SanPham sp){
        if(sp.getTenSp()==null||sp.getTenSp().trim().isEmpty())throw new IllegalArgumentException("Ten san pham khong duoc rong");
        if(sp.getDonGia()==null||sp.getDonGia().compareTo(BigDecimal.ZERO)<=0)throw new IllegalArgumentException("Don gia phai lon hon 0");
        if(sp.getSoLuong()<0)throw new IllegalArgumentException("So luong khong duoc am");
    }
}
