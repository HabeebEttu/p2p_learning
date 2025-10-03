package com.habeeb.p2plearn.models;

import com.habeeb.p2plearn.models.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_rank")
    private Rank rank;

    private String bio;

    private String avatarUrl = "/static/default.png";

    @ManyToMany
    @JoinTable(
            name = "profile_friends",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<Profile> friends = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<FriendRequest> friendRequests = new HashSet<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL)
    private Set<Follow> followers = new HashSet<>();

    private int xp = 0;


    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private Set<Follow> following = new HashSet<>();

}
