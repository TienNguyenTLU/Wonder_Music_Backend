package com.ms.app.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistDTO {
    private Long id;
    @NotBlank(message = "Name is required")
    @Size(min = 4, max = 150, message = "Name must be between 4 and 150 characters")
    private String name;
    @Size(min = 4, max = 500, message = "Description must be between 4 and 500 characters")
    private String description;
    private String coverUrl;
    private String wallpaperUrl;
    private Boolean isPublic;
    private Long userId;
}

