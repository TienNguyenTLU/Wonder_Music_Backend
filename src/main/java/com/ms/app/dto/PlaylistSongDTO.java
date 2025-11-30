package com.ms.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistSongDTO {
    private Long id;
    private Long playlistId;
    private Long songId;
}
