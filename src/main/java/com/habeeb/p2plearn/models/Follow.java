package com.habeeb.p2plearn.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Follow {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Profile follower;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private Profile followed;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime followedAt;

}
