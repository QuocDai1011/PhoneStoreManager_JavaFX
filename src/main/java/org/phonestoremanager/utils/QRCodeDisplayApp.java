package org.phonestoremanager.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class QRCodeDisplayApp extends Application {

    private Label statusLabel;
    private Label ipAddressLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Tìm địa chỉ IP thực của máy tính
        String serverIP =  "192.168.1.40";
        String qrContent = "http://" + serverIP + ":8080/scan-success";

        // Tạo các thành phần UI
        ImageView qrImageView = new ImageView();
        qrImageView.setFitWidth(300);
        qrImageView.setFitHeight(300);

        Button generateButton = new Button("Tạo mã QR");
        statusLabel = new Label("Chưa có quét");
        statusLabel.setStyle("-fx-font-size: 16px;");

        ipAddressLabel = new Label("IP máy chủ: " + serverIP);
        ipAddressLabel.setStyle("-fx-font-size: 14px;");

        // Xử lý sự kiện khi nhấn nút tạo mã QR
        generateButton.setOnAction(e -> {
            try {
                // Tạo QR code và lưu ra file
                generateQRCodeToFile(qrContent, 300, 300, "qrcode.png");

                // Đọc file ảnh và hiển thị lên ImageView
                Image image = new Image(new FileInputStream("qrcode.png"));
                qrImageView.setImage(image);

                statusLabel.setText("Đã tạo mã QR. Chờ quét...");
                System.out.println("Đã tạo mã QR: " + qrContent);
            } catch (WriterException | IOException ex) {
                statusLabel.setText("Lỗi khi tạo mã QR");
                ex.printStackTrace();
            }
        });

        // Tạo layout
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(generateButton, qrImageView, statusLabel, ipAddressLabel);
        root.setStyle("-fx-padding: 20;");

        // Tạo scene và hiển thị
        Scene scene = new Scene(root, 400, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quét mã QR");
        primaryStage.show();

        // Bắt đầu server HTTP
        startHttpServer();
    }

    private String getLocalIPAddress() {
        try {
            // Duyệt qua tất cả các network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                // Bỏ qua các interfaces không hoạt động và loopback
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        String ipAddress = addr.getHostAddress();
                        // Chỉ lấy IPv4 không phải IPv6
                        if (!ipAddress.contains(":") && !ipAddress.equals("192.168.1.40")) {
                            return ipAddress;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "192.168.1.40"; // Fallback nếu không tìm thấy
    }

    private void startHttpServer() {
        try {
            // Tạo server không giới hạn connection
            int port = 8080;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            // Thêm context handlers
            server.createContext("/scan-success", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    // In ra thông báo khi nhận tín hiệu từ điện thoại
                    System.out.println("Quét thành công từ thiết bị: " + exchange.getRemoteAddress());

                    // Trả về HTML response cho điện thoại để hiển thị trang thành công
                    String htmlResponse = "<html><body style='text-align:center;font-family:Arial;'>" +
                            "<h1>Thanh toán thành công</h1>" +
                            "<p>Cảm ơn bạn đã mua hàng. <3</p>" +
                            "</body></html>";

                    exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                    exchange.sendResponseHeaders(200, htmlResponse.getBytes("UTF-8").length);

                    // Gửi response về cho điện thoại
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(htmlResponse.getBytes("UTF-8"));
                    }
                    // Cập nhật UI trong JavaFX thread
                    Platform.runLater(() -> {
                        statusLabel.setText("Quét thành công!");
                        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: green; -fx-font-weight: bold;");
//                        JavaMail.sendMail("greefaife@gmail.com", "fjns hunp bfpj qbhf",
//                                "nhan01282544104@gmail.com", 0);
                    });
                }
            });

            server.setExecutor(null); // Sử dụng executor mặc định
            server.start();
            System.out.println("Server started on port " + port);


        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Lỗi khi khởi động server: " + e.getMessage());
        }
    }

    private void generateQRCodeToFile(String content, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
        Path path = Paths.get(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    @Override
    public void stop() {
        // Đóng server khi tắt ứng dụng (nếu cần)
        System.out.println("Đóng ứng dụng và server");
    }
}
