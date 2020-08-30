package com.forum.board.Database;

import com.forum.board.model.Board;
import com.forum.board.repository.BoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(BoardRepository boardRepository) {
        return args -> {
            Board board1 = new Board("board 1", "description 1");
            Board board2 = new Board("board 2", "description 2");
            List<Board> boards = Arrays.asList(board1, board2);

            boardRepository.saveAll(boards);
        };
    }

}
