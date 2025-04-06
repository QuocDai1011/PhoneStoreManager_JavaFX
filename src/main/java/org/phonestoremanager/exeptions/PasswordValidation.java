package org.phonestoremanager.exeptions;

public class PasswordValidation {
    public static void validate(String password) throws IllegalArgumentException {
        if (!password.matches(".*[A-Za-z].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất một chữ cái!");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất một chữ số!");
        }
    }
}
