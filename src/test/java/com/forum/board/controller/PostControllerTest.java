package com.forum.board.controller;

import com.forum.board.model.Post;
import com.forum.board.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostControllerTest {

    @Autowired
    private TestEntityManager postEntityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void whenFindByIdThenReturnPost() {
        // given
        Post post = new Post("Testing Title", "Testing description");
        postEntityManager.persist(post);
        postEntityManager.flush();

        // when
        Post found = postRepository.findPostById(post.getId());

        // then
        assertThat(found.getId())
                .isEqualTo(post.getId());
    }

}
