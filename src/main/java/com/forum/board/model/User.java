package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final int MAX_USERNAME_LENGTH = 16;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = MAX_USERNAME_LENGTH)
    private String username;

    @Column(name = "password", nullable = false, unique = true)
    @JsonIgnore
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @JsonIgnore
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Post> posts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Comment> comments;

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email);
    }

}
