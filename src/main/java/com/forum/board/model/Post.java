package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post implements Serializable {

    public static final int MAX_TITLE_LENGTH = 32;
    public static final int MAX_CONTENT_LENGTH = 256;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false, length = MAX_TITLE_LENGTH)
    private String title;

    @Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    @JsonManagedReference
    private User user;

    public Post() {}

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id) &&
                title.equals(post.title) &&
                content.equals(post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }

}
