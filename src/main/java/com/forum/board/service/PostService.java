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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    
    public CollectionModel<EntityModel<Post>> findPostsByBoardName(String boardName, int page, int size) {
        if (size < 0 || size > MAX_POSTS_BY_PAGE) {
            size = MAX_POSTS_BY_PAGE;
        }

        Pageable pageable = PageRequest.of(page, size);
        List<EntityModel<Post>> posts = postRepository.findAllByBoardName(boardName, pageable)
                .stream()
                .map(postAssembler::toModel)
                .collect(Collectors.toList());

        if (posts.isEmpty()) {
            throw new PostNotFoundException(0L);
        }

        return CollectionModel.of(
                posts,
                linkTo(methodOn(PostController.class).getPostsByBoardName(boardName, page, size)).withSelfRel()
        );
    }

    public EntityModel<Post> savePost(String boardName, Post post,
                                      Authentication authentication) throws RuntimeException {
        // TODO: exception for board
        Board board = boardRepository.findByName(boardName)
                .orElseThrow(() -> new PostNotFoundException(0L));

        String username = authentication.getName();
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        post.setBoard(board);
        post.setUserModel(user);

        return postAssembler.toModel(postRepository.save(post));
    }

    private Post findPostByUsernameAndPostId(String username, Long postId) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return postRepository.findByUserModelIdAndId(user.getId(), postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

//    public EntityModel<Post> editPost(Long id, Post post, Authentication authentication) {
    public HttpStatus editPost(Long id, Post post, Authentication authentication) {
        // TODO: forbidden exception
//        String username = authentication.getName();
//        UserModel user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//        Long userId = user.getId();
//
//        Post edit = postRepository.findByUserModelIdAndId(userId, id)
//                .orElseThrow(() -> new PostNotFoundException(id));
        Post edit = findPostByUsernameAndPostId(authentication.getName(), id);
        if (!edit.getId().equals(id)) {
            throw new PostNotFoundException(id);
        }

        String title = post.getTitle();
        String content = post.getContent();

        if (title != null && !title.isEmpty()) {
            edit.setTitle(title);
        }
        if (content != null && !content.isEmpty()) {
            edit.setContent(content);
        }

        postRepository.save(edit);
        return HttpStatus.NO_CONTENT;
//        return postAssembler.toModel(postRepository.save(edit));
    }

//    public HttpStatus deletePost(Long id, Authentication authentication) {
    public void deletePost(Long id, Authentication authentication) {
//        String username = authentication.getName();
//        UserModel user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//        Long userId = user.getId();
//
//        Post post = postRepository.findByUserModelIdAndId(userId, id)
//                .orElseThrow(() -> new PostNotFoundException(id));
        Post post = findPostByUsernameAndPostId(authentication.getName(), id);
        postRepository.delete(post);
//        return HttpStatus.NO_CONTENT;
    }
}
