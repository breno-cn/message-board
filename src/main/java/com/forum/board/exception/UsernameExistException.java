package com.forum.board.exception;

public class UsernameExistException extends RuntimeException {

    public UsernameExistException(String username) {
        super("Username already exists: " + username);
    }

}
