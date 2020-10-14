package com.forum.board.repository;

import com.forum.board.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByUserModelIdAndId(Long userModelId, Long id);

    List<Comment> findAllByPostId(@Param("postId") Long postId);

}
