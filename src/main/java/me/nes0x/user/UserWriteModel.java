package me.nes0x.user;

import javax.validation.constraints.NotBlank;

public class UserWriteModel {
    private String name;
    private String password;
    @NotBlank
    private String url;
    @NotBlank
    private String description;
    @NotBlank
    private String color;

    public User toUser() {
        var result = new User();
        result.setName(name);
        result.setPassword(password);
        result.setUrl(url);
        result.setDescription(description);
        result.setColor(color);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }
}
