package com.kondrat.wcic.controllers;

import java.util.List;

public class CreateRecipeRequest {
    String name;
    List<String> ingredients;
    String description;

    public CreateRecipeRequest(){
    }

    public CreateRecipeRequest(String name, List<String> ingredients, String description) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
