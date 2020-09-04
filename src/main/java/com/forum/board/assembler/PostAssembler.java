package com.forum.board.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.controller.CommentController;
import com.forum.board.controller.PostController;
import com.forum.board.model.Post;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PostAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {

    @Override
    public EntityModel<Post> toModel(Post post) {
        return EntityModel.of(
                post,
                linkTo(methodOn(CommentController.class).getCommentsByPostId(post.getId())).withRel("comments"),
                linkTo(methodOn(PostController.class).getPostById(post.getId())).withSelfRel()
        );
    }

}
