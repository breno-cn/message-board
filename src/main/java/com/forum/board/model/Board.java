package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "board")
@Data
@NoArgsConstructor
public class Board implements Serializable {
    public static final int MAX_NAME_LENGTH = 16;
    public static final int MAX_DESCRIPTION_LENGTH = 64;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = MAX_NAME_LENGTH)
    private String name;

    @Column(name = "description", nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "board")
    @JsonIgnore
    private List<Post> posts;

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
