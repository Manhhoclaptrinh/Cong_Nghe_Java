package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.model.DanhMuc;
import vn.edu.eaut.lab5.util.MessageUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DanhMucDialog extends JDialog {
    private final DanhMucBUS bus=new DanhMucBUS();
    private final JTextField txtTen=new JTextField();
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Ma DM","Ten danh muc"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model);
    public DanhMucDialog(Window owner){super(owner,"Quan ly danh muc",ModalityType.APPLICATION_MODAL);setSize(520,420);setLocationRelativeTo(owner);
        JPanel form=new JPanel(new BorderLayout(5,5));form.setBorder(BorderFactory.createTitledBorder("Danh muc"));
        JPanel input=new JPanel(new BorderLayout(5,5));input.add(new JLabel("Ten danh muc"),BorderLayout.WEST);input.add(txtTen);
        JPanel actions=new JPanel();JButton add=new JButton("Them");JButton edit=new JButton("Sua");JButton del=new JButton("Xoa");JButton close=new JButton("Dong");
        actions.add(add);actions.add(edit);actions.add(del);actions.add(close);form.add(input,BorderLayout.CENTER);form.add(actions,BorderLayout.SOUTH);
        add(form,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER);
        table.getSelectionModel().addListSelectionListener(e->{int r=table.getSelectedRow();if(r>=0)txtTen.setText(model.getValueAt(r,1).toString());});
        add.addActionListener(e->save(0));edit.addActionListener(e->save(selected()));del.addActionListener(e->delete());close.addActionListener(e->dispose());load();
    }
    private int selected(){int r=table.getSelectedRow();return r<0?0:Integer.parseInt(model.getValueAt(r,0).toString());}
    private void save(int id){try{DanhMuc d=new DanhMuc(id,txtTen.getText().trim());bus.save(d);MessageUtil.info(this,id==0?"Them thanh cong":"Sua thanh cong");txtTen.setText("");load();}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private void delete(){int id=selected();if(id<=0){MessageUtil.error(this,"Chon danh muc can xoa");return;}if(!MessageUtil.confirm(this,"Xoa danh muc?"))return;try{bus.delete(id);load();txtTen.setText("");}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
    private void load(){try{model.setRowCount(0);for(DanhMuc d:bus.findAll())model.addRow(new Object[]{d.getMaDm(),d.getTenDm()});}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
}
