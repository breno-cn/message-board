package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "board")
public class Board {
    public static final int NAME_LENGTH = 16;
    public static final int MAX_DESCRIPTION_LENGTH = 64;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = Board.NAME_LENGTH)
    private String name;

    @Column(name = "description", nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;

    @OneToMany(mappedBy = "board")
    @JsonIgnore
    private List<Post> posts;

    public Board() {}

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return id.equals(board.id) &&
                name.equals(board.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
