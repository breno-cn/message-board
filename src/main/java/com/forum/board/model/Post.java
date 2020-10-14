package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @JsonProperty(value = "title")
    private String title;

    @Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "users_id")
//    @JsonManagedReference
    @JsonIgnore
    private UserModel userModel;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
