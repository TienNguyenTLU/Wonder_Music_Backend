package com.ms.app.repository;

import com.ms.app.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByUserId(Long userId);

    List<Song> findByArtistId(Long artistId);

    List<Song> findByGenreId(Long genreId);

    List<Song> findByStatus(String status);
    List<Song> findByUserUsername(String username);
}
