package com.ms.app.dto;

import com.ms.app.model.Artist;
import com.ms.app.model.Genre;
import com.ms.app.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDTO {
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(min = 4, max = 150, message = "Title must be between 4 and 150 characters")
    private String title;
    @Size(min = 4, max = 500, message = "Description must be between 4 and 500 characters")
    private String description;
    private String fileUrl;
    private String coverUrl;
    private Double duration;
    private Long userId;
    @NotBlank(message = "Artist is required")
    private Long artistId;
    @NotBlank(message = "Genre is required")
    private Long genreId;
}
