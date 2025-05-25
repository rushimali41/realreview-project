package com.example.rushi.service;

import com.example.rushi.dto.ImageUploadResponseDTO;
import com.example.rushi.entity.ImageFile;
import com.example.rushi.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = "C:/uploaded-images/";

    @Autowired
    private ImageRepository imageRepository;

    public ImageUploadResponseDTO uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            return new ImageUploadResponseDTO(null, "File is empty");
        }

        try {
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    return new ImageUploadResponseDTO(null, "Could not create upload directory");
                }
            }

            String originalFilename = Paths.get(file.getOriginalFilename()).getFileName().toString();
            String filePath = UPLOAD_DIR + originalFilename;

            file.transferTo(new File(filePath));

            // Save file metadata to DB
            ImageFile imageFile = new ImageFile(originalFilename, filePath);
            imageRepository.save(imageFile);

            return new ImageUploadResponseDTO(originalFilename, "Image uploaded successfully");

        } catch (IOException e) {
            e.printStackTrace();
            return new ImageUploadResponseDTO(null, "Upload failed: " + e.getMessage());
        }
    }

    public List<ImageFile> getAllImages() {
        return imageRepository.findAll();
    }
}
