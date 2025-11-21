package com.ms.app.service;

import com.ms.app.dto.SongDTO;
import com.ms.app.model.Song;

import java.util.List;

public interface SongService {
    Song create(SongDTO song);
    Song update(Long id, SongDTO song);
    void delete(Long id);
    List<Song> findAll();
    Song findById(Long id);
}
