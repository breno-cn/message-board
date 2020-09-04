package com.forum.board.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.controller.CommentController;
import com.forum.board.model.Comment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CommentAssembler implements RepresentationModelAssembler<Comment, EntityModel<Comment>> {

    @Override
    public EntityModel<Comment> toModel(Comment comment) {
        return EntityModel.of(
                comment,
                linkTo(methodOn(CommentController.class).getCommentById(comment.getId())).withSelfRel()
        );
    }

}
