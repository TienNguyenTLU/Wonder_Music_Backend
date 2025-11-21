package com.ms.app.service;

import com.ms.app.dto.GenreDTO;
import com.ms.app.model.Genre;
import java.util.List;

public interface GenreService {
    Genre create(GenreDTO genre);
    Genre update(Long id, GenreDTO genre);
    void delete(Long id);
    List<Genre> findAll();
    Genre findById(Long id);
}