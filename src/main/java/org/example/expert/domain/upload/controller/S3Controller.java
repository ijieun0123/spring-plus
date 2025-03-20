package org.example.expert.domain.upload.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.upload.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = s3Service.saveFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }

    // 프리사인드 URL 제공 (파일 다운로드)
    @GetMapping("/download")
    public ResponseEntity<String> getDownloadUrl(@RequestParam("filename") String filename) {
        try {
            String presignedUrl = s3Service.getPresignedUrl(filename);
            return ResponseEntity.ok(presignedUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to generate download URL: " + e.getMessage());
        }
    }

    // 파일 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("filename") String filename) {
        try {
            s3Service.deleteImage(filename);
            return ResponseEntity.ok("File deleted successfully: " + filename);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete file: " + e.getMessage());
        }
    }
}

