package com.forum.board.exception;

public class PasswordExistsException extends RuntimeException {

    public PasswordExistsException(String password) {
        super("Password already exists: " + password);
    }

}
