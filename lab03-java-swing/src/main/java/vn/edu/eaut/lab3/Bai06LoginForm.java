package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai06LoginForm extends JFrame {

    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();

    private final JComboBox<String> cboRole =
            new JComboBox<>(new String[]{
                    "Admin",
                    "User"
            });

    private final JCheckBox chkShowPassword =
            new JCheckBox("Hiển thị mật khẩu");

    public Bai06LoginForm() {

        setTitle("Bài 6 - Form đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 8, 8)
        );

        formPanel.add(new JLabel("Tài khoản:"));
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Mật khẩu:"));
        formPanel.add(txtPassword);

        formPanel.add(new JLabel("Vai trò:"));
        formPanel.add(cboRole);

        formPanel.add(new JLabel(""));
        formPanel.add(chkShowPassword);

        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnClear = new JButton("Làm mới");

        JPanel buttonPanel = new JPanel(
                new FlowLayout()
        );

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnClear);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        chkShowPassword.addActionListener(
                e -> hienThiMatKhau()
        );

        btnLogin.addActionListener(
                e -> dangNhap()
        );

        btnClear.addActionListener(
                e -> lamMoi()
        );

        setSize(430, 250);
        setLocationRelativeTo(null);
    }

    private void hienThiMatKhau() {

        if (chkShowPassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('•');
        }
    }

    private void dangNhap() {

        String username =
                txtUsername.getText().trim();

        String password =
                new String(txtPassword.getPassword());

        String role =
                (String) cboRole.getSelectedItem();

        boolean loginSuccess = false;

        if (username.equals("admin")
                && password.equals("123456")
                && role.equals("Admin")) {

            loginSuccess = true;

        } else if (username.equals("user")
                && password.equals("123456")
                && role.equals("User")) {

            loginSuccess = true;
        }

        if (loginSuccess) {

            JOptionPane.showMessageDialog(
                    this,
                    "Đăng nhập thành công!\n"
                            + "Xin chào " + username
                            + " - Vai trò: " + role
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Tài khoản, mật khẩu hoặc vai trò không đúng!",
                    "Đăng nhập thất bại",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void lamMoi() {

        txtUsername.setText("");
        txtPassword.setText("");

        cboRole.setSelectedIndex(0);
        chkShowPassword.setSelected(false);

        txtPassword.setEchoChar('•');

        txtUsername.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai06LoginForm().setVisible(true)
        );
    }
}