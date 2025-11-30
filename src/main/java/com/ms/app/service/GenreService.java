package com.ms.app.service;

import com.ms.app.dto.GenreDTO;
import com.ms.app.model.Genre;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GenreService {
    Genre create(GenreDTO genre, MultipartFile image) throws IOException;
    Genre update(Long id, GenreDTO genre);
    void delete(Long id);
    List<Genre> findAll();
    Genre findById(Long id);
}