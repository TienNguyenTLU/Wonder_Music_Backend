package com.ms.app.service;
import com.ms.app.dto.GenreDTO;
import com.ms.app.model.Genre;
import com.ms.app.repository.GenreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repo;
    public GenreServiceImpl(GenreRepository repo) {
        this.repo = repo;
    }
    @Override
    public Genre create(GenreDTO genre) {
        Genre newGenre = new Genre();
        newGenre.setName(genre.getName());
        newGenre.setDescription(genre.getDescription());
        return repo.save(newGenre);
    }

    @Override
    public Genre update(Long id, GenreDTO genre) {
        Genre old = repo.findById(id).orElseThrow();
        old.setName(genre.getName());
        old.setDescription(genre.getDescription());
        return repo.save(old);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Genre> findAll() {
        return repo.findAll();
    }

    @Override
    public Genre findById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
