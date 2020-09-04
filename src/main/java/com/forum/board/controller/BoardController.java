package com.forum.board.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.assembler.BoardAssembler;
import com.forum.board.exception.PostNotFoundException;
import com.forum.board.model.Board;
import com.forum.board.repository.BoardRepository;
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
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;

    private final BoardAssembler boardAssembler;

    public BoardController(BoardRepository boardRepository, BoardAssembler boardAssembler) {
        this.boardRepository = boardRepository;
        this.boardAssembler = boardAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Board>>> getAllBoards() {
        List<EntityModel<Board>> boards = boardRepository.findAll()
                .stream()
                .map(boardAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                boards,
                linkTo(methodOn(BoardController.class).getAllBoards()).withSelfRel()
        ));
    }


    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Board>> getBoardById(@PathVariable Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id)); // PLACEHOLDER

        return ResponseEntity.ok(boardAssembler.toModel(board));
    }

}
