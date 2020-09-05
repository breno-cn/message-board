package com.forum.board.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.assembler.CommentAssembler;
import com.forum.board.exception.CommentNotFoundException;
import com.forum.board.exception.PostNotFoundException;
import com.forum.board.model.Comment;
import com.forum.board.model.Post;
import com.forum.board.model.UserModel;
import com.forum.board.repository.CommentRepository;
import com.forum.board.repository.PostRepository;
import com.forum.board.repository.UserRepository;
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
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentAssembler commentAssembler;

    public CommentController(CommentRepository commentRepository,
                            PostRepository postRepository,
                            UserRepository userRepository,
                            CommentAssembler commentAssembler) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentAssembler = commentAssembler;
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCommentById(@PathVariable(name = "id") Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        return ResponseEntity.ok(commentAssembler.toModel(comment));
    }

    @GetMapping(value = "/post/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        List<EntityModel<Comment>> comments = commentRepository.findAllByPostId(postId)
                .stream()
                .map(commentAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                comments,
                linkTo(methodOn(CommentController.class).getCommentsByPostId(postId)).withSelfRel()
        ));
    }

    @PostMapping(
            value = "/post/{postId}/new",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newCommentByPostId(
            @PathVariable(name = "postId") Long postId,
            @RequestBody Comment comment,
            Authentication authentication
    ) throws RuntimeException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        String username = authentication.getName();
        UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        comment.setPost(post);
        comment.setUser(userModel);
        Comment saved = commentRepository.save(comment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentAssembler.toModel(saved));
    }

}
