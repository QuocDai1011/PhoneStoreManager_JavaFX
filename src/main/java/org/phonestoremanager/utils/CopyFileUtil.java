package org.phonestoremanager.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CopyFileUtil {
    public static CopyFileUtil getInstance() {
        return new CopyFileUtil();
    }

    public String copyImage(String sourcePathStr, String destinationPathStr) {
        try {
            File sourceFile = new File(sourcePathStr);
            if (!sourceFile.exists()) {
                System.out.println("Ảnh không tồn tại!");
                return null;
            }

            File destinationFile = new File(destinationPathStr);
            File parentDir = destinationFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            Path sourcePath = sourceFile.toPath();
            Path destinationPath = destinationFile.toPath();

            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Ảnh đã được sao chép đến: " + destinationPath);
            return destinationPathStr;  // Trả về đường dẫn chỉ khi copy thành công

        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Trả về null nếu có lỗi xảy ra
        }
    }
}
