package com.forum.board.repository;

import com.forum.board.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "comment", path = "comment")
public interface CommentRepository extends JpaRepository<Comment, Long> {}
