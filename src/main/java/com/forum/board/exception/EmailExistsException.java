package com.forum.board.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String email) {
        super("Email already exits: " + email);
    }

}
