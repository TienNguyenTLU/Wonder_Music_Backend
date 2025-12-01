package com.ms.app.controller;

import com.ms.app.dto.PlaylistDTO;
import com.ms.app.model.Playlist;
import com.ms.app.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Playlist> create(@RequestPart("playlist") PlaylistDTO playlist,
                            @RequestPart("cover") MultipartFile coverFile,
                            @RequestPart(value = "wallpaper", required = false) MultipartFile wallpaperFile)  {
        return ResponseEntity.ok(service.create(playlist, coverFile, wallpaperFile));
    }
    @PutMapping("/{id}")
    public Playlist update(@PathVariable Long id, @RequestBody PlaylistDTO playlist, MultipartFile coverFile, MultipartFile wallpaperFile) {
        return service.update(id, playlist, coverFile, wallpaperFile);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<Playlist> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Playlist get(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Playlist>> getByUser() {
        return ResponseEntity.ok(service.findByUserId());
    }
}
