//package Controller;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/v1/images")
//public class ImageUploadController {
//
//	private static final String UPLOAD_DIR = "uploads/";
//
//	@PostMapping("/uploads")
//	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//		try {
//			if (file.isEmpty()) {
//				return ResponseEntity.badRequest().body("File is empty");
//			}
//
//			// Create uploads directory if it doesn't exist
//			File directory = new File(UPLOAD_DIR);
//			if (!directory.exists()) {
//				directory.mkdirs();
//			}
//
//			// Save the file
//			String filePath = UPLOAD_DIR + file.getOriginalFilename();
//			file.transferTo(new File(filePath));
//
//			return ResponseEntity.ok("Image uploaded successfully: " + file.getOriginalFilename());
//		} catch (IOException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
//		}
//	}
//}
package com.example.rushi.controller;


import com.example.rushi.dto.ImageUploadResponseDTO;
import com.example.rushi.entity.ImageFile;
import com.example.rushi.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageUploadController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponseDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageUploadResponseDTO response = imageService.uploadImage(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ImageFile>> getAllUploadedImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }
}
