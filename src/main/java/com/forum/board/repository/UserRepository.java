package com.forum.board.repository;

import com.forum.board.model.UserModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserModel, Long> {

    boolean existsOneByUsername(String username);
    boolean existsOneByEmail(String email);
    boolean existsOneByPassword(String password);
    Optional<UserModel> findByUsername(String username);

}
