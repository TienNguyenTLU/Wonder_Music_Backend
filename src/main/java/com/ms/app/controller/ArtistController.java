package com.ms.app.controller;

import com.ms.app.dto.ArtistDTO;
import com.ms.app.model.Artist;
import com.ms.app.service.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    private final ArtistService service;
    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @PostMapping
    public Artist create(@RequestBody ArtistDTO artist) {
        return service.create(artist);
    }

    @PutMapping("/{id}")
    public Artist update(@PathVariable Long id, @RequestBody ArtistDTO artist) {
        return service.update(id, artist);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<Artist> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Artist get(@PathVariable Long id) {
        return service.findById(id);
    }
}
