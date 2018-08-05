package com.kondrat.wcic.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SmallRecipe {
    String name;
    String instruction;
    int id;

    public SmallRecipe(String name, int id){
        this.name = name;
        this.id = id;
    }
    List<Ingredient> ingredients = new ArrayList<>();

    public SmallRecipe() {
    }

    @Transient
    public List<String> getProducts(){
        List<String> names = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            names.add(ingredients.get(i).getName());
        }
        return names;
    }

    @ManyToMany
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


    public SmallRecipe(int id) {
        this.id = id;
    }
    public SmallRecipe(String name) {
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
        if (!(o instanceof SmallRecipe)) return false;

        SmallRecipe recipe = (SmallRecipe) o;

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
