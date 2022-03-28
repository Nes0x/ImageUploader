package me.nes0x.user;


public class UserReadModel {
    private int id;
    private String name;
    private String url;
    private String description;
    private String color;


    public UserReadModel(User source) {
        id = source.getId();
        name = source.getName();
        url = source.getUrl();
        description = source.getDescription();
        color = source.getColor();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
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
