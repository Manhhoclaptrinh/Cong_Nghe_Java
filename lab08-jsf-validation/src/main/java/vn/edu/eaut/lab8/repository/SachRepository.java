package vn.edu.eaut.lab8.repository;

import java.util.ArrayList;
import java.util.List;

import vn.edu.eaut.lab8.model.Sach;

public class SachRepository {

    private static final List<Sach> data = new ArrayList<>();

    private static int autoId = 3;

    static {

        data.add(
            new Sach(
                1,
                "S001",
                "Lập trình Java",
                "Nguyễn Văn A",
                "NXB Giáo dục",
                2024
            )
        );

        data.add(
            new Sach(
                2,
                "S002",
                "Công nghệ Java",
                "Trần Văn B",
                "NXB Khoa học",
                2025
            )
        );
    }

    public List<Sach> findAll() {
        return data;
    }

    public void add(Sach sach) {

        sach.setId(autoId++);

        data.add(sach);
    }

    public void delete(int id) {

        data.removeIf(
            sach -> sach.getId() == id
        );
    }
}