package com.forum.board.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.forum.board.assembler.PostAssembler;
import com.forum.board.controller.PostController;
import com.forum.board.exception.PostNotFoundException;
import com.forum.board.model.Board;
import com.forum.board.model.Post;
import com.forum.board.model.UserModel;
import com.forum.board.repository.BoardRepository;
import com.forum.board.repository.PostRepository;
import com.forum.board.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    public static final int MAX_POSTS_BY_PAGE = 5;

    private final PostRepository postRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final PostAssembler postAssembler;

    public PostService(PostRepository postRepository, PostAssembler postAssembler,
                       BoardRepository boardRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.postAssembler = postAssembler;
    }

    public EntityModel<Post> findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return postAssembler.toModel(post);
    }

    public CollectionModel<EntityModel<Post>> findPostsByBoardId(Long boardId, int page) {
        Pageable pageable = PageRequest.of(page, MAX_POSTS_BY_PAGE);
        List<EntityModel<Post>> posts = postRepository.findAllByBoardId(boardId, pageable)
                .stream()
                .map(postAssembler::toModel)
                .collect(Collectors.toList());

        if (posts.isEmpty()) {
            // TODO: BoardNotFoundException or PageNotExistsException
            throw new PostNotFoundException(boardId);
        }

        return CollectionModel.of(
                posts,
                linkTo(methodOn(PostController.class).getPostById(boardId)).withSelfRel()
        );
    }

    public EntityModel<Post> savePost(Long boardId, Post post, Authentication authentication) throws RuntimeException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new PostNotFoundException(boardId));

        String username = authentication.getName();
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        post.setBoard(board);
        post.setUser(user);

        return postAssembler.toModel(postRepository.save(post));
    }

}
