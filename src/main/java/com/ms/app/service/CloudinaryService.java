package com.ms.app.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map uploadFile(MultipartFile file) throws IOException {
        // "resource_type", "auto" là QUAN TRỌNG NHẤT khi upload MP3/Video
        // Nếu để default, nó sẽ coi là ảnh và có thể gây lỗi với file nhạc.
        return cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "auto",
                        "folder", "music_app_mp3" // Tên thư mục trên Cloudinary
                ));
    }

    // Hàm xóa file (nếu cần)
    public Map deleteFile(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId,
                ObjectUtils.asMap("resource_type", "video")); // MP3 thường được xếp vào nhóm video/raw
    }
}