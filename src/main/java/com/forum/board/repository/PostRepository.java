package com.forum.board.repository;

import com.forum.board.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

//    @Query(
//            value = "SELECT p FROM Post p WHERE p.board.id = ?1 ORDER BY p.id",
//            countQuery = "SELECT count(p) FROM Post p")
    Slice<Post> findByBoardId(@Param("id") Long id, Pageable pageable);

}
