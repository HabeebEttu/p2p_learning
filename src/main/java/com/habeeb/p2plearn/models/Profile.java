package com.habeeb.p2plearn.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;
    @Column(name="first_name")
    private String firstName = "";
    @Column(name="last_name")
    private String lastName = "";
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
    private List<FriendRequest> friendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL)
    private List<Follow> followers = new ArrayList<>();

    private int xp = 0;


    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> following = new ArrayList<>();

}
