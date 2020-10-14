package com.forum.board.controller;

import com.forum.board.model.Comment;
import com.forum.board.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCommentById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(commentService.findCommentById(id));
    }

    @GetMapping(value = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }

    // TODO: same as post controller
    @PostMapping(value = "/posts/{postId}/comments",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newCommentByPostId(@PathVariable(name = "postId") Long postId,
                                                @RequestBody Comment comment,
                                                Authentication authentication) throws RuntimeException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.saveComment(postId, comment, authentication));
    }

}
