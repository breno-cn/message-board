package com.forum.board.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.assembler.CommentAssembler;
import com.forum.board.controller.CommentController;
import com.forum.board.exception.CommentNotFoundException;
import com.forum.board.exception.PostNotFoundException;
import com.forum.board.model.Comment;
import com.forum.board.model.Post;
import com.forum.board.model.Role;
import com.forum.board.model.UserModel;
import com.forum.board.repository.CommentRepository;
import com.forum.board.repository.PostRepository;
import com.forum.board.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentAssembler commentAssembler;

    public CommentService(CommentRepository commentRepository, CommentAssembler commentAssembler,
                          PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentAssembler = commentAssembler;
    }

    public EntityModel<Comment> findCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        return commentAssembler.toModel(comment);
    }

    public CollectionModel<EntityModel<Comment>> findCommentsByPostId(Long id) {
        List<EntityModel<Comment>> comments = commentRepository.findAllByPostId(id)
                .stream()
                .map(commentAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                comments,
                linkTo(methodOn(CommentController.class).getCommentsByPostId(id)).withSelfRel()
        );
    }

    public EntityModel<Comment> saveComment(Long postId, Comment comment, Authentication authentication) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        String username = authentication.getName();
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        comment.setPost(post);
        comment.setUserModel(user);

        return commentAssembler.toModel(commentRepository.save(comment));
    }

    public Comment findCommentByUsernameAndCommentId(String username, Long commentId) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return commentRepository.findByUserModelIdAndId(user.getId(), commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

//    public EntityModel<Comment> editComment(Long id, Comment comment, Authentication authentication) {
    public HttpStatus editComment(Long id, Comment comment, Authentication authentication) {
//        String username = authentication.getName();
//        UserModel user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//        Long userId = user.getId();
//        Comment edit = commentRepository.findByUserModelIdAndId(userId, id)
//                .orElseThrow(() -> new CommentNotFoundException(id));
        Comment edit = findCommentByUsernameAndCommentId(authentication.getName(), id);
        if (!edit.getId().equals(id)) {
            throw new CommentNotFoundException(id);
        }

        String content = comment.getContent();
        if (content != null && !content.isEmpty()) {
            edit.setContent(content);
        }

//        return commentAssembler.toModel(commentRepository.save(edit));
        commentRepository.save(edit);
        return HttpStatus.NO_CONTENT;
    }

//    public HttpStatus deleteComment(Long id, Authentication authentication) {
    public void deleteComment(Long id, Authentication authentication) {
//        String username = authentication.getName();
//        UserModel user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//        Long userId = user.getId();
//
//        Comment comment = commentRepository.findByUserModelIdAndId(userId, id)
//                .orElseThrow(() -> new CommentNotFoundException(id));

        Comment comment = findCommentByUsernameAndCommentId(authentication.getName(), id);
        commentRepository.delete(comment);
//        return HttpStatus.NO_CONTENT;
    }
}
