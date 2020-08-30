package com.forum.board.controller;

import com.forum.board.exception.PostNotFoundException;
import com.forum.board.model.Post;
import com.forum.board.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable(name = "id") Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return ResponseEntity.ok(post);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<Slice<Post>> getPostsByBoardId(
            @PathVariable(name = "boardId") Long boardId,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 5);
        Slice<Post> posts = postRepository.findByBoardId(boardId, pageable);

        return ResponseEntity.ok(posts);
    }

}
