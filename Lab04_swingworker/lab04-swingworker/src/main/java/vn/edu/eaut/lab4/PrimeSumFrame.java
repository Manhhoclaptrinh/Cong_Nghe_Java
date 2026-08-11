package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;

public class PrimeSumFrame extends JFrame {
    private final JTextField txtN = new JTextField();
    private final JButton btnCalculate = new JButton("Tính");
    private final JLabel lblResult = new JLabel("Kết quả:");
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public PrimeSumFrame() {
        setTitle("Bài 3 - Tổng các số nguyên tố");
        setSize(620, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        progressBar.setStringPainted(true);
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(txtN);
        panel.add(btnCalculate);
        panel.add(progressBar);
        panel.add(lblResult);
        add(panel);

        btnCalculate.addActionListener(e -> calculatePrimeSum());
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private void calculatePrimeSum() {
        final int n;
        try {
            n = Integer.parseInt(txtN.getText().trim());
            if (n <= 2) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "N phải là số nguyên lớn hơn 2.");
            return;
        }

        btnCalculate.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Đang tính...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() {
                long sum = 0;
                for (int i = 2; i < n; i++) {
                    if (isCancelled()) break;
                    if (isPrime(i)) sum += i;
                    setProgress((int) ((i * 100.0) / n));
                }
                return sum;
            }

            @Override
            protected void done() {
                try {
                    if (!isCancelled()) {
                        lblResult.setText("Tổng số nguyên tố nhỏ hơn " + n + " = " + get());
                        progressBar.setValue(100);
                    } else {
                        lblResult.setText("Đã hủy tác vụ");
                    }
                } catch (Exception ex) {
                    lblResult.setText("Có lỗi khi tính toán.");
                }
                btnCalculate.setEnabled(true);
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
