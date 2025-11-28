package com.ms.app.controller;

import com.ms.app.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadAudio(@RequestParam("file") MultipartFile file) {
        try {
            Map data = cloudinaryService.uploadFile(file);
            // data sẽ chứa url, public_id, duration, v.v.
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}