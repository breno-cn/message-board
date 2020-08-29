package com.forum.board.repository;

import com.forum.board.model.UserModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "user", path = "user")
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserModel, Long> {}
