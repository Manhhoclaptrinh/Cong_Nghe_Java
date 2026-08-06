package vn.edu.eaut.lab2;

public class GradeCalculator {

    public static double calculateFinalScore(Student student) {
        return student.getAttendanceScore() * 0.1
                + student.getMidtermScore() * 0.3
                + student.getFinalScore() * 0.6;
    }

    public static String classify(double score) {

        if (score >= 8.5)
            return "A";
        if (score >= 7)
            return "B";
        if (score >= 5.5)
            return "C";
        if (score >= 4)
            return "D";

        return "F";
    }

    public static void validateScore(double score, String name) {

        if (score < 0 || score > 10) {
            throw new IllegalArgumentException(name + " phai tu 0 den 10.");
        }
    }
}