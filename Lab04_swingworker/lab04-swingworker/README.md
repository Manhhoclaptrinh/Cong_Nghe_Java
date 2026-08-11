LAB 04 - SWINGWORKER
======================

Project: lab04-swingworker
Package: vn.edu.eaut.lab4
Java: 17+

CHẠY:
1. Mở project bằng IntelliJ IDEA.
2. Reload Maven.
3. Chạy src/main/java/vn/edu/eaut/lab4/App.java

Hoặc:
mvn clean package

10 bài:
1. Đồng hồ đếm ngược
2. Mô phỏng tải dữ liệu
3. Tổng số nguyên tố nhỏ hơn N
4. Fibonacci bằng memoization
5. Đếm số dòng file
6. Hủy tác vụ bằng cancel/isCancelled
7. Tìm kiếm từ khóa trong file
8. Đọc CSV điểm sinh viên + thống kê
9. Mô phỏng tải danh sách sản phẩm
10. CRUD sản phẩm + đọc/ghi CSV bằng SwingWorker

File test:
- sample_students.csv
- sample_products.csv
- sample_text.txt

Gợi ý ảnh minh chứng:
- Chụp màn hình menu App.
- Chụp từng bài khi đang chạy hoặc sau khi hoàn thành.
- Với Bài 6 chụp trạng thái "Đã hủy tác vụ".
- Với Bài 8 chụp JTable và điểm trung bình/điểm cao nhất.
- Với Bài 10 chụp thêm/sửa/xóa và đọc/ghi CSV.

Báo cáo ngắn nên nêu:
- EDT là luồng xử lý sự kiện của Swing.
- Không nên chạy tác vụ lâu trực tiếp trong ActionListener vì có thể làm giao diện treo.
- SwingWorker chạy tác vụ nền trong doInBackground().
- process() nhận dữ liệu publish() để cập nhật GUI.
- setProgress() cập nhật tiến độ qua PropertyChangeListener.
- done() chạy khi worker kết thúc và dùng để cập nhật trạng thái cuối.
