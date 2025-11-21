package com.ms.app.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistDTO {
    private Long id;
    private String name;
    private String description;
    private String coverUrl;
    private String wallpaperUrl;
    private Boolean isPublic;
    private Long userId;
}

