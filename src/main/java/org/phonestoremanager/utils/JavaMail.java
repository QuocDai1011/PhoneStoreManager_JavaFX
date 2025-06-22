package org.phonestoremanager.utils;
import org.phonestoremanager.exeptions.EmailDomainValidator;
import org.phonestoremanager.models.OrderUpdateModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMail {

    public static boolean sendMail(String emailTo, int index, String customerName,
                                   double total, List<OrderUpdateModel> list, boolean paid) {
        String domain = emailTo.substring(emailTo.indexOf("@") + 1);
        if (!EmailDomainValidator.hasMXRecord(domain)) {
            return false;
        }

        // 1. Thông tin tài khoản email gửi
        final String username = "greefaife@gmail.com"; // thay bằng email của bạn
        final String password = "fjns hunp bfpj qbhf";         // thay bằng mật khẩu ứng dụng (không phải mật khẩu gmail!)

        // 2. Cấu hình SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // 3. Tạo phiên gửi email
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            String pdfPath;
            if(paid) {
                pdfPath = PdfGenerator.createPdf(index, customerName, total, list); // Tạo file PDF
            }else {
                pdfPath = PdfGenerator.pdfCreateWithoutPayment(index, customerName, total, list);
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Đơn hàng #" + index);

            // Nội dung email
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Đơn hàng của bạn đã được xử lý. File đính kèm là hóa đơn.");

            // Đính kèm file PDF
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(pdfPath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Đã gửi Email cho " + emailTo + "thành công!");
            return true;

        } catch (MessagingException e) {
            System.out.println("khong ton tai email");
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {
        //Quốc Đại
        List<OrderUpdateModel> listTemp = new ArrayList<>();

        OrderUpdateModel sp1 = new OrderUpdateModel("iPhone 14 Pro Max", 6, 128, "Đen", 1, "27.990.000");
        OrderUpdateModel sp2 = new OrderUpdateModel("Samsung Galaxy S23 Ultra", 12, 256, "Xanh", 2, "24.490.000");
        OrderUpdateModel sp3 = new OrderUpdateModel("Xiaomi 13T", 8, 256, "Xanh Rêu", 1, "9.990.000");

        listTemp.add(sp1);
        listTemp.add(sp2);
        listTemp.add(sp3);
        //nhan01282544104@gmail.com
        JavaMail.sendMail("nhan01282544104@gmail.com", 32, "Đinh Nhật Huyền Nhân", 600000000, listTemp, false);
        JavaMail.sendMail("nhan01282544104@gmail.com", 33, "Đinh Nhật Huyền Nhân", 600000000, listTemp, true);
    }
}
