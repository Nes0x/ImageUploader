package me.nes0x.user;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class UserWriteModel {
    private String name;
    private String password;

    public User toUser() {
        var result = new User();
        result.setName(name);
        result.setPassword(password);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}
