package com.forum.board.repository;

import com.forum.board.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "user", path = "user")
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {}
