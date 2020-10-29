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

        // TODO: BoardNotFoundException
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
        // TODO: BoardNotFoundException
        Board board = boardRepository.findByName(boardName)
                .orElseThrow(() -> new PostNotFoundException(0L));

        String username = authentication.getName();
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        post.setBoard(board);
        post.setUserModel(user);
        Post saved = postRepository.save(post);

        return postAssembler.toModel(saved);
//        return postAssembler.toModel(postRepository.save(post));
    }

    // TODO: UNAUTHORIZED DELETE EXCEPTION
    private Post findPostByUsernameAndPostId(String username, Long postId) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return postRepository.findByUserModelIdAndId(user.getId(), postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    public void editPost(Long id, Post post, Authentication authentication) {
        // TODO: forbidden exception
        Post edit = findPostByUsernameAndPostId(authentication.getName(), id);
        if (!edit.getId().equals(id)) {
            throw new PostNotFoundException(id);
        }

//        String title = post.getTitle();
//        String content = post.getContent();
//
//        if (title == null || title.isBlank() || content == null || content.isBlank()) {
//             TODO: Bad edit exception
//            throw new IllegalArgumentException();
//        }
//
//        if (title != null && !title.isBlank()) {
//            edit.setTitle(title);
//        }
//        if (content != null && !content.isBlank()) {
//            edit.setContent(content);
//        }

        edit.setTitle(post.getTitle());
        edit.setContent(post.getContent());
        postRepository.save(edit);
    }

    public void deletePost(Long id, Authentication authentication) {
        Post post = findPostByUsernameAndPostId(authentication.getName(), id);
        postRepository.delete(post);
    }
}
