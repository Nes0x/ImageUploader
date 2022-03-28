package me.nes0x.user;

import me.nes0x.image.Image;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    @Id
    private int id;
    private String name;
    private String password;
    @NotBlank
    private String url;
    @NotBlank
    private String description;
    @NotBlank
    private String color;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Image> images;


    User() {
    }

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(final String password) {
        this.password = password;
    }

    Set<Image> getImages() {
        return images;
    }

    void setImages(final Set<Image> images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    void setUrl(final String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    void setColor(final String color) {
        this.color = color;
    }
}
