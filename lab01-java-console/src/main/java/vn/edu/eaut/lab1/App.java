package vn.edu.eaut.lab1;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {

            hienThiMenu();

            System.out.print("Chon bai tap: ");
            choice = scanner.nextInt();

            try {

                switch (choice) {

                    case 1:
                        bai1(scanner);
                        break;

                    case 2:
                        bai2(scanner);
                        break;

                    case 3:
                        bai3(scanner);
                        break;

                    case 4:
                        bai4(scanner);
                        break;

                    case 5:
                        bai5(scanner);
                        break;

                    case 0:
                        System.out.println("Ket thuc chuong trinh.");
                        break;

                    default:
                        System.out.println("Lua chon khong hop le.");
                }

            } catch (IllegalArgumentException e) {

                System.out.println("Loi: " + e.getMessage());

            }

            System.out.println();

        } while (choice != 0);

        scanner.close();
    }

    private static void hienThiMenu() {

        System.out.println("========= LAB 1 JAVA =========");
        System.out.println("1. Tong so chan");
        System.out.println("2. Tong nghich dao");
        System.out.println("3. Kiem tra so nguyen to");
        System.out.println("4. Phan loai tam giac");
        System.out.println("5. Day Fibonacci");
        System.out.println("0. Thoat");
    }

    private static void bai1(Scanner sc) {

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        System.out.println("Tong = " + So.tongChanDenN(n));
    }

    private static void bai2(Scanner sc) {

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        System.out.printf("Tong = %.4f%n", So.tongNghichDao(n));
    }

    private static void bai3(Scanner sc) {

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        if (So.laSoNguyenTo(n))
            System.out.println(n + " la so nguyen to");
        else
            System.out.println(n + " khong phai so nguyen to");
    }

    private static void bai4(Scanner sc) {

        System.out.print("Nhap a: ");
        double a = sc.nextDouble();

        System.out.print("Nhap b: ");
        double b = sc.nextDouble();

        System.out.print("Nhap c: ");
        double c = sc.nextDouble();

        System.out.println(So.loaiTamGiac(a, b, c));
    }

    private static void bai5(Scanner sc) {

        System.out.print("Nhap n: ");
        int n = sc.nextInt();

        System.out.println(So.dayFibonacci(n));
    }
}