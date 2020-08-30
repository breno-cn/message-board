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

//    @Query(nativeQuery = true,
//            value = "SELECT * FROM post WHERE board_id = ?1",
//            countQuery = "SELECT count(*) FROM POST WHERE board_id = ?1"
//    )
    Slice<Post> findAllByBoardId(@Param("id") Long id, Pageable pageable);

}
