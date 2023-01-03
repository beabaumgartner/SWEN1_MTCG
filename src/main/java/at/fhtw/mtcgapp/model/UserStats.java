package at.fhtw.mtcgapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserStats {
    @JsonAlias({"Name"})
    private String name;
    @JsonAlias({"Elo"})
    private Integer elo;
    @JsonAlias({"Wins"})
    private Integer wins;
    @JsonAlias({"Losses"})
    private Integer losses;

    // Jackson needs the default constructor
    public UserStats() {}

    public UserStats(String name, Integer wins, Integer losses) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.elo = wins-losses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getElo() {
        return elo;
    }

    public void setElo(Integer elo) {
        this.elo = elo;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }
}
