package com.ms.app.repository;
import com.ms.app.model.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {
    List<PlaylistSong> findByPlaylistId(Long playlistId);
    List<PlaylistSong> findBySongId(Long songId);
    void deleteByPlaylistIdAndSongId(Long playlistId, Long songId);
}
