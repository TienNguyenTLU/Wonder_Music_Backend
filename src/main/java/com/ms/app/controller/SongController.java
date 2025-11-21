package com.ms.app.controller;

import com.ms.app.dto.SongDTO;
import com.ms.app.model.Song;
import com.ms.app.service.SongService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService service;

    public SongController(SongService service) {
        this.service = service;
    }

    @PostMapping
    public Song create(@RequestBody SongDTO song) {
        return service.create(song);
    }

    @PutMapping("/{id}")
    public Song update(@PathVariable Long id, @RequestBody SongDTO song) {
        return service.update(id, song);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<Song> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Song get(@PathVariable Long id) {
        return service.findById(id);
    }
}
