package com.ms.app.service;

import com.ms.app.dto.PlaylistDTO;
import com.ms.app.model.Playlist;
import com.ms.app.repository.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository repo;
    private final CloudinaryService cloudinaryService;
    public PlaylistServiceImpl(PlaylistRepository repo, CloudinaryService cloudinaryService) {
        this.repo = repo;
        this.cloudinaryService = cloudinaryService;
    }
    @Override
    public Playlist create(PlaylistDTO playlist, MultipartFile coverFile, MultipartFile wallpaperFile) {
        Playlist play = new Playlist();
        play.setName(playlist.getName());
        play.setDescription(playlist.getDescription());
        try {
            Map coverData = cloudinaryService.uploadImage(coverFile);
            play.setCoverUrl((String) coverData.get("url"));
        } catch (IOException e) {
            System.err.println("Error uploading cover file to Cloudinary: " + e.getMessage());
        }
        try {
            Map wallpaperData = cloudinaryService.uploadWallpaper(wallpaperFile);
            play.setWallpaperUrl((String) wallpaperData.get("url"));
        } catch (IOException e) {
            System.err.println("Error uploading wallpaper file to Cloudinary: " + e.getMessage());
        }
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
