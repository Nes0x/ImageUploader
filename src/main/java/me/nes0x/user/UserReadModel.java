package me.nes0x.user;


public class UserReadModel {
    private int id;
    private String name;


    public UserReadModel(User source) {
        id = source.getId();
        name = source.getName();
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

}
