package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class StudentCsvFrame extends JFrame {
    private final JButton btnChoose = new JButton("Chọn CSV");
    private final JLabel lblFile = new JLabel("Chưa chọn file");
    private final JLabel lblAverage = new JLabel("Điểm trung bình: -");
    private final JLabel lblMax = new JLabel("Điểm cao nhất: -");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Mã SV", "Họ tên", "Điểm"}, 0);
    private final JTable table = new JTable(model);
    private File selectedFile;

    public StudentCsvFrame() {
        setTitle("Bài 8 - Đọc CSV điểm sinh viên");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        progressBar.setStringPainted(true);

        JPanel top = new JPanel(new GridLayout(3, 1, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top.add(btnChoose);
        top.add(lblFile);
        top.add(progressBar);

        JPanel bottom = new JPanel(new GridLayout(2, 1));
        bottom.add(lblAverage);
        bottom.add(lblMax);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnChoose.addActionListener(e -> chooseAndLoad());
    }

    private void chooseAndLoad() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        selectedFile = chooser.getSelectedFile();
        lblFile.setText("File: " + selectedFile.getAbsolutePath());
        loadCsv();
    }

    private void loadCsv() {
        btnChoose.setEnabled(false);
        model.setRowCount(0);
        progressBar.setValue(0);

        SwingWorker<List<String[]>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<String[]> doInBackground() throws Exception {
                List<String[]> rows = new ArrayList<>();
                List<String> allLines = Files.readAllLines(
                        selectedFile.toPath(), StandardCharsets.UTF_8);
                int total = allLines.size();

                for (int i = 1; i < allLines.size(); i++) {
                    if (isCancelled()) break;
                    String line = allLines.get(i).trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",", -1);
                    if (parts.length >= 3) rows.add(new String[]{
                            parts[0].trim(), parts[1].trim(), parts[2].trim()
                    });
                    setProgress(total == 0 ? 100 : i * 100 / total);
                }
                return rows;
            }

            @Override
            protected void done() {
                try {
                    List<String[]> rows = get();
                    double sum = 0;
                    double max = -Double.MAX_VALUE;
                    String maxName = "";

                    for (String[] row : rows) {
                        double score = Double.parseDouble(row[2]);
                        model.addRow(row);
                        sum += score;
                        if (score > max) {
                            max = score;
                            maxName = row[1];
                        }
                    }

                    if (!rows.isEmpty()) {
                        lblAverage.setText(String.format("Điểm trung bình: %.2f", sum / rows.size()));
                        lblMax.setText(String.format("Điểm cao nhất: %.2f - %s", max, maxName));
                    } else {
                        lblAverage.setText("Điểm trung bình: -");
                        lblMax.setText("Điểm cao nhất: -");
                    }
                    progressBar.setValue(100);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(StudentCsvFrame.this,
                            "Lỗi đọc CSV: " + ex.getMessage());
                }
                btnChoose.setEnabled(true);
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
