package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.TaiKhoanBUS;
import vn.edu.eaut.lab5.model.TaiKhoan;
import vn.edu.eaut.lab5.util.MessageUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TaiKhoanPanel extends JPanel {
    private final TaiKhoanBUS bus=new TaiKhoanBUS();
    private final JTextField user=new JTextField(),pass=new JTextField(),name=new JTextField();
    private final JComboBox<String> role=new JComboBox<>(new String[]{"ADMIN","NHANVIEN","KETOAN"});
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Username","Mat khau","Ho ten","Vai tro"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model);
    public TaiKhoanPanel(){setLayout(new BorderLayout(8,8));JPanel form=new JPanel(new GridLayout(2,4,6,5));form.setBorder(BorderFactory.createTitledBorder("Tai khoan"));form.add(new JLabel("Username"));form.add(user);form.add(new JLabel("Password"));form.add(pass);form.add(new JLabel("Ho ten"));form.add(name);form.add(new JLabel("Vai tro"));form.add(role);
        JPanel a=new JPanel();JButton save=new JButton("Them/Sua"),del=new JButton("Xoa"),clear=new JButton("Lam moi");a.add(save);a.add(del);a.add(clear);JPanel top=new JPanel(new BorderLayout());top.add(form,BorderLayout.CENTER);top.add(a,BorderLayout.SOUTH);add(top,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);
        save.addActionListener(e->save());del.addActionListener(e->delete());clear.addActionListener(e->clear());table.getSelectionModel().addListSelectionListener(e->fill());load();}
    private void fill(){int r=table.getSelectedRow();if(r<0)return;user.setText(""+model.getValueAt(r,0));pass.setText(""+model.getValueAt(r,1));name.setText(""+model.getValueAt(r,2));role.setSelectedItem(""+model.getValueAt(r,3));}
    private void save(){try{bus.save(new TaiKhoan(user.getText().trim(),pass.getText(),name.getText().trim(),String.valueOf(role.getSelectedItem())));MessageUtil.info(this,"Luu tai khoan thanh cong");clear();load();}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private void delete(){int r=table.getSelectedRow();if(r<0){MessageUtil.error(this,"Chon tai khoan");return;}String u=model.getValueAt(r,0).toString();if(!MessageUtil.confirm(this,"Xoa tai khoan "+u+"?"))return;try{bus.delete(u);load();}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private void clear(){user.setText("");pass.setText("");name.setText("");role.setSelectedIndex(0);table.clearSelection();}
    private void load(){try{model.setRowCount(0);for(TaiKhoan a:bus.findAll())model.addRow(new Object[]{a.getUsername(),a.getPassword(),a.getHoTen(),a.getVaiTro()});}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
}
