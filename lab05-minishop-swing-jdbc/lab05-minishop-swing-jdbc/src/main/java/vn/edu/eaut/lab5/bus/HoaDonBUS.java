package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HoaDonBUS {
    private final HoaDonDAL dal=new HoaDonDAL();
    public int save(int maKh,List<ChiTietHoaDon> list)throws SQLException{
        if(maKh<=0)throw new IllegalArgumentException("Vui long chon khach hang");
        if(list==null||list.isEmpty())throw new IllegalArgumentException("Hoa don chua co san pham");
        for(ChiTietHoaDon ct:list){if(ct.getSoLuong()<=0)throw new IllegalArgumentException("So luong phai lon hon 0");if(ct.getDonGia()==null)throw new IllegalArgumentException("Don gia khong hop le");}
        return dal.insertHoaDon(maKh,list);
    }
    public List<HoaDon> findAll()throws SQLException{return dal.findAll();}
    public List<HoaDon> search(LocalDate f,LocalDate t,Integer k,BigDecimal min,BigDecimal max,int page,int size,String sort,String dir)throws SQLException{return dal.search(f,t,k,min,max,Math.max(0,page)*size,size,sort,dir);}
    public int count(LocalDate f,LocalDate t,Integer k,BigDecimal min,BigDecimal max)throws SQLException{return dal.count(f,t,k,min,max);}
    public List<ChiTietHoaDon> findDetails(int id)throws SQLException{return dal.findDetails(id);}
    public HoaDon findOne(int id)throws SQLException{return dal.findOne(id);}
}
