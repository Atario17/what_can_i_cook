package com.kondrat.wcic.repository;

import com.kondrat.wcic.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    default List<Ingredient> createIfNotExists(List<String> names) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String s : names) {
            Ingredient newIngredient = new Ingredient(s, (int) (Math.random() * 1000000));
            if (!ingredientPresent(newIngredient)) {
                save(newIngredient);
            }else{
                newIngredient = findByName(s);
            }
            ingredients.add(newIngredient);
        }
        return ingredients;
    }

    Ingredient findByName(String s);

    default boolean ingredientPresent(Ingredient newIngredient){
        if(newIngredient==null){
            return false;
        }
        return existsByName(newIngredient.getName());
    }

    boolean existsByNameAndId(String name, int id);


    boolean existsByName(String name);
}
/*
    default List<Ingredient> createIfNotExists(List<String> names) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String s : names) {
//            boolean ingredinetPresent = false;
//            Ingredient ingredient1 = new Ingredient(s, (int) (Math.random() * 1000000));
            Ingredient newIngredient = new Ingredient(s, (int) (Math.random() * 1000000));
//            for(Ingredient ingredient : findAll()){
//                if(findAll().size()==0){
//                    break;
//                }
//                if(ingredient.getLogin().equals(s)){
//                    ingredinetPresent = true;
//                    ingredient1 = ingredient;
//                    break;
//                }
//            }
            if (!ingredientPresent(newIngredient)) {
                save(newIngredient);
            }
            ingredients.add(newIngredient);
        }return ingredients;
    }
 */