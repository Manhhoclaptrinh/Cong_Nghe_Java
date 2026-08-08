package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai07MayTinhMini extends JFrame {

    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();

    private final JLabel lblResult =
            new JLabel("Kết quả: ");

    private final JTextArea txtHistory =
            new JTextArea(8, 35);

    public Bai07MayTinhMini() {

        setTitle("Bài 7 - Máy tính mini");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(
                new GridLayout(2, 2, 8, 8)
        );

        inputPanel.add(new JLabel("Số thứ nhất:"));
        inputPanel.add(txtA);

        inputPanel.add(new JLabel("Số thứ hai:"));
        inputPanel.add(txtB);

        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 5, 5, 5)
        );

        JButton btnAdd = new JButton("+");
        JButton btnSub = new JButton("-");
        JButton btnMul = new JButton("*");
        JButton btnDiv = new JButton("/");
        JButton btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSub);
        buttonPanel.add(btnMul);
        buttonPanel.add(btnDiv);
        buttonPanel.add(btnClear);

        txtHistory.setEditable(false);
        txtHistory.setLineWrap(true);

        JPanel topPanel = new JPanel(
                new BorderLayout(5, 5)
        );

        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(lblResult, BorderLayout.CENTER);

        JPanel historyPanel = new JPanel(
                new BorderLayout()
        );

        historyPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Lịch sử"
                )
        );

        historyPanel.add(
                new JScrollPane(txtHistory),
                BorderLayout.CENTER
        );

        add(historyPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(
                e -> tinhToan("+")
        );

        btnSub.addActionListener(
                e -> tinhToan("-")
        );

        btnMul.addActionListener(
                e -> tinhToan("*")
        );

        btnDiv.addActionListener(
                e -> tinhToan("/")
        );

        btnClear.addActionListener(
                e -> lamMoi()
        );

        setSize(500, 430);
        setLocationRelativeTo(null);
    }

    private void tinhToan(String operator) {

        try {

            double a = Double.parseDouble(
                    txtA.getText().trim()
            );

            double b = Double.parseDouble(
                    txtB.getText().trim()
            );

            double result;

            switch (operator) {

                case "+":
                    result = a + b;
                    break;

                case "-":
                    result = a - b;
                    break;

                case "*":
                    result = a * b;
                    break;

                case "/":

                    if (b == 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Không thể chia cho 0!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }

                    result = a / b;
                    break;

                default:
                    return;
            }

            lblResult.setText(
                    "Kết quả: " + result
            );

            txtHistory.append(
                    a + " " + operator + " "
                            + b + " = " + result + "\n"
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập hai số hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void lamMoi() {

        txtA.setText("");
        txtB.setText("");

        lblResult.setText("Kết quả: ");

        txtHistory.setText("");

        txtA.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai07MayTinhMini().setVisible(true)
        );
    }
}