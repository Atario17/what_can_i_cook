package com.kondrat.wcic.repository;

import com.kondrat.wcic.domain.Recipe;

import java.util.ArrayList;
import java.util.List;

public class OldRecipeRepository {
    public List<Recipe> recipes = new ArrayList<>();

    public OldRecipeRepository(){
        fullRecipes();
    }

    Recipe rp = new Recipe("Жареная картошка", 1);
    Recipe rp1 = new Recipe("Драники",2);

    public void fullRecipes(){
        rp.getProducts().add("картофель");
        rp.getProducts().add("масло подсолнечное");
        rp.getProducts().add("соль");
        rp.getProducts().add("лук");
        rp.setInstruction("Почистить, натереть, пожарить.");
        rp1.getProducts().add("картофель");
        rp1.getProducts().add("масло подсолнечное");
        rp1.getProducts().add("соль");
        rp1.getProducts().add("лук");
        rp1.getProducts().add("мука");
        rp1.getProducts().add("яйца куриные");
        rp1.setInstruction("Почистить, натереть, смешать, сформировать, пожарить.");
        recipes.add(rp);
        recipes.add(rp1);
    }

    public List<Recipe> getSuitableRecipes(List<String> params){
        List<Recipe> suitableRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            boolean allProductsPresent = true;
            for (String product : recipe.getProducts()) {
                if (!params.contains(product)) {
                    allProductsPresent = false;
                    break;
                }
            }if (allProductsPresent) {
                suitableRecipes.add(recipe);
            }else{
                suitableRecipes = null;
            }
        }return suitableRecipes;
    }

    public List<Recipe> getRecipesForIngredient(String param){
        List<Recipe>listForRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getProducts().contains(param)) {
                listForRecipes.add(recipe);
            }
        }
        return listForRecipes;
    }
    public List<Recipe> removeElement(String param){
        int count = 0;
        boolean result = false;
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            if (recipe.getName().equals(param)) {
                count = i;
                result = true;
                break;
            }
        }if(result){
            recipes.remove(count);
        }return recipes;
    }
    public Recipe addNewRecipe(String name, List<String> params, String desc) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name)) {
                return null;
            }
        }
        Recipe rp3 = new Recipe(name);
        rp3.setId(recipes.size() + 1);
        rp3.getProducts().addAll(params);
        rp3.setInstruction(desc);
        recipes.add(rp3);
        return rp3;
    }

    public Recipe getRecipesById(Integer id) {
        for (Recipe recipe : recipes) {
            if (recipe.getId() == id) {
                return recipe;
            }
        }
        return null;
    }
}
