package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.MessageUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.util.List;

public class KhachHangPanel extends JPanel {
    private final KhachHangBUS bus=new KhachHangBUS();
    private final JTextField txtMa=new JTextField(),txtTen=new JTextField(),txtSdt=new JTextField(),txtDiaChi=new JTextField();
    private final JTextField fTen=new JTextField(),fSdt=new JTextField(),fDiaChi=new JTextField();
    private final JComboBox<String> sort=new JComboBox<>(new String[]{"ma_kh","ten_kh","sdt","dia_chi"});private String dir="ASC";
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Ma KH","Ten khach hang","SDT","Dia chi"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model);private final JLabel pageLabel=new JLabel("Trang 1/1");private int page=0;private static final int SIZE=10;
    public KhachHangPanel(){setLayout(new BorderLayout(8,8));txtMa.setEditable(false);((AbstractDocument)txtSdt.getDocument()).setDocumentFilter(new PhoneFilter());
        JPanel form=new JPanel(new GridLayout(2,4,8,5));form.setBorder(BorderFactory.createTitledBorder("Thong tin khach hang"));
        form.add(new JLabel("Ma KH"));form.add(txtMa);form.add(new JLabel("Ten KH"));form.add(txtTen);form.add(new JLabel("SDT"));form.add(txtSdt);form.add(new JLabel("Dia chi"));form.add(txtDiaChi);
        JPanel search=new JPanel(new GridLayout(2,6,5,5));search.setBorder(BorderFactory.createTitledBorder("Tim kiem nang cao"));
        search.add(new JLabel("Ten"));search.add(fTen);search.add(new JLabel("SDT"));search.add(fSdt);search.add(new JLabel("Dia chi"));search.add(fDiaChi);search.add(new JLabel("Sap xep"));search.add(sort);JButton order=new JButton("Tang/Giam");search.add(order);
        JPanel actions=new JPanel();JButton find=new JButton("Tim kiem"),add=new JButton("Them"),edit=new JButton("Sua"),del=new JButton("Xoa"),clear=new JButton("Lam moi");actions.add(find);actions.add(add);actions.add(edit);actions.add(del);actions.add(clear);search.add(new JLabel(""));search.add(actions);
        JPanel top=new JPanel(new BorderLayout(5,5));top.add(form,BorderLayout.NORTH);top.add(search,BorderLayout.CENTER);add(top,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);
        JPanel nav=new JPanel();JButton first=new JButton("Dau"),prev=new JButton("Truoc"),next=new JButton("Sau"),last=new JButton("Cuoi");nav.add(first);nav.add(prev);nav.add(pageLabel);nav.add(next);nav.add(last);add(nav,BorderLayout.SOUTH);
        find.addActionListener(e->{page=0;load();});add.addActionListener(e->save(0));edit.addActionListener(e->save(selected()));del.addActionListener(e->delete());clear.addActionListener(e->{clearForm();page=0;load();});order.addActionListener(e->{dir=dir.equals("ASC")?"DESC":"ASC";load();});
        first.addActionListener(e->{page=0;load();});prev.addActionListener(e->{if(page>0)page--;load();});next.addActionListener(e->{page++;load();});last.addActionListener(e->{page=Math.max(0,pages()-1);load();});table.getSelectionModel().addListSelectionListener(e->fill());load();}
    static class PhoneFilter extends DocumentFilter{private boolean ok(String s){return s.matches("\\d{0,10}");}public void insertString(FilterBypass f,int o,String s,AttributeSet a)throws BadLocationException{String c=f.getDocument().getText(0,f.getDocument().getLength());String n=c.substring(0,o)+(s==null?"":s)+c.substring(o);if(ok(n))super.insertString(f,o,s,a);}public void replace(FilterBypass f,int o,int l,String s,AttributeSet a)throws BadLocationException{String c=f.getDocument().getText(0,f.getDocument().getLength());String n=c.substring(0,o)+(s==null?"":s)+c.substring(o+l);if(ok(n))super.replace(f,o,l,s,a);}}
    private int selected(){int r=table.getSelectedRow();return r<0?0:Integer.parseInt(model.getValueAt(r,0).toString());}
    private void fill(){int r=table.getSelectedRow();if(r<0)return;txtMa.setText(""+model.getValueAt(r,0));txtTen.setText(""+model.getValueAt(r,1));txtSdt.setText(""+model.getValueAt(r,2));txtDiaChi.setText(""+model.getValueAt(r,3));}
    private void save(int id){try{KhachHang k=new KhachHang(id,txtTen.getText().trim(),txtSdt.getText().trim(),txtDiaChi.getText().trim());bus.save(k);MessageUtil.info(this,id==0?"Them thanh cong":"Sua thanh cong");clearForm();load();}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private void delete(){int id=selected();if(id<=0){MessageUtil.error(this,"Chon khach hang");return;}if(!MessageUtil.confirm(this,"Ban co chac muon xoa?"))return;try{bus.delete(id);clearForm();load();}catch(Exception e){MessageUtil.error(this,"Khong xoa duoc: khach hang co the da co hoa don.");}}
    private void clearForm(){txtMa.setText("");txtTen.setText("");txtSdt.setText("");txtDiaChi.setText("");table.clearSelection();}
    private int pages(){try{return Math.max(1,(bus.count(fTen.getText(),fSdt.getText(),fDiaChi.getText())+SIZE-1)/SIZE);}catch(Exception e){return 1;}}
    private void load(){try{int total=bus.count(fTen.getText(),fSdt.getText(),fDiaChi.getText());int pg=Math.max(1,(total+SIZE-1)/SIZE);if(page>=pg)page=pg-1;List<KhachHang> l=bus.searchAdvanced(fTen.getText(),fSdt.getText(),fDiaChi.getText(),page,SIZE,String.valueOf(sort.getSelectedItem()),dir);model.setRowCount(0);for(KhachHang k:l)model.addRow(new Object[]{k.getMaKh(),k.getTenKh(),k.getSdt(),k.getDiaChi()});pageLabel.setText("Trang "+(page+1)+"/"+pg);}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
}
