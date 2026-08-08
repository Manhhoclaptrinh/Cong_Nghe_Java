package vn.edu.eaut.lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Bai08QuanLySinhVien extends JFrame {

    private final JTextField txtMaSV = new JTextField();
    private final JTextField txtHoTen = new JTextField();
    private final JTextField txtDiemTB = new JTextField();

    private final DefaultTableModel tableModel;

    private final JTable table;

    public Bai08QuanLySinhVien() {

        setTitle("Bài 8 - Quản lý sinh viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(
                new GridLayout(3, 2, 8, 8)
        );

        inputPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Thông tin sinh viên"
                )
        );

        inputPanel.add(new JLabel("Mã sinh viên:"));
        inputPanel.add(txtMaSV);

        inputPanel.add(new JLabel("Họ tên:"));
        inputPanel.add(txtHoTen);

        inputPanel.add(new JLabel("Điểm trung bình:"));
        inputPanel.add(txtDiemTB);

        JPanel buttonPanel = new JPanel(
                new FlowLayout()
        );

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);

        String[] columns = {
                "Mã sinh viên",
                "Họ tên",
                "Điểm TB",
                "Xếp loại"
        };

        tableModel = new DefaultTableModel(
                columns,
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        table = new JTable(tableModel);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnThem.addActionListener(
                e -> themSinhVien()
        );

        btnSua.addActionListener(
                e -> suaSinhVien()
        );

        btnXoa.addActionListener(
                e -> xoaSinhVien()
        );

        btnLamMoi.addActionListener(
                e -> lamMoi()
        );

        table.getSelectionModel()
                .addListSelectionListener(e -> hienThiSinhVien());

        setSize(650, 450);
        setLocationRelativeTo(null);
    }

    private Student layDuLieuTuForm() {

        String maSV =
                txtMaSV.getText().trim();

        String hoTen =
                txtHoTen.getText().trim();

        String diemText =
                txtDiemTB.getText().trim();

        if (maSV.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập mã sinh viên!"
            );

            txtMaSV.requestFocus();

            return null;
        }

        if (hoTen.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập họ tên!"
            );

            txtHoTen.requestFocus();

            return null;
        }

        try {

            double diem =
                    Double.parseDouble(diemText);

            if (diem < 0 || diem > 10) {

                JOptionPane.showMessageDialog(
                        this,
                        "Điểm trung bình phải từ 0 đến 10!"
                );

                txtDiemTB.requestFocus();

                return null;
            }

            return new Student(
                    maSV,
                    hoTen,
                    diem
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Điểm trung bình phải là số hợp lệ!"
            );

            txtDiemTB.requestFocus();

            return null;
        }
    }

    private void themSinhVien() {

        Student student =
                layDuLieuTuForm();

        if (student == null) {
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            String ma =
                    tableModel.getValueAt(
                            i,
                            0
                    ).toString();

            if (ma.equalsIgnoreCase(
                    student.getMaSinhVien()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Mã sinh viên đã tồn tại!"
                );

                return;
            }
        }

        tableModel.addRow(
                new Object[]{
                        student.getMaSinhVien(),
                        student.getHoTen(),
                        student.getDiemTrungBinh(),
                        student.getXepLoai()
                }
        );

        JOptionPane.showMessageDialog(
                this,
                "Thêm sinh viên thành công!"
        );

        lamMoi();
    }

    private void suaSinhVien() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sinh viên cần sửa!"
            );

            return;
        }

        Student student =
                layDuLieuTuForm();

        if (student == null) {
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            if (i == selectedRow) {
                continue;
            }

            String ma =
                    tableModel.getValueAt(
                            i,
                            0
                    ).toString();

            if (ma.equalsIgnoreCase(
                    student.getMaSinhVien()
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Mã sinh viên đã tồn tại!"
                );

                return;
            }
        }

        tableModel.setValueAt(
                student.getMaSinhVien(),
                selectedRow,
                0
        );

        tableModel.setValueAt(
                student.getHoTen(),
                selectedRow,
                1
        );

        tableModel.setValueAt(
                student.getDiemTrungBinh(),
                selectedRow,
                2
        );

        tableModel.setValueAt(
                student.getXepLoai(),
                selectedRow,
                3
        );

        JOptionPane.showMessageDialog(
                this,
                "Sửa sinh viên thành công!"
        );

        lamMoi();
    }

    private void xoaSinhVien() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sinh viên cần xóa!"
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc muốn xóa sinh viên này?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm == JOptionPane.YES_OPTION) {

            tableModel.removeRow(
                    selectedRow
            );

            lamMoi();

            JOptionPane.showMessageDialog(
                    this,
                    "Xóa sinh viên thành công!"
            );
        }
    }

    private void hienThiSinhVien() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        txtMaSV.setText(
                tableModel.getValueAt(
                        selectedRow,
                        0
                ).toString()
        );

        txtHoTen.setText(
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString()
        );

        txtDiemTB.setText(
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString()
        );
    }

    private void lamMoi() {

        txtMaSV.setText("");
        txtHoTen.setText("");
        txtDiemTB.setText("");

        table.clearSelection();

        txtMaSV.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new Bai08QuanLySinhVien().setVisible(true)
        );
    }
}