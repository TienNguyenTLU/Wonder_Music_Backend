package com.ms.app.service;
import com.ms.app.dto.PlaylistSongDTO;
import com.ms.app.model.PlaylistSong;

import java.util.List;

public interface PlaylistSongService {
    PlaylistSong addSong(PlaylistSongDTO ps);
    void removeSong(Long playlistId, Long songId);
    List<PlaylistSong> findByPlaylist(Long id);
    void deleteById(Long id);
}
