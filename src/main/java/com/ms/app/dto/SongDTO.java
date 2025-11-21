package com.ms.app.dto;

import com.ms.app.model.Artist;
import com.ms.app.model.Genre;
import com.ms.app.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDTO {
    private Long id;
    private String title;
    private String description;
    private String fileUrl;
    private String coverUrl;
    private Integer duration;
    private User user;
    private Artist artist;
    private Genre genre;
}
