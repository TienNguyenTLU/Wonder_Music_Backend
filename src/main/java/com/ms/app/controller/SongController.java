package com.ms.app.controller;

import com.ms.app.dto.SongDTO;
import com.ms.app.model.Song;
import com.ms.app.service.SongService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    private final SongService service;
    public SongController(SongService service) {
        this.service = service;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Song> create(
            @RequestPart("song") SongDTO song,
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        try {
            Song newSong = service.create(song, file, image);
            return ResponseEntity.ok(newSong);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
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
    public List<Song> all() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Song get(@PathVariable Long id) {
        return service.findById(id);
    }
    @GetMapping("/user_id/{userId}")
    public List<Song> getByUserId(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }
    @GetMapping("/user/{username}")
    public List<Song> getByUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }
    @GetMapping("/genre/{id}")
    public List<Song> getByGenreId(@PathVariable Long id)
    {
        return service.findByGenreId(id);
    }
    @GetMapping("/artist/{id}")
    public List<Song> getByArtistId(@PathVariable Long id) {
        return service.findByArtistId(id);
    }
}