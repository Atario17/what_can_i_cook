package com.kondrat.wcic.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;


public class Recipe {
    String name;
    String instruction;
    int id;
    List<String> products = new ArrayList<>();

    public Recipe(String name, int id){
        this.name = name;
        this.id = id;
    }

    public Recipe() {
    }

    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public List<String> getProducts() {
        return products;
    }
    public Recipe(int id) {
        this.id = id;
    }
    public Recipe(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;

        Recipe recipe = (Recipe) o;

        if (getId() != recipe.getId()) return false;
        return getName().equals(recipe.getName());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getId();
        return result;
    }
}
