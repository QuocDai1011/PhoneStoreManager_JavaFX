package org.phonestoremanager.utils;

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

    public static void main(String[] args) {
        System.out.println(toSHA256("123dai"));
    }
}
