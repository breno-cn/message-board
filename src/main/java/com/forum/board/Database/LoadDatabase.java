package com.forum.board.Database;

import com.forum.board.model.Board;
import com.forum.board.model.Comment;
import com.forum.board.model.Post;
import com.forum.board.model.User;
import com.forum.board.repository.BoardRepository;
import com.forum.board.repository.CommentRepository;
import com.forum.board.repository.PostRepository;
import com.forum.board.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(
            BoardRepository boardRepository,
            UserRepository userRepository,
            PostRepository postRepository,
            CommentRepository commentRepository) {
        return args -> {
            Board board1 = new Board("board 1", "description 1");
            Board board2 = new Board("board 2", "description 2");
            List<Board> boards = Arrays.asList(board1, board2);

            User user = new User("user 1", "pass 1", "email 1");

            List<Post> posts = new ArrayList<>();
            for (int i = 0; i < 23; i++) {
                Post post = new Post("Title " + i, "Content " + i);
                post.setBoard(board1);
                post.setUser(user);
                posts.add(post);
            }

            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Comment comment = new Comment("Comment " + i);
                comment.setUser(user);
                comment.setPost(posts.get(0));
                comments.add(comment);
            }

            boardRepository.saveAll(boards);
            userRepository.save(user);
            postRepository.saveAll(posts);
            commentRepository.saveAll(comments);
        };
    }

}
