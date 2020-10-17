package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
public class Post implements Serializable {

    public static final int MAX_TITLE_LENGTH = 32;
    public static final int MAX_CONTENT_LENGTH = 256;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false, length = MAX_TITLE_LENGTH)
    @NotBlank(message = "must provide a title")
    @JsonProperty(value = "title")
    private String title;

    @Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
    @NotBlank(message = "must provide content of the post")
    @JsonProperty(value = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Comment> comments;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private UserModel userModel;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
