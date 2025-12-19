// java
package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.models.ImageTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadDir;

    public FileStorageServiceImpl(@Value("${file.upload-dir:uploads}") String uploadDir) throws IOException {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadDir);
        Files.createDirectories(this.uploadDir.resolve("avatars"));
        Files.createDirectories(this.uploadDir.resolve("articles"));

        System.out.println("Upload directories created at: " + this.uploadDir);
    }


    @Override
    public Path store(MultipartFile file, ImageTypes imageType) throws IOException {
        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = "";
        int idx = original.lastIndexOf('.');
        if (idx > 0) ext = original.substring(idx);
        String filename = UUID.randomUUID().toString() + ext;
        String subdirectory = getSubdirectory(imageType);
        Path target = this.uploadDir.resolve(subdirectory).resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("file saved at "+target);
        return target;
    }

    @Override
    public String toPublicUrl(Path storedPath, ImageTypes imageType) {
        String subdirectory = getSubdirectory(imageType);

        // Return URL: /uploads/subdirectory/filename
        String url = "/uploads/" + subdirectory + "/" + storedPath.getFileName().toString();

        System.out.println("Public URL: " + url);
        return url;
    }
    @Override
    public void deleteFile(String filename, ImageTypes imageType) throws IOException {
        String subdirectory = getSubdirectory(imageType);
        Path filePath = this.uploadDir.resolve(subdirectory).resolve(filename);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
            System.out.println("Deleted file: " + filePath);
        }
    }
    private String getSubdirectory(ImageTypes imageType) {
        return switch (imageType) {
            case PROFILE -> "avatars";
            case ARTICLE_COVER -> "articles";
            default -> "others";
        };
    }
}