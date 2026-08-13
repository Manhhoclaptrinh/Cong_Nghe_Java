package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.DanhMucDAL;
import vn.edu.eaut.lab5.model.DanhMuc;
import java.sql.SQLException;
import java.util.List;

public class DanhMucBUS {
    private final DanhMucDAL dal=new DanhMucDAL();
    public List<DanhMuc> findAll() throws SQLException{return dal.findAll();}
    public boolean save(DanhMuc dm) throws SQLException{
        if(dm==null || dm.getTenDm()==null || dm.getTenDm().trim().isEmpty()) throw new IllegalArgumentException("Ten danh muc khong duoc rong");
        return dm.getMaDm()==0 ? dal.insert(dm) : dal.update(dm);
    }
    public boolean delete(int id) throws SQLException{
        if(id<=0) throw new IllegalArgumentException("Ma danh muc khong hop le");
        return dal.delete(id);
    }
}
