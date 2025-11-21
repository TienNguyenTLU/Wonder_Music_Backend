package com.ms.app.service;

import com.ms.app.dto.ArtistDTO;
import com.ms.app.model.Artist;
import com.ms.app.repository.ArtistRepository;
import com.ms.app.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepo;
    public ArtistServiceImpl(ArtistRepository artistRepo) {
        this.artistRepo = artistRepo;
    }
    @Override
    public Artist create(ArtistDTO artist) {
        Artist newArtist = new Artist();
        newArtist.setName(artist.getName());
        newArtist.setBio(artist.getBio());
        newArtist.setAvatarUrl(artist.getAvatarUrl());
        return artistRepo.save(newArtist);
    }
    @Override
    public Artist update(Long id, ArtistDTO artist) {
        Artist old = artistRepo.findById(id).orElseThrow();
        old.setName(artist.getName());
        old.setBio(artist.getBio());
        old.setAvatarUrl(artist.getAvatarUrl());
        return artistRepo.save(old);
    }
    @Override
    public void delete(Long id) {
        artistRepo.deleteById(id);
    }
    @Override
    public List<Artist> findAll() {
        return artistRepo.findAll();
    }
    @Override
    public Artist findById(Long id) {
        return artistRepo.findById(id).orElse(null);
    }
}
