package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ThongKePanel extends JPanel {
    private final ThongKeBUS bus = new ThongKeBUS();
    private final JTextField txtTuNgay = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
    private final JTextField txtDenNgay = new JTextField(LocalDate.now().toString());
    private final JLabel lblDoanhThu = new JLabel("Doanh thu: -");
    private final JLabel lblCaoNhat = new JLabel("Hoa don cao nhat: -");
    private final JLabel lblBanChay = new JLabel("San pham ban chay: -");
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public ThongKePanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBorder(BorderFactory.createTitledBorder("Thong ke doanh thu"));
        top.add(new JLabel("Tu ngay (yyyy-MM-dd):"));
        txtTuNgay.setPreferredSize(new Dimension(120, 28));
        top.add(txtTuNgay);
        top.add(new JLabel("Den ngay:"));
        txtDenNgay.setPreferredSize(new Dimension(120, 28));
        top.add(txtDenNgay);

        JButton btnDoanhThu = new JButton("Tinh doanh thu");
        JButton btnCaoNhat = new JButton("HD cao nhat");
        JButton btnBanChay = new JButton("SP ban chay");
        top.add(btnDoanhThu);
        top.add(btnCaoNhat);
        top.add(btnBanChay);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        center.add(lblDoanhThu);
        center.add(Box.createVerticalStrut(20));
        center.add(lblCaoNhat);
        center.add(Box.createVerticalStrut(20));
        center.add(lblBanChay);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        btnDoanhThu.addActionListener(e -> tinhDoanhThu());
        btnCaoNhat.addActionListener(e -> loadCaoNhat());
        btnBanChay.addActionListener(e -> loadBanChay());
    }

    private void tinhDoanhThu() {
        try {
            LocalDate from = LocalDate.parse(txtTuNgay.getText().trim(), formatter);
            LocalDate to = LocalDate.parse(txtDenNgay.getText().trim(), formatter);
            lblDoanhThu.setText("Dang thong ke...");

            new SwingWorker<BigDecimal, Void>() {
                @Override
                protected BigDecimal doInBackground() throws Exception {
                    return bus.tinhDoanhThu(from, to);
                }

                @Override
                protected void done() {
                    try {
                        lblDoanhThu.setText("Doanh thu: " + get() + " VND");
                    } catch (Exception ex) {
                        lblDoanhThu.setText("Doanh thu: Loi");
                        MessageUtil.error(ThongKePanel.this, "Loi thong ke: " + ex.getMessage());
                    }
                }
            }.execute();
        } catch (Exception ex) {
            MessageUtil.error(this, "Ngay phai co dang yyyy-MM-dd");
        }
    }

    private void loadCaoNhat() {
        new SwingWorker<String, Void>() {
            protected String doInBackground() throws Exception { return bus.hoaDonCaoNhat(); }
            protected void done() {
                try { lblCaoNhat.setText("Hoa don cao nhat: " + get()); }
                catch (Exception ex) { MessageUtil.error(ThongKePanel.this, ex.getMessage()); }
            }
        }.execute();
    }

    private void loadBanChay() {
        new SwingWorker<String, Void>() {
            protected String doInBackground() throws Exception { return bus.sanPhamBanChay(); }
            protected void done() {
                try { lblBanChay.setText("San pham ban chay: " + get()); }
                catch (Exception ex) { MessageUtil.error(ThongKePanel.this, ex.getMessage()); }
            }
        }.execute();
    }
}
