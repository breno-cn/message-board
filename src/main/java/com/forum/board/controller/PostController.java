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
@Slf4j
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.findPostById(id));
    }

    @GetMapping(value = "/boards/{boardName}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPostsByBoardName(
            @PathVariable(name = "boardName") String boardName,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        return ResponseEntity.ok(postService.findPostsByBoardName(boardName, page));

    }

    // TODO: use board name instead of id
//    TODO: endpoint in authentication
    @PostMapping(
            value = "/boards/{boardName}/posts/new",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePostOnBoard(
            @PathVariable(name = "boardName") String boardName,
            @RequestBody Post post,
            Authentication authentication) throws RuntimeException {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.savePost(boardName, post, authentication));

    }

}
