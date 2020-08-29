package com.forum.board.repository;

import com.forum.board.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "post", path = "path")
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {}
