package com.ms.app.service;

import com.ms.app.model.PlaylistSong;

import java.util.List;

public interface PlaylistSongService {
    PlaylistSong addSong(PlaylistSong ps);
    void removeSong(Long playlistId, Long songId);
    List<PlaylistSong> findByPlaylist(Long id);
}
