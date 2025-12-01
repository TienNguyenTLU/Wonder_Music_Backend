package com.ms.app.service;

import com.ms.app.dto.PlayListSongResponse;
import com.ms.app.dto.PlaylistDTO;
import com.ms.app.model.Playlist;
import com.ms.app.model.PlaylistSong;
import com.ms.app.model.User;
import com.ms.app.repository.PlaylistRepository;
import com.ms.app.repository.PlaylistSongRepository;
import com.ms.app.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository repo;
    private final CloudinaryService cloudinaryService;
    private final UsersRepository userRepository;
    private final PlaylistSongRepository playlistSongRepo;
    public PlaylistServiceImpl(PlaylistRepository repo, CloudinaryService cloudinaryService, UsersRepository userRepository, PlaylistSongRepository playlistSongRepo) {
        this.repo = repo;
        this.cloudinaryService = cloudinaryService;
        this.userRepository = userRepository;
        this.playlistSongRepo = playlistSongRepo;
    }
    @Override
    public Playlist create(PlaylistDTO playlist, MultipartFile coverFile, MultipartFile wallpaperFile) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((User) principal).getUsername();
        User user = userRepository.findByUsername(username).orElseThrow();
        Playlist play = new Playlist();
        play.setUser(user);
        play.setName(playlist.getName());
        play.setCreatedAt(LocalDateTime.now());
        play.setDescription(playlist.getDescription());
        try {
            Map coverData = cloudinaryService.uploadImage(coverFile);
            play.setCoverUrl((String) coverData.get("url"));
        } catch (IOException e) {
            System.err.println("Error uploading cover file to Cloudinary: " + e.getMessage());
        }
        try {
            if (wallpaperFile != null) {
                Map wallpaperData = cloudinaryService.uploadWallpaper(wallpaperFile);
                play.setWallpaperUrl((String) wallpaperData.get("url"));
            }
        } catch (IOException e) {
            System.err.println("Error uploading wallpaper file to Cloudinary: " + e.getMessage());
        }
        play.setIsPublic(playlist.getIsPublic());
        return repo.save(play);
    }
    @Override
    public Playlist update(Long id, PlaylistDTO playlist, MultipartFile coverFile, MultipartFile wallpaperFile) {
        Playlist old = repo.findById(id).orElseThrow();
        old.setName(playlist.getName());
        old.setDescription(playlist.getDescription());
        try {
            Map coverData = cloudinaryService.uploadImage(coverFile);
            old.setCoverUrl((String) coverData.get("url"));
        } catch (IOException e) {
            System.err.println("Error uploading cover file to Cloudinary: " + e.getMessage());
        }
        try {
            Map wallpaperData = cloudinaryService.uploadWallpaper(wallpaperFile);
            old.setWallpaperUrl((String) wallpaperData.get("url"));
        } catch (IOException e) {
            System.err.println("Error uploading wallpaper file to Cloudinary: " + e.getMessage());
        }
        old.setIsPublic(playlist.getIsPublic());
        return repo.save(old);
    }
    @Override
    public void delete(Long id) throws IOException {
        Playlist play = repo.findById(id).orElseThrow();
        String coverUrl = play.getCoverUrl();
        String wallpaperUrl = play.getWallpaperUrl();
        cloudinaryService.deleteImage(cloudinaryService.extractPublicID(coverUrl));
        if (wallpaperUrl != null) {
            cloudinaryService.deleteWallpaper(cloudinaryService.extractPublicID(wallpaperUrl));
        }
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

    @Override
    public List<Playlist> findByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User current_User;
        if(principal instanceof User) {
            current_User = (User) principal;
        } else {
            String email = authentication.getName();
            current_User = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found: " + email));
        }
        return repo.findByUserId(current_User.getId());
    }
    @Override
    public List<PlayListSongResponse> getSongsInPlaylist(Long playlistId) {
        // 1. Lấy List<PlaylistSong> từ DB (Dạng Entity + Proxy)
        List<PlaylistSong> entities = playlistSongRepo.findByPlaylistId(playlistId);
        return entities.stream()
                .map(PlayListSongResponse::fromEntity) // Gọi hàm mapper static ta vừa viết
                .collect(Collectors.toList());
    }
}
