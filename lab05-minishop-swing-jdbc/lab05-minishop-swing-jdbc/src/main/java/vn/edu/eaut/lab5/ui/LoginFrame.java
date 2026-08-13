package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.TaiKhoanBUS;
import vn.edu.eaut.lab5.model.TaiKhoan;
import vn.edu.eaut.lab5.util.MessageUtil;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final TaiKhoanBUS bus=new TaiKhoanBUS();private final JTextField user=new JTextField();private final JPasswordField pass=new JPasswordField();
    public LoginFrame(){setTitle("MiniShop - Dang nhap");setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);setSize(400,230);setLocationRelativeTo(null);
        JPanel p=new JPanel(new GridBagLayout());GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(7,7,7,7);g.fill=GridBagConstraints.HORIZONTAL;
        g.gridx=0;g.gridy=0;p.add(new JLabel("Username"),g);g.gridx=1;p.add(user,g);g.gridx=0;g.gridy=1;p.add(new JLabel("Password"),g);g.gridx=1;p.add(pass,g);JButton login=new JButton("Dang nhap");g.gridx=1;g.gridy=2;p.add(login,g);g.gridy=3;p.add(new JLabel("Demo: admin/admin | nv/nv123 | kt/kt123"),g);add(p);
        login.addActionListener(e->login());pass.addActionListener(e->login());}
    private void login(){try{TaiKhoan a=bus.login(user.getText(),new String(pass.getPassword()));if(a==null){MessageUtil.error(this,"Sai username hoac mat khau");return;}new MainFrame(a).setVisible(true);dispose();}catch(Exception e){MessageUtil.error(this,e.getMessage());}}
}
