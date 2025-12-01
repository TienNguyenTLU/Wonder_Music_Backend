package com.ms.app.service;

import com.ms.app.dto.PlaylistDTO;
import com.ms.app.model.Playlist;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PlaylistService {
    Playlist create(PlaylistDTO playlist, MultipartFile coverFile, MultipartFile wallpaperFile);
    Playlist update(Long id, PlaylistDTO playlist, MultipartFile coverFile, MultipartFile wallpaperFile);
    void delete(Long id) throws IOException;
    List<Playlist> findAll();
    Playlist findById(Long id);
    List<Playlist> findByUserId();
}
