package me.nes0x.image;


import me.nes0x.user.User;

import java.util.UUID;

public class ImageReadModel {
    private UUID id;
    private String title;
    private String extension;
    private String path;
    private boolean secretImage;
    private User user;


    public ImageReadModel(Image source) {
        id = source.getId();
        title = source.getTitle();
        extension = source.getExtension();
        path = source.getPath();
        secretImage = source.isSecretImage();
        user = source.getUser();
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public boolean isSecretImage() {
        return secretImage;
    }

    public void setSecretImage(final boolean secretImage) {
        this.secretImage = secretImage;
    }
}
