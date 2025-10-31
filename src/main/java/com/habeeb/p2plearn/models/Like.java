package com.habeeb.p2plearn.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


    @Entity
    @Table(name = "likes")
    public class Like {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(nullable = false)
        private Article article;

        private LocalDateTime likedAt = LocalDateTime.now();


}
