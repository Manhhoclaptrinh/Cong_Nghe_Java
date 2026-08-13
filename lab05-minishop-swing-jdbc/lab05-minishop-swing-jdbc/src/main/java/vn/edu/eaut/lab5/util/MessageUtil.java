package vn.edu.eaut.lab5.util;

import javax.swing.*;

public class MessageUtil {
    private MessageUtil() {}

    public static void info(java.awt.Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(java.awt.Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Loi", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(java.awt.Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, "Xac nhan",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
