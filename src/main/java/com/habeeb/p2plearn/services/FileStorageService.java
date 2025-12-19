// java
package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.models.ImageTypes;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface FileStorageService {
    Path store(MultipartFile file, ImageTypes imageType) throws Exception;
    void deleteFile(String fileName, ImageTypes imageType) throws Exception;
    String toPublicUrl(Path storedPath,ImageTypes imageType);
}
