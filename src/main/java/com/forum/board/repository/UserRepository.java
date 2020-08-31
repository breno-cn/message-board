package com.forum.board.repository;

import com.forum.board.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    boolean existsOneByUsername(String username);
    boolean existsOneByEmail(String email);
    boolean existsOneByPassword(String password);
    Optional<User> findByUsername(String username);

}
