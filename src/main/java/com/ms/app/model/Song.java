package com.ms.app.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "songs")
@Getter
@Setter
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String fileUrl;
    private String coverUrl;
    private Double duration;
    private String status;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
