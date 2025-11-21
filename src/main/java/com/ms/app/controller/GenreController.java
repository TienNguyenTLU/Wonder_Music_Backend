package com.ms.app.controller;
import com.ms.app.dto.GenreDTO;
import com.ms.app.model.Artist;
import com.ms.app.model.Genre;
import com.ms.app.service.ArtistService;
import com.ms.app.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @PostMapping
    public Genre create(@RequestBody GenreDTO genre) {
        return service.create(genre);
    }

    @PutMapping("/{id}")
    public Genre update(@PathVariable Long id, @RequestBody GenreDTO genre) {
        return service.update(id, genre);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<Genre> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable Long id) {
        return service.findById(id);
    }
}

