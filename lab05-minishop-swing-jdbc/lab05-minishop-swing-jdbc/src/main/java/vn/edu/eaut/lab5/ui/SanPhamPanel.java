package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.DanhMuc;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class SanPhamPanel extends JPanel {
    private final SanPhamBUS bus=new SanPhamBUS(); private final DanhMucBUS dmBus=new DanhMucBUS();
    private final JTextField txtMa=new JTextField(),txtTen=new JTextField(),txtGia=new JTextField(),txtSoLuong=new JTextField();
    private final JTextField txtMinGia=new JTextField(),txtMaxGia=new JTextField(),txtMinSL=new JTextField(),txtMaxSL=new JTextField(),txtTim=new JTextField();
    private final JComboBox<DanhMuc> cboDm=new JComboBox<>(),cboFilterDm=new JComboBox<>(); private final JComboBox<String> cboSort=new JComboBox<>(new String[]{"ma_sp","ten_sp","don_gia","so_luong"});
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Ma SP","Ten san pham","Don gia","So luong","Danh muc"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model); private final JLabel lblPage=new JLabel("Trang 1/1"); private int page=0; private static final int SIZE=10; private String dir="ASC";

    public SanPhamPanel(){setLayout(new BorderLayout(8,8));txtMa.setEditable(false);
        JPanel form=new JPanel(new GridLayout(2,6,6,5));form.setBorder(BorderFactory.createTitledBorder("Thong tin san pham"));
        form.add(new JLabel("Ma SP"));form.add(txtMa);form.add(new JLabel("Ten SP"));form.add(txtTen);form.add(new JLabel("Don gia"));form.add(txtGia);
        form.add(new JLabel("So luong"));form.add(txtSoLuong);form.add(new JLabel("Danh muc"));form.add(cboDm);JButton qldm=new JButton("Quan ly danh muc");form.add(qldm);form.add(new JLabel(""));

        JPanel search=new JPanel(new GridLayout(2,6,5,5));search.setBorder(BorderFactory.createTitledBorder("Tim kiem nang cao"));
        search.add(new JLabel("Ten"));search.add(txtTim);search.add(new JLabel("Gia tu"));search.add(txtMinGia);search.add(new JLabel("Gia den"));search.add(txtMaxGia);
        search.add(new JLabel("SL tu"));search.add(txtMinSL);search.add(new JLabel("SL den"));search.add(txtMaxSL);search.add(new JLabel("Danh muc"));search.add(cboFilterDm);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT));JButton searchBtn=new JButton("Tim kiem");JButton add=new JButton("Them");JButton edit=new JButton("Sua");JButton del=new JButton("Xoa");JButton clear=new JButton("Lam moi");actions.add(searchBtn);actions.add(add);actions.add(edit);actions.add(del);actions.add(clear);actions.add(new JLabel("Sap xep"));actions.add(cboSort);JButton order=new JButton("Tang/Giam");actions.add(order);
        JPanel top=new JPanel(new BorderLayout(5,5));top.add(form,BorderLayout.NORTH);top.add(search,BorderLayout.CENTER);top.add(actions,BorderLayout.SOUTH);add(top,BorderLayout.NORTH);
        table.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){public Component getTableCellRendererComponent(JTable t,Object v,boolean s,boolean f,int r,int c){Component x=super.getTableCellRendererComponent(t,v,s,f,r,c);Object qty=t.getValueAt(r,3);if(!s&&qty instanceof Number&&((Number)qty).intValue()<5)x.setFont(x.getFont().deriveFont(Font.BOLD));return x;}});
        add(new JScrollPane(table),BorderLayout.CENTER);
        JPanel nav=new JPanel();JButton first=new JButton("Dau"),prev=new JButton("Truoc"),next=new JButton("Sau"),last=new JButton("Cuoi");nav.add(first);nav.add(prev);nav.add(lblPage);nav.add(next);nav.add(last);add(nav,BorderLayout.SOUTH);
        qldm.addActionListener(e->{new DanhMucDialog(SwingUtilities.getWindowAncestor(this)).setVisible(true);loadDm();});
        searchBtn.addActionListener(e->{page=0;load();});add.addActionListener(e->save(0));edit.addActionListener(e->save(selected()));del.addActionListener(e->delete());clear.addActionListener(e->{clearForm();page=0;load();});order.addActionListener(e->{dir="ASC".equals(dir)?"DESC":"ASC";load();});
        first.addActionListener(e->{page=0;load();});prev.addActionListener(e->{if(page>0)page--;load();});next.addActionListener(e->{page++;load();});last.addActionListener(e->{page=Math.max(0,totalPages()-1);load();});
        table.getSelectionModel().addListSelectionListener(e->fill());loadDm();load();
    }
    private void loadDm(){try{cboDm.removeAllItems();cboDm.addItem(new DanhMuc(0,"Khong chon"));for(DanhMuc d:dmBus.findAll())cboDm.addItem(d);JComboBox<DanhMuc> f=cboFilterDm;DanhMuc old=(DanhMuc)f.getSelectedItem();f.removeAllItems();f.addItem(new DanhMuc(0,"Tat ca"));for(DanhMuc d:dmBus.findAll())f.addItem(d);if(old!=null)for(int i=0;i<f.getItemCount();i++)if(f.getItemAt(i).getMaDm()==old.getMaDm())f.setSelectedIndex(i);}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private int selected(){int r=table.getSelectedRow();return r<0?0:Integer.parseInt(model.getValueAt(r,0).toString());}
    private void fill(){int r=table.getSelectedRow();if(r<0)return;txtMa.setText(model.getValueAt(r,0).toString());txtTen.setText(model.getValueAt(r,1).toString());txtGia.setText(model.getValueAt(r,2).toString());txtSoLuong.setText(model.getValueAt(r,3).toString());String dm=String.valueOf(model.getValueAt(r,4));for(int i=0;i<cboDm.getItemCount();i++)if(cboDm.getItemAt(i).getTenDm()!=null&&cboDm.getItemAt(i).getTenDm().equals(dm))cboDm.setSelectedIndex(i);}
    private void save(int id){try{SanPham s=new SanPham();s.setMaSp(id);s.setTenSp(txtTen.getText().trim());s.setDonGia(new BigDecimal(txtGia.getText().trim()));s.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));DanhMuc d=(DanhMuc)cboDm.getSelectedItem();s.setMaDm(d==null?0:d.getMaDm());bus.save(s);MessageUtil.info(this,id==0?"Them san pham thanh cong":"Sua san pham thanh cong");clearForm();load();}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private void delete(){int id=selected();if(id<=0){MessageUtil.error(this,"Chon san pham");return;}if(!MessageUtil.confirm(this,"Ban co chac muon xoa?"))return;try{bus.delete(id);clearForm();load();}catch(Exception e){MessageUtil.error(this,"Khong xoa duoc: "+e.getMessage());}}
    private void clearForm(){txtMa.setText("");txtTen.setText("");txtGia.setText("");txtSoLuong.setText("");table.clearSelection();}
    private Integer intOrNull(String s){return s.isBlank()?null:Integer.valueOf(s.trim());} private BigDecimal decOrNull(String s){return s.isBlank()?null:new BigDecimal(s.trim());}
    private int totalPages(){try{DanhMuc d=(DanhMuc)cboFilterDm.getSelectedItem();return Math.max(1,(bus.count(txtTim.getText(),decOrNull(txtMinGia.getText()),decOrNull(txtMaxGia.getText()),intOrNull(txtMinSL.getText()),intOrNull(txtMaxSL.getText()),d==null?0:d.getMaDm())+SIZE-1)/SIZE);}catch(Exception e){return 1;}}
    private void load(){try{DanhMuc d=(DanhMuc)cboFilterDm.getSelectedItem();int total=bus.count(txtTim.getText(),decOrNull(txtMinGia.getText()),decOrNull(txtMaxGia.getText()),intOrNull(txtMinSL.getText()),intOrNull(txtMaxSL.getText()),d==null?0:d.getMaDm());int pages=Math.max(1,(total+SIZE-1)/SIZE);if(page>=pages)page=pages-1;List<SanPham> l=bus.search(txtTim.getText(),decOrNull(txtMinGia.getText()),decOrNull(txtMaxGia.getText()),intOrNull(txtMinSL.getText()),intOrNull(txtMaxSL.getText()),d==null?0:d.getMaDm(),page,SIZE,String.valueOf(cboSort.getSelectedItem()),dir);model.setRowCount(0);for(SanPham s:l)model.addRow(new Object[]{s.getMaSp(),s.getTenSp(),s.getDonGia(),s.getSoLuong(),s.getTenDm()==null?"":s.getTenDm()});lblPage.setText("Trang "+(page+1)+"/"+pages+" | Canh bao: ton < 5");}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
}
