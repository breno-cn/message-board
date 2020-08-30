package com.forum.board.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super("Post não encontrado, id: " + id);
    }

}
