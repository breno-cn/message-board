package com.forum.board.repository;

import com.forum.board.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByUserModelUsernameAndId(String username, Long id);

    Slice<Post> findAllByBoardId(@Param("id") Long id, Pageable pageable);

    Slice<Post> findAllByBoardName(String boardName, Pageable pageable);

}
