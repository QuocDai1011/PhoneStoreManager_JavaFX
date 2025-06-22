package org.phonestoremanager.utils;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeGenerator {
    public static void main(String[] args) {
        try {
            // Đọc file QR từ ổ cứng
            File file = new File("qrcode.png");
            BufferedImage bufferedImage = ImageIO.read(file);

            // Phân tích ảnh QR
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);

            // Nếu decode thành công
            System.out.println("Nội dung mã QR: " + result.getText());
            System.out.println("quét thành công");
        } catch (IOException e) {
            System.out.println("Không đọc được file ảnh: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("Không tìm thấy mã QR trong ảnh.");
        }
    }
}
