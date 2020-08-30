package com.forum.board.repository;

import com.forum.board.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "post", path = "path")
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    public Slice<Post> findByBoardId(@Param("id") Long id, Pageable pageable);

}
