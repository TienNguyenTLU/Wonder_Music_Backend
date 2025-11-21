package com.ms.app.service;
import java.util.List;

import com.ms.app.dto.SongDTO;
import com.ms.app.model.Song;
import com.ms.app.repository.SongRepository;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository repo;
    public SongServiceImpl(SongRepository repo) {
        this.repo = repo;
    }
    @Override
    public Song create(SongDTO song) {
        Song s = new Song();
        s.setTitle(song.getTitle());
        s.setDescription(song.getDescription());
        s.setCoverUrl(song.getCoverUrl());
        s.setFileUrl(song.getFileUrl());
        s.setDuration(song.getDuration());
        s.setArtist(song.getArtist());
        s.setGenre(song.getGenre());
        return repo.save(s);
    }

    @Override
    public Song update(Long id, SongDTO song) {
        Song old = repo.findById(id).orElseThrow();
        old.setTitle(song.getTitle());
        old.setDescription(song.getDescription());
        old.setCoverUrl(song.getCoverUrl());
        old.setFileUrl(song.getFileUrl());
        old.setDuration(song.getDuration());
        old.setArtist(song.getArtist());
        old.setGenre(song.getGenre());
        return repo.save(old);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Song> findAll() {
        return repo.findAll();
    }

    @Override
    public Song findById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
