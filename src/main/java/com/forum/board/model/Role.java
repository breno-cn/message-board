package com.forum.board.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    public static final int ROLE_NAME_MAX_LENGTH = 32;

    @Id
    @Column(name = "role_name", unique = true, nullable = false, length = ROLE_NAME_MAX_LENGTH)
    private String roleName;

    @ManyToMany
    private List<User> users;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

}
