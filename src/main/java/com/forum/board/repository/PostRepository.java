package com.forum.board.repository;

import com.forum.board.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "post", path = "path")
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {}
