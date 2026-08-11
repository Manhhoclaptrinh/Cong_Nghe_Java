package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class KeywordSearchFrame extends JFrame {
    private final JButton btnChoose = new JButton("Chọn file .txt");
    private final JButton btnSearch = new JButton("Tìm kiếm");
    private final JTextField txtKeyword = new JTextField();
    private final JLabel lblFile = new JLabel("Chưa chọn file");
    private final JLabel lblResult = new JLabel("Kết quả: 0 dòng");
    private final JTextArea txtOutput = new JTextArea();
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private File selectedFile;

    public KeywordSearchFrame() {
        setTitle("Bài 7 - Tìm kiếm từ khóa trong file");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtOutput.setEditable(false);
        txtOutput.setLineWrap(true);
        txtOutput.setWrapStyleWord(true);
        progressBar.setStringPainted(true);

        JPanel top = new JPanel(new GridLayout(4, 1, 8, 8));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top.add(btnChoose);
        top.add(lblFile);
        top.add(txtKeyword);
        top.add(btnSearch);

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.add(progressBar, BorderLayout.CENTER);
        bottom.add(lblResult, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(txtOutput), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnChoose.addActionListener(e -> chooseFile());
        btnSearch.addActionListener(e -> search());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
        }
    }

    private void search() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file.");
            return;
        }
        String keyword = txtKeyword.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa.");
            return;
        }

        btnSearch.setEnabled(false);
        txtOutput.setText("");
        lblResult.setText("Đang tìm...");
        progressBar.setValue(0);

        SwingWorker<Integer, String> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws Exception {
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                int count = 0;

                try (BufferedReader reader = Files.newBufferedReader(
                        selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    long lineNo = 0;
                    while ((line = reader.readLine()) != null) {
                        if (isCancelled()) break;
                        lineNo++;
                        if (line.toLowerCase().contains(keyword.toLowerCase())) {
                            count++;
                            publish("Dòng " + lineNo + ": " + line);
                        }
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;
                        int progress = totalBytes == 0 ? 100 :
                                (int) Math.min(100, readBytes * 100 / totalBytes);
                        setProgress(progress);
                    }
                }
                return count;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String s : chunks) {
                    txtOutput.append(s + System.lineSeparator());
                }
            }

            @Override
            protected void done() {
                try {
                    if (!isCancelled()) {
                        lblResult.setText("Số dòng tìm thấy: " + get());
                        progressBar.setValue(100);
                    } else {
                        lblResult.setText("Đã hủy tác vụ");
                    }
                } catch (Exception ex) {
                    lblResult.setText("Lỗi: " + ex.getMessage());
                }
                btnSearch.setEnabled(true);
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
