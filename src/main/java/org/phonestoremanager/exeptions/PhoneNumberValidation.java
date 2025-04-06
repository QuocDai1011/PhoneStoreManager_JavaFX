package org.phonestoremanager.exeptions;

public class PhoneNumberValidation {
    public static void validatePhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (!phoneNumber.matches("0\\d{9}")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.");
        }
    }

    public static void main(String[] args) {
        try {
            validatePhoneNumber("0987654321"); // ✅ Hợp lệ
            System.out.println("Số điện thoại hợp lệ!");

            validatePhoneNumber("1234567890"); // ❌ Ném ngoại lệ
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }

        try {
            validatePhoneNumber("09876abc21"); // ❌ Ném ngoại lệ
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }

        try {
            validatePhoneNumber("098765432"); // ❌ Ném ngoại lệ
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}
