package com.forum.board.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.assembler.PostAssembler;
import com.forum.board.exception.PostNotFoundException;
import com.forum.board.model.Board;
import com.forum.board.model.Post;
import com.forum.board.model.UserModel;
import com.forum.board.repository.BoardRepository;
import com.forum.board.repository.PostRepository;
import com.forum.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

    public static final int MAX_POSTS_BY_PAGE = 5;

    private final PostRepository postRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final PostAssembler postAssembler;

    public PostController(PostRepository postRepository,
                          BoardRepository boardRepository,
                          UserRepository userRepository,
                          PostAssembler postAssembler) {
        this.postRepository = postRepository;
        this.postAssembler = postAssembler;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  getPostById(@PathVariable(name = "id") Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return ResponseEntity.ok(postAssembler.toModel(post));
    }

    @GetMapping(value = "/board/{boardId}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/board/{boardId}/new",
                 produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Post>> newPostOnBoardId(
            @PathVariable(name = "boardId") Long boardId,
            @RequestBody Post post,
            Authentication authentication) throws RuntimeException {

        // TODO: BoardNotFoundException
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new PostNotFoundException(boardId));

        String username = authentication.getName();
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        post.setBoard(board);
        post.setUser(user);
        Post saved = postRepository.save(post);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postAssembler.toModel(saved));
    }

}
