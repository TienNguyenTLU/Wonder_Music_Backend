package com.ms.app.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "follow")
public class Follow {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @JoinColumn(name = "user_id")
        @ManyToOne
        private User user;
        @JoinColumn(name = "artist_id")
        @ManyToOne
        private Artist artist;
}
