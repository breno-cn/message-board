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

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
public class Comment implements Serializable {

    private static final int MAX_COMMENT_LENGTH = 256;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false, length = MAX_COMMENT_LENGTH)
    @NotBlank(message = "must provide content of the comment")
    @JsonProperty(value = "content", access = JsonProperty.Access.READ_WRITE)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @ManyToOne(optional = false)
    @JoinColumn(name = "users_id")
    @JsonProperty(value = "user", access = JsonProperty.Access.READ_ONLY)
    private UserModel userModel;

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

    public Comment(String content) {
        this.content = content;
    }

}
