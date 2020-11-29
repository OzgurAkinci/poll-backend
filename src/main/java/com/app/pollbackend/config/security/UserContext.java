package com.app.pollbackend.config.security;


import com.app.pollbackend.domain.SUser;

public class UserContext {
    private static ThreadLocal<SUser> activeUser = new ThreadLocal<>();

    public static SUser getActiveUser() {
        return activeUser.get();
    }

    public static void setActiveUser(SUser user) {
        activeUser.set(user);
    }
}
