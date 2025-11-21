package com.ms.app.service;

import com.ms.app.dto.ArtistDTO;
import com.ms.app.model.Artist;
import java.util.List;

public interface ArtistService {
    Artist create(ArtistDTO artist);
    Artist update(Long id, ArtistDTO artist);
    void delete(Long id);
    List<Artist> findAll();
    Artist findById(Long id);
}
