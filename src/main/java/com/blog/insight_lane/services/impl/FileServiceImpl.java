package com.blog.insight_lane.services.impl;

import com.blog.insight_lane.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        List<String> validExtensionList = Arrays.asList("jpg", "jpeg", "png");
        if (!validExtensionList.contains(fileExtension.toLowerCase())) {
            throw new InvalidPropertiesFormatException(fileExtension);
        }
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(fileExtension);

        String filePath = path + File.separator + fileName;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws Exception {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
