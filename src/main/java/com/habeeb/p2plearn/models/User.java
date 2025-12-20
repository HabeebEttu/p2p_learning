package com.habeeb.p2plearn.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    @Column(unique = true ,nullable = false)
    private String username;
    @NotNull
    @Column(unique = true,nullable = false)
    private String email;
    @NotNull
    @JsonIgnore
    private String hashPassword;
    private boolean isAdmin;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @JsonManagedReference
    private Profile profile;

}
