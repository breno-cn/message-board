package com.forum.board.controller;

import com.forum.board.exception.CommentNotFoundException;
import com.forum.board.model.Comment;
import com.forum.board.repository.CommentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Comment> getPostById(@PathVariable(name = "id") Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        return ResponseEntity
                .ok(comment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(commentRepository.findAllByPostId(postId));
    }

}