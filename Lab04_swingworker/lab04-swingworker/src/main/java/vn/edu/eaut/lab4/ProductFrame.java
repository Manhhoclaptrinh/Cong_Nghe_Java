package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

public class ProductFrame extends JFrame {
    private final JButton btnLoad = new JButton("Tải sản phẩm");
    private final JLabel lblStatus = new JLabel("Chưa tải sản phẩm");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Mã SP", "Tên SP", "Đơn giá"}, 0);
    private final JTable table = new JTable(model);

    public ProductFrame() {
        setTitle("Bài 9 - Mô phỏng tải danh sách sản phẩm");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        progressBar.setStringPainted(true);

        JPanel top = new JPanel(new GridLayout(3, 1, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top.add(btnLoad);
        top.add(progressBar);
        top.add(lblStatus);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnLoad.addActionListener(e -> loadProducts());
    }

    private void loadProducts() {
        btnLoad.setEnabled(false);
        model.setRowCount(0);
        progressBar.setValue(0);
        lblStatus.setText("Đang tải sản phẩm...");

        SwingWorker<List<String[]>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                List<String[]> products = Arrays.asList(
                        new String[]{"SP01", "Bàn phím", "250000"},
                        new String[]{"SP02", "Chuột", "150000"},
                        new String[]{"SP03", "Màn hình", "2500000"}
                );

                for (int i = 0; i < products.size(); i++) {
                    if (isCancelled()) break;
                    Thread.sleep(1500);
                    setProgress((i + 1) * 100 / products.size());
                }
                return products;
            }

            @Override
            protected void done() {
                try {
                    if (!isCancelled()) {
                        for (String[] p : get()) model.addRow(p);
                        progressBar.setValue(100);
                        lblStatus.setText("Tải sản phẩm hoàn tất");
                    } else {
                        lblStatus.setText("Đã hủy tác vụ");
                    }
                } catch (Exception ex) {
                    lblStatus.setText("Có lỗi khi tải sản phẩm");
                }
                btnLoad.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });
        worker.execute();
    }
}
