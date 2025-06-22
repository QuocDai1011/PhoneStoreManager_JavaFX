package org.phonestoremanager.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.phonestoremanager.models.OrderUpdateModel;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfGenerator {
    public static String createPdf(int index, String customerName, double total, List<OrderUpdateModel> list) {
        String filePath = "order-" + index + ".pdf";
        String totalString = ParseVietNamCurrencyToDouble.formatToVietnamCurrency(total);

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 16, Font.BOLD);
            Font normalFont = new Font(baseFont, 12);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            String dateTime = now.format(formatter);

            // Tiêu đề
            Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            LineSeparator line = new LineSeparator();
            document.add(line);
            document.add(Chunk.NEWLINE);

            // Thông tin khách hàng
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{3, 7});

            infoTable.addCell(createCell("Mã đơn hàng:", normalFont, true));
            infoTable.addCell(createCell("#" + index, normalFont, false));

            infoTable.addCell(createCell("Thời gian:", normalFont, true));
            infoTable.addCell(createCell(dateTime, normalFont, false));

            infoTable.addCell(createCell("Khách hàng:", normalFont, true));
            infoTable.addCell(createCell(customerName, normalFont, false));

            document.add(infoTable);
            document.add(Chunk.NEWLINE);

            // Bảng sản phẩm
            PdfPTable productTable = new PdfPTable(6);
            productTable.setWidthPercentage(100);
            productTable.setWidths(new float[]{3, 2, 2, 2, 2, 3});
            productTable.setSpacingBefore(10f);
            productTable.setSpacingAfter(10f);

            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            String[] headers = {"Tên sản phẩm", "RAM", "ROM", "Màu sắc", "Số lượng", "Đơn giá"};

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(5);
                productTable.addCell(cell);
            }

            for (OrderUpdateModel item : list) {
                productTable.addCell(new Phrase(item.getProductName(), normalFont));
                productTable.addCell(new Phrase(item.getRam() + "GB", normalFont));
                productTable.addCell(new Phrase(item.getRom() + "GB", normalFont));
                productTable.addCell(new Phrase(item.getColor(), normalFont));
                productTable.addCell(new Phrase(String.valueOf(item.getQuantity()), normalFont));
                productTable.addCell(new Phrase(item.getUnitPrice(), normalFont));
            }

            document.add(productTable);

            // Tổng tiền
            Paragraph totalPara = new Paragraph("Tổng tiền: " + totalString + " VNĐ", headerFont);
            totalPara.setAlignment(Element.ALIGN_RIGHT);
            totalPara.setSpacingBefore(10);
            document.add(totalPara);

            // Lời cảm ơn
            Paragraph thank = new Paragraph("Cảm ơn bạn đã thanh toán thành công!", normalFont);
            thank.setSpacingBefore(20);
            thank.setAlignment(Element.ALIGN_CENTER);
            document.add(thank);

            document.close();
            System.out.println("Tạo PDF thành công: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }

    private static PdfPCell createCell(String content, Font font, boolean alignRight) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignRight ? Element.ALIGN_RIGHT : Element.ALIGN_LEFT);
        return cell;
    }

}
