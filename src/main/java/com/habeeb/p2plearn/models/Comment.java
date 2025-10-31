package com.habeeb.p2plearn.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "article_id",referencedColumnName = "id")
    private Article article;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime commentedAt = LocalDateTime.now();
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void updateCommentTime(){
        this.updatedAt = LocalDateTime.now();
    }
}
