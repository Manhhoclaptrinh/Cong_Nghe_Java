package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.*;
import vn.edu.eaut.lab5.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class HoaDonPanel extends JPanel {
    private final HoaDonBUS hdBus=new HoaDonBUS();private final KhachHangBUS khBus=new KhachHangBUS();private final SanPhamBUS spBus=new SanPhamBUS();
    private final JComboBox<KhachHang> cboKh=new JComboBox<>();private final JComboBox<SanPham> cboSp=new JComboBox<>();private final JTextField txtSL=new JTextField("1");private final JLabel lblTong=new JLabel("Tong tien: 0 VND");
    private final List<ChiTietHoaDon> details=new ArrayList<>();
    private final DefaultTableModel ctModel=new DefaultTableModel(new String[]{"Ma SP","Ten SP","So luong","Don gia","Thanh tien"},0){public boolean isCellEditable(int r,int c){return false;}};private final JTable ctTable=new JTable(ctModel);
    private final DefaultTableModel hdModel=new DefaultTableModel(new String[]{"Ma HD","Ngay lap","Ma KH","Khach hang","Tong tien"},0){public boolean isCellEditable(int r,int c){return false;}};private final JTable hdTable=new JTable(hdModel);
    private final JTextField from=new JTextField(),to=new JTextField(),min=new JTextField(),max=new JTextField();private final JComboBox<KhachHang> filterKh=new JComboBox<>();private final JComboBox<String> sort=new JComboBox<>(new String[]{"ma_hd","ngay_lap","ma_kh","tong_tien"});private String dir="DESC";private int page=0;private static final int SIZE=10;private final JLabel pageLabel=new JLabel("Trang 1/1");

    public HoaDonPanel(){setLayout(new BorderLayout(6,6));
        JPanel create=new JPanel(new BorderLayout(5,5));create.setBorder(BorderFactory.createTitledBorder("Lap hoa don"));
        JPanel in=new JPanel(new GridLayout(2,5,6,5));in.add(new JLabel("Khach hang"));in.add(cboKh);in.add(new JLabel("San pham"));in.add(cboSp);in.add(new JLabel("So luong"));in.add(txtSL);JButton add=new JButton("Them dong"),remove=new JButton("Xoa dong"),save=new JButton("Luu hoa don");in.add(add);in.add(remove);in.add(new JLabel(""));
        create.add(in,BorderLayout.NORTH);create.add(new JScrollPane(ctTable),BorderLayout.CENTER);JPanel bottom=new JPanel(new FlowLayout(FlowLayout.RIGHT));bottom.add(lblTong);bottom.add(save);create.add(bottom,BorderLayout.SOUTH);
        JPanel history=new JPanel(new BorderLayout(5,5));history.setBorder(BorderFactory.createTitledBorder("Hoa don - tim kiem nang cao"));
        JPanel filters=new JPanel(new GridLayout(2,8,5,5));filters.add(new JLabel("Tu ngay"));filters.add(from);filters.add(new JLabel("Den ngay"));filters.add(to);filters.add(new JLabel("Tong tu"));filters.add(min);filters.add(new JLabel("Tong den"));filters.add(max);filters.add(new JLabel("Khach hang"));filters.add(filterKh);filters.add(new JLabel("Sap xep"));filters.add(sort);JButton order=new JButton("Tang/Giam");filters.add(order);JButton find=new JButton("Tim");filters.add(find);JButton reload=new JButton("Lam moi");filters.add(reload);
        JPanel export=new JPanel(new FlowLayout(FlowLayout.RIGHT));JButton txt=new JButton("Xuat TXT"),csv=new JButton("Xuat CSV");export.add(txt);export.add(csv);
        JPanel htop=new JPanel(new BorderLayout());htop.add(filters,BorderLayout.CENTER);htop.add(export,BorderLayout.SOUTH);history.add(htop,BorderLayout.NORTH);history.add(new JScrollPane(hdTable),BorderLayout.CENTER);
        JPanel nav=new JPanel();JButton first=new JButton("Dau"),prev=new JButton("Truoc"),next=new JButton("Sau"),last=new JButton("Cuoi");nav.add(first);nav.add(prev);nav.add(pageLabel);nav.add(next);nav.add(last);history.add(nav,BorderLayout.SOUTH);
        JSplitPane split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,create,history);split.setResizeWeight(.52);add(split,BorderLayout.CENTER);
        add.addActionListener(e->addDetail());remove.addActionListener(e->removeDetail());save.addActionListener(e->saveInvoice());find.addActionListener(e->{page=0;loadInvoices();});reload.addActionListener(e->{clearFilters();page=0;loadInvoices();});order.addActionListener(e->{dir=dir.equals("ASC")?"DESC":"ASC";loadInvoices();});
        first.addActionListener(e->{page=0;loadInvoices();});prev.addActionListener(e->{if(page>0)page--;loadInvoices();});next.addActionListener(e->{page++;loadInvoices();});last.addActionListener(e->{page=Math.max(0,pages()-1);loadInvoices();});
        txt.addActionListener(e->export(false));csv.addActionListener(e->export(true));
        loadCombos();loadInvoices();
    }
    private void loadCombos(){try{cboKh.removeAllItems();filterKh.removeAllItems();filterKh.addItem(new KhachHang(0,"Tat ca","",""));for(KhachHang k:khBus.findAll()){cboKh.addItem(k);filterKh.addItem(k);}cboSp.removeAllItems();for(SanPham s:spBus.findAll())if(s.getSoLuong()>0)cboSp.addItem(s);}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private void addDetail(){try{SanPham s=(SanPham)cboSp.getSelectedItem();if(s==null)throw new IllegalArgumentException("Khong co san pham con hang");int q=Integer.parseInt(txtSL.getText().trim());if(q<=0)throw new IllegalArgumentException("So luong phai lon hon 0");int old=0;for(ChiTietHoaDon c:details)if(c.getMaSp()==s.getMaSp())old=c.getSoLuong();if(old+q>s.getSoLuong())throw new IllegalArgumentException("Vuot ton kho. Ton: "+s.getSoLuong());if(old>0){for(ChiTietHoaDon c:details)if(c.getMaSp()==s.getMaSp())c.setSoLuong(old+q);}else details.add(new ChiTietHoaDon(s.getMaSp(),s.getTenSp(),q,s.getDonGia()));refreshDetails();}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private void removeDetail(){int r=ctTable.getSelectedRow();if(r<0){MessageUtil.error(this,"Chon dong can xoa");return;}details.remove(r);refreshDetails();}
    private void refreshDetails(){ctModel.setRowCount(0);BigDecimal t=BigDecimal.ZERO;for(ChiTietHoaDon c:details){ctModel.addRow(new Object[]{c.getMaSp(),c.getTenSp(),c.getSoLuong(),c.getDonGia(),c.getThanhTien()});t=t.add(c.getThanhTien());}lblTong.setText("Tong tien: "+t+" VND");}
    private void saveInvoice(){try{KhachHang k=(KhachHang)cboKh.getSelectedItem();int id=hdBus.save(k==null?0:k.getMaKh(),details);MessageUtil.info(this,"Luu hoa don thanh cong. Ma HD: "+id);details.clear();refreshDetails();loadCombos();page=0;loadInvoices();}catch(Exception e){MessageUtil.error(this,"Khong luu duoc: "+e.getMessage());}}
    private LocalDate date(String s){return s==null||s.isBlank()?null:LocalDate.parse(s.trim());}private BigDecimal money(String s){return s==null||s.isBlank()?null:new BigDecimal(s.trim());}
    private Integer selectedKh(){KhachHang k=(KhachHang)filterKh.getSelectedItem();return k==null||k.getMaKh()==0?null:k.getMaKh();}
    private int pages(){try{return Math.max(1,(hdBus.count(date(from.getText()),date(to.getText()),selectedKh(),money(min.getText()),money(max.getText()))+SIZE-1)/SIZE);}catch(Exception e){return 1;}}
    private void loadInvoices(){new SwingWorker<List<HoaDon>,Void>(){private int actualPage;private int totalPages;protected List<HoaDon> doInBackground()throws Exception{LocalDate f=date(from.getText()),t=date(to.getText());Integer k=selectedKh();BigDecimal mn=money(min.getText()),mx=money(max.getText());int total=hdBus.count(f,t,k,mn,mx);totalPages=Math.max(1,(total+SIZE-1)/SIZE);actualPage=Math.min(Math.max(0,page),totalPages-1);page=actualPage;return hdBus.search(f,t,k,mn,mx,actualPage,SIZE,String.valueOf(sort.getSelectedItem()),dir);}protected void done(){try{List<HoaDon> l=get();hdModel.setRowCount(0);for(HoaDon h:l)hdModel.addRow(new Object[]{h.getMaHd(),h.getNgayLap(),h.getMaKh(),h.getTenKh(),h.getTongTien()});pageLabel.setText("Trang "+(page+1)+"/"+totalPages);}catch(Exception e){MessageUtil.error(HoaDonPanel.this,"Loi load hoa don: "+e.getMessage());}}}.execute();}
    private void clearFilters(){from.setText("");to.setText("");min.setText("");max.setText("");filterKh.setSelectedIndex(0);}
    private int selectedHd(){int r=hdTable.getSelectedRow();return r<0?0:Integer.parseInt(hdModel.getValueAt(r,0).toString());}
    private void export(boolean csv){int id=selectedHd();if(id<=0){MessageUtil.error(this,"Chon hoa don can xuat");return;}try{HoaDon h=hdBus.findOne(id);KhachHang k=null;for(KhachHang x:khBus.findAll())if(x.getMaKh()==h.getMaKh()){k=x;break;}if(k==null)throw new IllegalArgumentException("Khong tim thay khach hang");List<ChiTietHoaDon> d=hdBus.findDetails(id);java.nio.file.Path p=csv?HoaDonExporter.exportCsv(h,k,d):HoaDonExporter.exportTxt(h,k,d);MessageUtil.info(this,"Da xuat file: "+p.toAbsolutePath());}catch(Exception e){MessageUtil.error(this,"Xuat hoa don loi: "+e.getMessage());}}
}
