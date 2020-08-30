package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    private static final int MAX_COMMENT_LENGTH = 256;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, length = MAX_COMMENT_LENGTH)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    public Comment() {}

    public Comment(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) &&
                content.equals(comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

}
