package vn.edu.eaut.lab8.repository;

import java.util.ArrayList;
import java.util.List;

import vn.edu.eaut.lab8.model.SinhVien;

public class SinhVienRepository {

    private static final List<SinhVien> data = new ArrayList<>();

    private static int autoId = 3;

    static {
        data.add(
            new SinhVien(
                1,
                "20240001",
                "Nguyễn Văn An",
                "an@gmail.com",
                "DCCNTT15.10.1"
            )
        );

        data.add(
            new SinhVien(
                2,
                "20240002",
                "Trần Thị Bình",
                "binh@gmail.com",
                "DCCNTT15.10.2"
            )
        );
    }

    public List<SinhVien> findAll() {
        return data;
    }

    public void add(SinhVien sv) {
        sv.setId(autoId++);
        data.add(sv);
    }

    public void update(SinhVien sv) {

        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).getId() == sv.getId()) {
                data.set(i, sv);
                return;
            }
        }
    }

    public void delete(int id) {
        data.removeIf(x -> x.getId() == id);
    }

    public SinhVien findById(int id) {

        for (SinhVien sv : data) {

            if (sv.getId() == id) {
                return sv;
            }
        }

        return null;
    }

    public List<SinhVien> search(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return data;
        }

        String key = keyword.toLowerCase().trim();

        List<SinhVien> result = new ArrayList<>();

        for (SinhVien sv : data) {

            if (sv.getHoTen().toLowerCase().contains(key)
                    || sv.getLop().toLowerCase().contains(key)) {

                result.add(sv);
            }
        }

        return result;
    }
}