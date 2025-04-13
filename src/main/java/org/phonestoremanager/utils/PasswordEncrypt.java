package org.phonestoremanager.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class PasswordEncrypt {
    public static String toSHA256(String pass) {
        String salt = "Đố mày giải được HaHaHa";
        String result = null;
        pass += salt;
        try {
            byte[] dataByte = pass.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            result = Base64.getEncoder().encodeToString(md.digest(dataByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // mã hóa và giải mã bằng AES
//    private static final String SECRET_KEY = "1234567890123456"; // 16 ký tự = 128-bit
//    private static final String INIT_VECTOR = "RandomInitVector"; // 16 ký tự

    private static final String SECRET_KEY = "1234567890123dai"; // 16 ký tự = 128-bit
    private static final String INIT_VECTOR = "RandomInitVecdai";

    public static String encryptAES(String input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // dùng chế độ CBC và padding
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encrypted); // mã hóa ra base64 để dễ lưu trữ
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decryptAES(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Demo
    public static void main(String[] args) {
        String original = "123dai";

        String encrypted = encryptAES(original);
        String decrypted = decryptAES(encrypted);

        System.out.println("Chuỗi gốc: " + original);
        System.out.println("Chuỗi đã mã hóa: " + encrypted);
        System.out.println("Chuỗi đã giải mã: " + decrypted);
    }
}
