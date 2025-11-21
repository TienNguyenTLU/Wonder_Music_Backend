package com.ms.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
public class ArtistDTO {

    private Long id;
    @NotEmpty(message = "Name is required")
    @Max(value = 20, message = "Name must be less than 100 characters")
    private String name;
    private String bio;
    private String avatarUrl;
}
