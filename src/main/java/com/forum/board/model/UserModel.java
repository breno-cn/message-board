package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Slf4j
public class UserModel implements Serializable, UserDetails {

    private static final int MAX_USERNAME_LENGTH = 16;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    @JsonProperty("id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = MAX_USERNAME_LENGTH)
    @JsonProperty("username")
    private String username;

    @Column(name = "password", nullable = false, unique = true)
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "account_locked", nullable = false)
    @JsonProperty(value = "account_locked", access = JsonProperty.Access.READ_ONLY)
    private Boolean accountLocked;

    @Column(name = "email", nullable = false, unique = true)
    @JsonProperty(value = "email", access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @OneToMany(mappedBy = "userModel")
    @JsonIgnore
    List<Post> posts;

    @OneToMany(mappedBy = "userModel")
    @JsonIgnore
    List<Comment> comments;

//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "role_name"))
    List<Role> roles = new ArrayList<>();

    public UserModel() {}

    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

        this.accountLocked = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//ESTAVA NULL, voltar se estiver errado
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        log.info("USER MODEL AUTHORITIES " + roles);
        return roles;
    }

    @Override
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' + roles.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return id.equals(userModel.id) &&
                username.equals(userModel.username) &&
                password.equals(userModel.password) &&
                email.equals(userModel.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email);
    }

}
