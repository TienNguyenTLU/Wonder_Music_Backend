package com.ms.app.service;
import com.ms.app.dto.PlaylistSongDTO;
import com.ms.app.model.Playlist;
import com.ms.app.model.PlaylistSong;
import com.ms.app.model.Song;
import com.ms.app.repository.PlaylistRepository;
import com.ms.app.repository.PlaylistSongRepository;
import com.ms.app.repository.SongRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class PlaylistSongServiceImpl implements PlaylistSongService {
    private final PlaylistSongRepository repo;
    private final PlaylistRepository playlistRepo; // <--- Cần thêm cái này
    private final SongRepository songRepo;
    public PlaylistSongServiceImpl(PlaylistSongRepository repo,
                                   PlaylistRepository playlistRepo,
                                   SongRepository songRepo) {
        this.repo = repo;
        this.playlistRepo = playlistRepo;
        this.songRepo = songRepo;
    }

    @Override
    public PlaylistSong addSong(PlaylistSongDTO ps) {
        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setAddedAt(LocalDateTime.now());
        Playlist playlist = playlistRepo.findById(ps.getPlaylistId()).orElse(null);
        playlistSong.setPlaylist(playlist);
        Song song = songRepo.findById(ps.getSongId()).orElse(null);
        playlistSong.setSong(song);
        return repo.save(playlistSong);
    }

    @Override
    public void removeSong(Long playlistId, Long songId) {
        repo.deleteByPlaylistIdAndSongId(playlistId, songId);
    }
    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    @Override
    public List<PlaylistSong> findByPlaylist(Long id) {
        return repo.findByPlaylistId(id);
    }
}
