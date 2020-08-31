package com.forum.board.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(Long id) {
        super("Comentário não encontrado de id: " + id);
    }

}
