package com.forum.board.controller;

import com.forum.board.model.Post;
import com.forum.board.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.findPostById(id));
    }

    @GetMapping(value = "/board/{boardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPostsByBoardId(@PathVariable(name = "boardId") Long boardId,
                                               @RequestParam(name = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(postService.findPostsByBoardId(boardId, page));
    }

    @PostMapping(value = "/board/{boardId}/new",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Post>> newPostOnBoardId(
            @PathVariable(name = "boardId") Long boardId,
            @RequestBody Post post,
            Authentication authentication) throws RuntimeException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.savePost(boardId, post, authentication));
    }

}
