package com.ms.app.service;

import com.ms.app.model.PlaylistSong;
import com.ms.app.repository.PlaylistSongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistSongRepository repo;

    public PlaylistSongServiceImpl(PlaylistSongRepository repo) {
        this.repo = repo;
    }

    @Override
    public PlaylistSong addSong(PlaylistSong ps) {
        return repo.save(ps);
    }

    @Override
    public void removeSong(Long playlistId, Long songId) {
        repo.deleteByPlaylistIdAndSongId(playlistId, songId);
    }

    @Override
    public List<PlaylistSong> findByPlaylist(Long id) {
        return repo.findByPlaylistId(id);
    }
}
