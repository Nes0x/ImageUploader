package me.nes0x.image;

import me.nes0x.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "images")
public class Image {
    @Id
    private UUID id;
    private String title;
    private String extension;
    private String path;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    Image() {
    }


    UUID getId() {
        return id;
    }

    void setId(final UUID id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    String getExtension() {
        return extension;
    }

    void setExtension(final String extension) {
        this.extension = extension;
    }

    String getPath() {
        return path;
    }

    void setPath(final String path) {
        this.path = path;
    }

    User getUser() {
        return user;
    }

    void setUser(final User user) {
        this.user = user;
    }
}
