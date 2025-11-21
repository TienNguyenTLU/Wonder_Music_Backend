package com.ms.app.service;

import com.ms.app.dto.PlaylistDTO;
import com.ms.app.model.Playlist;
import com.ms.app.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository repo;
    public PlaylistServiceImpl(PlaylistRepository repo) {
        this.repo = repo;
    }
    @Override
    public Playlist create(PlaylistDTO playlist) {
        Playlist play = new Playlist();
        play.setName(playlist.getName());
        play.setDescription(playlist.getDescription());
        play.setCoverUrl(playlist.getCoverUrl());
        play.setWallpaperUrl(playlist.getWallpaperUrl());
        play.setIsPublic(playlist.getIsPublic());
        return repo.save(play);
    }
    @Override
    public Playlist update(Long id, PlaylistDTO playlist) {
        Playlist old = repo.findById(id).orElseThrow();
        old.setName(playlist.getName());
        old.setDescription(playlist.getDescription());
        old.setCoverUrl(playlist.getCoverUrl());
        old.setWallpaperUrl(playlist.getWallpaperUrl());
        return repo.save(old);
    }
    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
    @Override
    public List<Playlist> findAll() {
        return repo.findAll();
    }

    @Override
    public Playlist findById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
