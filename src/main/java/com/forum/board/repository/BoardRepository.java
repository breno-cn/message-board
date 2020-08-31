package com.forum.board.repository;

import com.forum.board.model.Board;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends PagingAndSortingRepository<Board, Long> {}
