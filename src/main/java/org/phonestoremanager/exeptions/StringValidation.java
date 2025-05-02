package org.phonestoremanager.exeptions;

public class StringValidation {
    public static String validateString(String input) {
        if (!input.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Dữ liệu chỉ được chứa chữ cái và khoảng trắng!");
        }
        return "success";
    }


    public static void main(String[] args) {
        try {
            validateString("Dinh");
            System.out.println("Ok");
            return;// Lỗi
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
