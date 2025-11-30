package com.ms.app.controller;

import com.ms.app.dto.PlaylistSongDTO;
import com.ms.app.model.PlaylistSong;
import com.ms.app.service.PlaylistSongService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist-songs")
public class PlaylistSongController {

    private final PlaylistSongService service;

    public PlaylistSongController(PlaylistSongService service) {
        this.service = service;
    }

    @PostMapping
    public PlaylistSong add(@RequestBody PlaylistSongDTO ps) {
        return service.addSong(ps);
    }

    @DeleteMapping("/{playlistId}/{songId}")
    public void remove(@PathVariable Long playlistId, @PathVariable Long songId) {
        service.removeSong(playlistId, songId);
    }

    @GetMapping("/playlist/{id}")
    public List<PlaylistSong> getByPlaylist(@PathVariable Long id) {
        return service.findByPlaylist(id);
    }
}
