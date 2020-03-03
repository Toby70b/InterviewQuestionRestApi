package models;

import java.util.List;

public class User {
    private String name,username;
    private List<String> interests;

    public User(String name, String username, List<String> interests) {
        this.name = name;
        this.username = username;
        this.interests = interests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
