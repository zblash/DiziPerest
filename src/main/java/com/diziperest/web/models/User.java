package com.diziperest.web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 25)
    private String userName;

    @Email
    @NotBlank
    @JsonIgnore
    private String email;

    @NotBlank
    @Size(min = 3, max = 25)
    @JsonIgnore
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 25)
    @JsonIgnore
    private String lastName;

    @NotBlank
    @Size(min = 5, max = 90)
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String resetToken;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

    @JsonIgnore
    @ManyToMany(mappedBy = "users",fetch = FetchType.LAZY)
    private List<Hashtag> hashtags;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}