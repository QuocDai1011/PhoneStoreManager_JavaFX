package org.phonestoremanager.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    //lay ngay thang nam va thoi gian
    public static String getCurrentDateTime() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Định dạng ngày tháng năm + giờ phút giây (nếu cần)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Trả về chuỗi định dạng để lưu vào DB
        return now.format(formatter);
    }

    // Nếu chỉ muốn ngày tháng (không có giờ)
    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getCurrentDate());
    }
}
