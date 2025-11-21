package com.ms.app.service;

import com.ms.app.dto.PlaylistDTO;
import com.ms.app.model.Playlist;

import java.util.List;

public interface PlaylistService {
    Playlist create(PlaylistDTO playlist);
    Playlist update(Long id, PlaylistDTO playlist);
    void delete(Long id);
    List<Playlist> findAll();
    Playlist findById(Long id);
}
