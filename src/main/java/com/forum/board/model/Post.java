package com.forum.board.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {

    public static final int TITLE_LENGTH = 32;
    public static final int CONTENT_LENGTH = 256;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false, length = TITLE_LENGTH)
    private String title;

    @Column(name = "content", nullable = false, length = CONTENT_LENGTH)
    private String content;

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