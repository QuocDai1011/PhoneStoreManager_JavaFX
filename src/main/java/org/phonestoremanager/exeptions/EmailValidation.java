package org.phonestoremanager.exeptions;

public class EmailValidation {
    public static boolean validation(String email) throws IllegalArgumentException{
        if (email == null || !email.endsWith("@gmail.com")) {
            throw new IllegalArgumentException("Email không hợp lệ. Email phải kết thúc bằng '@gmail.com'.");
        }
        return true;
    }
}
