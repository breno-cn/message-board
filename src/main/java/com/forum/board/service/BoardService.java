package com.forum.board.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.assembler.BoardAssembler;
import com.forum.board.controller.BoardController;
import com.forum.board.exception.PostNotFoundException;
import com.forum.board.model.Board;
import com.forum.board.repository.BoardRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardAssembler boardAssembler;

    public BoardService(BoardRepository boardRepository, BoardAssembler boardAssembler) {
        this.boardRepository = boardRepository;
        this.boardAssembler = boardAssembler;
    }

    public EntityModel<Board> findBoardById(Long id) {
        // TODO: BoardNotFoundException
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return boardAssembler.toModel(board);
    }

    public CollectionModel<EntityModel<Board>> findAllBoards() {
        List<EntityModel<Board>> boards = boardRepository.findAll()
                .stream()
                .map(boardAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                boards,
                linkTo(methodOn(BoardController.class).getAllBoards()).withSelfRel()
        );
    }

}
