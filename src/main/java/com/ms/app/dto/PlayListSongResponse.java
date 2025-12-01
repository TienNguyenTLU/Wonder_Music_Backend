package com.ms.app.dto;

import com.ms.app.model.PlaylistSong;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PlayListSongResponse {
    // 1. Thông tin từ bảng trung gian
    private Long id;            // ID của PlaylistSong
    private LocalDateTime addedAt;

    // 2. Thông tin Playlist
    private Long playlistId;
    private String playlistTitle;       // Map từ playlist.name
    private String playlistCoverUrl;    // Ảnh bìa playlist
    private String playlistWallpaperUrl;// Ảnh nền playlist

    // 3. Thông tin Song
    private Long songId;
    private String songName;            // Map từ song.title
    private String fileUrl;             // Link nhạc
    private String songCoverUrl;        // Ảnh bìa bài hát
    private Double duration;
    private LocalDateTime songCreatedAt;

    // 4. Thông tin liên quan
    private String artistName;
    private String genreName;

    // --- HELPER METHOD: Chuyển đổi từ Entity sang DTO ---
    // Viết hàm này ở đây để code Service gọn gàng hơn
    public static PlayListSongResponse fromEntity(PlaylistSong entity) {
        PlayListSongResponse dto = new PlayListSongResponse();

        // Map bảng trung gian
        dto.setId(entity.getId());
        dto.setAddedAt(entity.getAddedAt());

        // Map Playlist (Check null safety nếu cần)
        if (entity.getPlaylist() != null) {
            dto.setPlaylistId(entity.getPlaylist().getId());
            dto.setPlaylistTitle(entity.getPlaylist().getName()); // Bạn muốn lấy title (name)
            dto.setPlaylistCoverUrl(entity.getPlaylist().getCoverUrl());
            dto.setPlaylistWallpaperUrl(entity.getPlaylist().getWallpaperUrl());
        }

        // Map Song & Artist & Genre
        if (entity.getSong() != null) {
            dto.setSongId(entity.getSong().getId());
            dto.setSongName(entity.getSong().getTitle()); // Bạn muốn lấy name (title)
            dto.setFileUrl(entity.getSong().getFileUrl());
            dto.setSongCoverUrl(entity.getSong().getCoverUrl());
            dto.setDuration(entity.getSong().getDuration());
            dto.setSongCreatedAt(entity.getSong().getCreatedAt());

            if (entity.getSong().getArtist() != null) {
                dto.setArtistName(entity.getSong().getArtist().getName());
            }

            if (entity.getSong().getGenre() != null) {
                dto.setGenreName(entity.getSong().getGenre().getName());
            }
        }
        return dto;
    }
}