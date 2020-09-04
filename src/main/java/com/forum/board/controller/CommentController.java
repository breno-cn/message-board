package com.forum.board.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.assembler.CommentAssembler;
import com.forum.board.exception.CommentNotFoundException;
import com.forum.board.model.Comment;
import com.forum.board.repository.CommentRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepository;

    private final CommentAssembler commentAssembler;

    public CommentController(CommentRepository commentRepository, CommentAssembler commentAssembler) {
        this.commentRepository = commentRepository;
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
        List<EntityModel<Comment>> comments = commentRepository.findAll()
                .stream()
                .map(commentAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                comments,
                linkTo(methodOn(CommentController.class).getCommentsByPostId(postId)).withSelfRel()
        ));
    }

}
