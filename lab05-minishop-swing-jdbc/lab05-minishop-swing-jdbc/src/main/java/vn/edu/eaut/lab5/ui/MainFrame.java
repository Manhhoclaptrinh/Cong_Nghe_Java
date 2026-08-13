package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.model.TaiKhoan;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final TaiKhoan account;
    public MainFrame(TaiKhoan account){this.account=account;setTitle("MiniShop - "+account.getHoTen()+" ["+account.getVaiTro()+"]");setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);setSize(1150,760);setLocationRelativeTo(null);
        JTabbedPane tabs=new JTabbedPane();
        if(can("PRODUCT"))tabs.addTab("San pham",new SanPhamPanel());
        if(can("CUSTOMER"))tabs.addTab("Khach hang",new KhachHangPanel());
        if(can("INVOICE"))tabs.addTab("Hoa don",new HoaDonPanel());
        if(can("REPORT"))tabs.addTab("Thong ke",new ThongKePanel());
        if(can("ACCOUNT"))tabs.addTab("Tai khoan",new TaiKhoanPanel());
        add(tabs,BorderLayout.CENTER);
        JMenuBar bar=new JMenuBar();JMenu menu=new JMenu("He thong");JMenuItem logout=new JMenuItem("Dang xuat");JMenuItem exit=new JMenuItem("Thoat");menu.add(logout);menu.add(exit);bar.add(menu);setJMenuBar(bar);
        logout.addActionListener(e->{new LoginFrame().setVisible(true);dispose();});exit.addActionListener(e->System.exit(0));
    }
    private boolean can(String f){return switch(account.getVaiTro()){case "ADMIN"->true;case "NHANVIEN"->f.equals("PRODUCT")||f.equals("CUSTOMER")||f.equals("INVOICE");case "KETOAN"->f.equals("INVOICE")||f.equals("REPORT");default->false;};}
}
