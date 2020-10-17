package com.forum.board.controller;

import com.forum.board.model.Post;
import com.forum.board.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public ResponseEntity<?> getPostsByBoardName(@PathVariable(name = "boardName") String boardName,
                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "5") int size) {

        return ResponseEntity.ok(postService.findPostsByBoardName(boardName, page, size));
    }

    @PostMapping(value = "/boards/{boardName}/posts", produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePostOnBoard(@PathVariable(name = "boardName") String boardName,
                                             @Valid @RequestBody Post post,
                                             Authentication authentication) throws RuntimeException {

        log.info("DEBUG POST CONTROLLER: " + authentication.getPrincipal());
        log.info(authentication.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.savePost(boardName, post, authentication));

    }

    @PutMapping(value = "/posts/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editPostById(@PathVariable(name = "id") Long id, @RequestBody Post post,
                                          Authentication authentication) {
//        return ResponseEntity
//                .accepted()
//                .body(postService.editPost(id, post, authentication));
        return ResponseEntity
                .status(postService.editPost(id, post, authentication))
                .build();
    }

    @DeleteMapping(value = "/posts/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable(name = "id") Long id, Authentication authentication) {
        postService.deletePost(id, authentication);

        return ResponseEntity
                .noContent()
                .build();
//                .status(postService.deletePost(id, authentication))
//                .build();
    }

}
