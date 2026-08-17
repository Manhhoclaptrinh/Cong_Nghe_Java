package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StudentStore {
    private static final List<Student> students =
            Collections.synchronizedList(new ArrayList<>());

    private StudentStore() {}

    public static List<Student> findAll() {
        synchronized (students) {
            return new ArrayList<>(students);
        }
    }

    public static void add(Student student) {
        if (student == null) return;
        synchronized (students) {
            students.add(student);
        }
    }

    public static Student findById(String id) {
        if (id == null) return null;
        synchronized (students) {
            for (Student student : students) {
                if (id.equals(student.getId())) return student;
            }
        }
        return null;
    }

    public static boolean exists(String id) {
        return findById(id) != null;
    }

    public static boolean delete(String id) {
        if (id == null) return false;
        synchronized (students) {
            return students.removeIf(student -> id.equals(student.getId()));
        }
    }

    public static boolean update(String id, String name, String className, String email) {
        if (id == null) return false;
        synchronized (students) {
            for (Student student : students) {
                if (id.equals(student.getId())) {
                    student.setName(name);
                    student.setClassName(className);
                    student.setEmail(email);
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String q = keyword.trim().toLowerCase();
        List<Student> result = new ArrayList<>();

        synchronized (students) {
            for (Student student : students) {
                if (student.getName() != null
                        && student.getName().toLowerCase().contains(q)) {
                    result.add(student);
                }
            }
        }
        return result;
    }

    public static int count() {
        return students.size();
    }

    public static void clear() {
        students.clear();
    }
}
