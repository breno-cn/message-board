package com.forum.board.repository;

import com.forum.board.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "post", path = "path")
public interface PostRepository extends JpaRepository<Post, Long> {}
