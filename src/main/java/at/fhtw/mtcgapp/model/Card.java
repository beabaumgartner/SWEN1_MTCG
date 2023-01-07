package at.fhtw.mtcgapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.beans.ConstructorProperties;

public class Card {
    @JsonAlias({"Id"})
    private String card_id;
    @JsonAlias({"Name"})
    private String name;
    @JsonAlias({"Damage"})
    private Integer damage;

    @JsonIgnore
    private String card_type;



    // Jackson needs the default constructor
    public Card(@JsonProperty("Name") String name)
    {
        this.name = name;

        if(this.name.toLowerCase().endsWith("spell"))
        {
            this.card_type = "spell";
        }
        else
        {
            this.card_type = "monster";
        }
    }

    public Card(String card_id, String name, Integer damage) {
        this.card_id = card_id;
        this.name = name;
        this.damage = damage;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public String getCard_type() { return card_type; }

    public void setCard_type(String card_type) { this.card_type = card_type; }
}
