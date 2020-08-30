package com.forum.board.Database;

import com.forum.board.model.Board;
import com.forum.board.model.Post;
import com.forum.board.model.User;
import com.forum.board.repository.BoardRepository;
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
            PostRepository postRepository) {
        return args -> {
            Board board1 = new Board("board 1", "description 1");
            Board board2 = new Board("board 2", "description 2");
            List<Board> boards = Arrays.asList(board1, board2);

            User user = new User("user 1", "email 1", "pass 1");

            List<Post> posts = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Post post = new Post(String.format("Title %d", i), String.format("Content %d", i));
                post.setBoard(board1);
                post.setUser(user);
                posts.add(post);
            }

            boardRepository.saveAll(boards);
            userRepository.save(user);
            postRepository.saveAll(posts);
        };
    }

}
