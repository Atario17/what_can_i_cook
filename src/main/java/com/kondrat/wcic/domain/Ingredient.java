package com.kondrat.wcic.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ingredient {
    int id;
    String name;

    public Ingredient(){}

    public Ingredient(String name, int id){
        this.name = name;
        this.id = id;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
