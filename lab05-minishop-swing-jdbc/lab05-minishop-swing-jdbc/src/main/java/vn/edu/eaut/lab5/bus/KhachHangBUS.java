package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.KhachHangDAL;
import vn.edu.eaut.lab5.model.KhachHang;
import java.sql.SQLException;
import java.util.List;

public class KhachHangBUS {
    private final KhachHangDAL dal=new KhachHangDAL();
    public List<KhachHang> findAll()throws SQLException{return dal.findAll();}
    public List<KhachHang> search(String k)throws SQLException{return dal.search(k,k,null,0,100000,"ma_kh","ASC");}
    public List<KhachHang> searchAdvanced(String n,String s,String a,int page,int size,String sort,String dir)throws SQLException{return dal.search(n,s,a,Math.max(0,page)*size,size,sort,dir);}
    public int count(String n,String s,String a)throws SQLException{return dal.count(n,s,a);}
    public boolean save(KhachHang k)throws SQLException{validate(k);return k.getMaKh()==0?dal.insert(k):dal.update(k);}
    public boolean delete(int id)throws SQLException{if(id<=0)throw new IllegalArgumentException("Ma khach hang khong hop le");return dal.delete(id);}
    private void validate(KhachHang k){if(k.getTenKh()==null||k.getTenKh().trim().isEmpty())throw new IllegalArgumentException("Ten khach hang khong duoc rong");if(k.getSdt()==null||!k.getSdt().matches("\\d{1,10}"))throw new IllegalArgumentException("So dien thoai chi gom so va toi da 10 ky tu");}
}
