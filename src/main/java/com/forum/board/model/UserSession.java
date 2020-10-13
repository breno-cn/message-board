package com.forum.board.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("user_session")
public class UserSession {

    public String username;
    public String email;

    public UserSession() {}

    public UserSession(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
