package com.forum.board.repository;

import com.forum.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "board", path = "board;")
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByName(@Param("name") String name);

}
