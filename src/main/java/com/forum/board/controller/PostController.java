package com.forum.board.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.assembler.PostAssembler;
import com.forum.board.exception.PostNotFoundException;
import com.forum.board.model.Post;
import com.forum.board.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {

    public static final int MAX_POSTS_BY_PAGE = 5;

    private final PostRepository postRepository;

    private final PostAssembler postAssembler;

    public PostController(PostRepository postRepository, PostAssembler postAssembler) {
        this.postRepository = postRepository;
        this.postAssembler = postAssembler;
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<EntityModel<Post>> getPostById(@PathVariable(name = "id") Long id) {
    public ResponseEntity<?>  getPostById(@PathVariable(name = "id") Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return ResponseEntity.ok(postAssembler.toModel(post));
    }

    @GetMapping(value = "/board/{boardId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<CollectionModel<EntityModel<Post>>> getPostsByBoardId(
    public ResponseEntity<?> getPostsByBoardId(
            @PathVariable(name = "boardId") Long boardId,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, MAX_POSTS_BY_PAGE);
        List<EntityModel<Post>> posts = postRepository.findAllByBoardId(boardId, pageable)
                .stream()
                .map(postAssembler::toModel)
                .collect(Collectors.toList());

        if (posts.isEmpty()) {
            // TODO: EMPTY PAGE OR BOARD NOT FOUND
            throw new PostNotFoundException(boardId);
        }

        return ResponseEntity.ok(CollectionModel.of(
                posts,
                linkTo(methodOn(BoardController.class).getAllBoards()).withSelfRel()
        ));
    }

}
