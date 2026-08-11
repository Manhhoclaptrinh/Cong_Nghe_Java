package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CountdownFrame extends JFrame {
    private final JTextField txtSeconds = new JTextField();
    private final JButton btnStart = new JButton("Bắt đầu");
    private final JLabel lblTime = new JLabel("Thời gian còn lại:");

    public CountdownFrame() {
        setTitle("Bài 1 - Đồng hồ đếm ngược");
        setSize(420, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        lblTime.setHorizontalAlignment(SwingConstants.CENTER);
        lblTime.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(txtSeconds);
        panel.add(btnStart);
        panel.add(lblTime);
        add(panel);

        btnStart.addActionListener(e -> startCountdown());
    }

    private void startCountdown() {
        int seconds;
        try {
            seconds = Integer.parseInt(txtSeconds.getText().trim());
            if (seconds <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên lớn hơn 0.");
            return;
        }

        btnStart.setEnabled(false);
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = seconds; i >= 0; i--) {
                    if (isCancelled()) break;
                    publish(i);
                    Thread.sleep(1000);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                if (!chunks.isEmpty()) {
                    lblTime.setText("Thời gian còn lại: " + chunks.get(chunks.size() - 1) + " giây");
                }
            }

            @Override
            protected void done() {
                btnStart.setEnabled(true);
                if (!isCancelled()) {
                    JOptionPane.showMessageDialog(CountdownFrame.this, "Hoàn thành!");
                }
            }
        };
        worker.execute();
    }
}
