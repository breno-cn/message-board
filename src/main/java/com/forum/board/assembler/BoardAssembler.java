package com.forum.board.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.controller.PostController;
import com.forum.board.model.Board;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BoardAssembler implements RepresentationModelAssembler<Board, EntityModel<Board>> {

    @Override
    public EntityModel<Board> toModel(Board board) {
        return EntityModel.of(board,
                linkTo(methodOn(PostController.class).getPostsByBoardId(board.getId(), 0)).withRel("posts"));
    }

}
