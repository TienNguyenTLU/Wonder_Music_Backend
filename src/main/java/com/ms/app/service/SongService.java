package com.ms.app.service;

import com.ms.app.dto.SongDTO;
import com.ms.app.model.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SongService {
    Song create(SongDTO song, MultipartFile mp3File, MultipartFile imageFile) throws IOException;
    void delete(Long id) throws IOException;
    List<Song> findAll();
    Song findById(Long id);
}
