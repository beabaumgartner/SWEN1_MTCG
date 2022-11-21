package at.fhtw.mtcgapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class User {
    @JsonAlias({"user_id"})
    private Integer id;
    @JsonAlias({"name"})
    private String name;
    @JsonAlias({"coins"})
    private Integer coins;
    @JsonAlias({"password"})
    private String password;

    // Jackson needs the default constructor
    public User() {}

    public User(Integer id, String name, Integer coins, String password) {
        this.id = id;
        this.name = name;
        this.coins = coins;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
