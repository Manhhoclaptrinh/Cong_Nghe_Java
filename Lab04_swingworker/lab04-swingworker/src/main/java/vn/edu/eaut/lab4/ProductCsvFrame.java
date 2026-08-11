package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ProductCsvFrame extends JFrame {
    private final JTextField txtCode = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextField txtPrice = new JTextField();

    private final JButton btnAdd = new JButton("Thêm");
    private final JButton btnUpdate = new JButton("Sửa");
    private final JButton btnDelete = new JButton("Xóa");
    private final JButton btnLoad = new JButton("Đọc CSV");
    private final JButton btnSave = new JButton("Lưu CSV");

    private final JLabel lblStatus = new JLabel("Sẵn sàng");
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Mã SP", "Tên SP", "Đơn giá"}, 0);
    private final JTable table = new JTable(model);

    private File csvFile;

    public ProductCsvFrame() {
        setTitle("Bài 10 - Quản lý sản phẩm bằng CSV");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        progressBar.setStringPainted(true);

        JPanel input = new JPanel(new GridLayout(3, 2, 8, 8));
        input.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        input.add(new JLabel("Mã SP:"));
        input.add(txtCode);
        input.add(new JLabel("Tên SP:"));
        input.add(txtName);
        input.add(new JLabel("Đơn giá:"));
        input.add(txtPrice);

        JPanel buttons = new JPanel(new GridLayout(1, 5, 8, 8));
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnLoad);
        buttons.add(btnSave);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top.add(input, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.add(progressBar, BorderLayout.CENTER);
        bottom.add(lblStatus, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> fillForm());
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnLoad.addActionListener(e -> chooseAndLoad());
        btnSave.addActionListener(e -> chooseAndSave());
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtCode.setText(model.getValueAt(row, 0).toString());
            txtName.setText(model.getValueAt(row, 1).toString());
            txtPrice.setText(model.getValueAt(row, 2).toString());
        }
    }

    private boolean validateForm() {
        if (txtCode.getText().trim().isEmpty() || txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã và tên sản phẩm không được để trống.");
            return false;
        }
        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số không âm.");
            return false;
        }
        return true;
    }

    private void addProduct() {
        if (!validateForm()) return;
        model.addRow(new Object[]{
                txtCode.getText().trim(),
                txtName.getText().trim(),
                txtPrice.getText().trim()
        });
        clearForm();
        lblStatus.setText("Đã thêm sản phẩm");
    }

    private void updateProduct() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa.");
            return;
        }
        if (!validateForm()) return;

        model.setValueAt(txtCode.getText().trim(), row, 0);
        model.setValueAt(txtName.getText().trim(), row, 1);
        model.setValueAt(txtPrice.getText().trim(), row, 2);
        lblStatus.setText("Đã cập nhật sản phẩm");
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa.");
            return;
        }
        model.removeRow(row);
        clearForm();
        lblStatus.setText("Đã xóa sản phẩm");
    }

    private void clearForm() {
        txtCode.setText("");
        txtName.setText("");
        txtPrice.setText("");
        table.clearSelection();
    }

    private void chooseAndLoad() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        csvFile = chooser.getSelectedFile();
        loadCsv(csvFile);
    }

    private void loadCsv(File file) {
        setButtonsEnabled(false);
        progressBar.setValue(0);
        lblStatus.setText("Đang đọc CSV...");

        SwingWorker<List<String[]>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                List<String[]> rows = new ArrayList<>();
                List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

                for (int i = 1; i < lines.size(); i++) {
                    if (isCancelled()) break;
                    String line = lines.get(i).trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",", -1);
                    if (parts.length >= 3) {
                        rows.add(new String[]{
                                parts[0].trim(), parts[1].trim(), parts[2].trim()
                        });
                    }
                    setProgress(lines.isEmpty() ? 100 : i * 100 / lines.size());
                    Thread.sleep(50);
                }
                return rows;
            }

            @Override
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (String[] row : get()) model.addRow(row);
                    progressBar.setValue(100);
                    lblStatus.setText("Đọc CSV hoàn tất");
                } catch (Exception ex) {
                    lblStatus.setText("Lỗi đọc CSV: " + ex.getMessage());
                }
                setButtonsEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });
        worker.execute();
    }

    private void chooseAndSave() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("products.csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        csvFile = chooser.getSelectedFile();
        saveCsv(csvFile);
    }

    private void saveCsv(File file) {
        setButtonsEnabled(false);
        progressBar.setValue(0);
        lblStatus.setText("Đang lưu CSV...");

        List<String[]> rows = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            rows.add(new String[]{
                    model.getValueAt(i, 0).toString(),
                    model.getValueAt(i, 1).toString(),
                    model.getValueAt(i, 2).toString()
            });
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (BufferedWriter writer = Files.newBufferedWriter(
                        file.toPath(), StandardCharsets.UTF_8)) {
                    writer.write("MaSP,TenSP,DonGia");
                    writer.newLine();

                    int total = rows.size();
                    for (int i = 0; i < total; i++) {
                        if (isCancelled()) break;
                        String[] p = rows.get(i);
                        writer.write(csv(p[0]) + "," + csv(p[1]) + "," + csv(p[2]));
                        writer.newLine();
                        setProgress(total == 0 ? 100 : (i + 1) * 100 / total);
                        Thread.sleep(50);
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    progressBar.setValue(100);
                    lblStatus.setText("Lưu CSV hoàn tất: " + file.getAbsolutePath());
                } catch (Exception ex) {
                    lblStatus.setText("Lỗi lưu CSV: " + ex.getMessage());
                }
                setButtonsEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });
        worker.execute();
    }

    private String csv(String value) {
        String v = value.replace("\"", "\"\"");
        return "\"" + v + "\"";
    }

    private void setButtonsEnabled(boolean enabled) {
        btnAdd.setEnabled(enabled);
        btnUpdate.setEnabled(enabled);
        btnDelete.setEnabled(enabled);
        btnLoad.setEnabled(enabled);
        btnSave.setEnabled(enabled);
    }
}
