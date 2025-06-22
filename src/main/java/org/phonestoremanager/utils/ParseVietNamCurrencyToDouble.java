package org.phonestoremanager.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ParseVietNamCurrencyToDouble {
    public static double parseVietnamCurrency(String input) {
        if (input == null || input.isEmpty()) return 0;

        // Bỏ ký tự tiền tệ và khoảng trắng
        String cleaned = input.replaceAll("[^\\d]", ""); // chỉ giữ số

        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            System.err.println("Lỗi định dạng tiền tệ: " + input);
            return 0;
        }
    }

    public static String formatToVietnamCurrency(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return formatter.format(amount);
    }
}
