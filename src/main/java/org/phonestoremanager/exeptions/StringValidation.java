package org.phonestoremanager.exeptions;

public class StringValidation {
    public static String validateString(String input) {
        if (input.matches(".*[0-9\\W].*")) { // \W đại diện cho ký tự không phải chữ cái
            throw new IllegalArgumentException("Dữ liệu không được chứa số hoặc ký tự đặc biệt!");
        }
        return "success";
    }

    public static void main(String[] args) {
        try {
            validateString("Đinh");
            System.out.println("Ok");
            return;// Lỗi
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
