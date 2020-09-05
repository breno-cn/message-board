package com.forum.board.Database;

import com.forum.board.model.*;
import com.forum.board.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(
            BoardRepository boardRepository,
            UserRepository userRepository,
            PostRepository postRepository,
            CommentRepository commentRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Board board1 = new Board("board 1", "description 1");
            Board board2 = new Board("board 2", "description 2");
            List<Board> boards = Arrays.asList(board1, board2);

            Role user = new Role();
            user.setRoleName("ROLE_USER");
            Role admin = new Role();
            admin.setRoleName("ROLE_ADMIN");
            List<Role> roles = Arrays.asList(user, admin);
//            List<Role> roles = Arrays.asList(user);

//            UserModel userModel = new UserModel("breno", passwordEncoder.encode("123"), "email 1");
            UserModel userModel = new UserModel("breno", passwordEncoder.encode("123"), "email 1");
            log.info("TEST LOAD DATABASE " + userModel.getPassword());
            userRepository.save(userModel);
            userModel.getRoles().addAll(roles);

            List<Post> posts = new ArrayList<>();
            for (int i = 0; i < 23; i++) {
                Post post = new Post("Title " + i, "Content " + i);
                post.setBoard(board1);
                post.setUser(userModel);
                posts.add(post);
            }

            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Comment comment = new Comment("Comment " + i);
                comment.setUser(userModel);
                comment.setPost(posts.get(0));
                comments.add(comment);
            }

            boardRepository.saveAll(boards);
            roleRepository.saveAll(roles);
            userRepository.save(userModel);
            postRepository.saveAll(posts);
            commentRepository.saveAll(comments);
        };
    }

}
