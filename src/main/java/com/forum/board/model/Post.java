package com.forum.board.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "title", nullable = false, length = MAX_TITLE_LENGTH)
    @NotBlank(message = "must provide a title")
    @JsonProperty(value = "title", access = JsonProperty.Access.READ_WRITE)
    private String title;

    @Column(name = "content", nullable = false, length = MAX_CONTENT_LENGTH)
    @NotBlank(message = "must provide content of the post")
    @JsonProperty(value = "content", access = JsonProperty.Access.READ_WRITE)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments;

    @CreationTimestamp
//    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = true, updatable = false)
    @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @UpdateTimestamp
//    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    @JsonProperty(value = "updated_at", access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    @JsonProperty(value = "user", access = JsonProperty.Access.READ_ONLY)
    private UserModel userModel;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
