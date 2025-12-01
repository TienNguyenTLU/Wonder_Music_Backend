package com.ms.app.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "playlist_songs")
@Getter
@Setter
public class PlaylistSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime addedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Playlist playlist;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Song song;
}

