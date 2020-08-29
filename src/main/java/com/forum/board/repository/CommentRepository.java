package com.forum.board.repository;

import com.forum.board.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "comment", path = "comment")
@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {}
