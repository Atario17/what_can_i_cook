package com.kondrat.wcic.repository;

import com.kondrat.wcic.domain.Ingredient;
import com.kondrat.wcic.domain.SmallRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<SmallRecipe, Integer> {

    boolean existsByName(String name);

    SmallRecipe findByName(String name);

    //новый метод вместо addNewRecipe, проверяет на наличие в бд, но не особо коротко вышло
    default SmallRecipe createIfNotExists(String name, List<Ingredient> params, String desc) {
        SmallRecipe newSmallRecipe = new SmallRecipe(name, (int) (Math.random() * 1000000));
        newSmallRecipe.setInstruction(desc);
        newSmallRecipe.getIngredients().addAll(params);
        if (!existsByName(newSmallRecipe.getName())) {
            save(newSmallRecipe);
        } else {
            return null;
        }
        return newSmallRecipe;
    }

    default List<SmallRecipe> getRecipesForIngredient(String param){
        List<SmallRecipe>listForRecipes = findAll();
        List<SmallRecipe>suitableRecipes = new ArrayList<>();
        for (SmallRecipe smallRecipe : listForRecipes) {
            for (int i = 0; i < smallRecipe.getIngredients().size(); i++) {
                if (smallRecipe.getIngredients().get(i).getName().equals(param)) {
                    suitableRecipes.add(smallRecipe);
                }
            }
        }
        return suitableRecipes;
    }
    //выбрать все объекты а типа SmallRecipe у которых есть ингредиент с именем
    @Query("select a from SmallRecipe a join a.ingredients i where i.name = ?1")
    List<SmallRecipe> getRecipesForIngredientJpa(String ingredient);
    //потом доделаем
    @Query("select a from SmallRecipe a join a.ingredients i where i.name = ?1")
    List<SmallRecipe> getSuitableRecipesJpa(List<String> params);

    @Query(value = "select count(*) from small_recipe s where s.name = ?1",
            nativeQuery = true
    )

    default List<SmallRecipe> getSuitableRecipes(List<String> params) {
        List<SmallRecipe> listForRecipes = findAll();
        List<SmallRecipe> suitablesRecipes = new ArrayList<>();
        for (SmallRecipe smallRecipe : listForRecipes) {
            boolean allProductsPresent = true;
            for (Ingredient ingredient : smallRecipe.getIngredients()) {
                if (!params.contains(ingredient.getName())) {
                    allProductsPresent = false;
                    break;
                }
            }
            if (allProductsPresent) {
                suitablesRecipes.add(smallRecipe);
            }
        }
        return suitablesRecipes;
    }

//    int fjhgfjhgf(String recipe);
    //    default List<SmallRecipe> removeElement(String name){
//        if(findAll().size()!=0) {
//            for (SmallRecipe smallRecipe : findAll()) {
//                if(smallRecipe.getLogin().equals(name)){
//                    delete(smallRecipe);
//                    break;
//                }
//            }
//        }
//        return findAll();
//    }
//    default List<SmallRecipe> removeElement2(String name){
//        if(findAll().size()!=0) {
//                deleteById(findByLogin(name).getId());
//        }
//        return findAll();
//    }

    //    default SmallRecipe addNewRecipe(String name, String desc, List<Ingredient> params){
//        int id = (int)(Math.random()*1000000);
//        SmallRecipe smallRecipe = new SmallRecipe(name,id);
//        smallRecipe.setInstruction(desc);
//        smallRecipe.getIngredients().addAll(params);
//        save(smallRecipe);
//        return smallRecipe;
//    }
//  default SmallRecipe getRecipesById(Integer id){
//        return findById(id).orElse(null);
//    }

//    void deleteByName(SmallRecipe smallRecipe);
//
//    void removeByName(String name);
//
//    void findByNameAndDelete(String name);
//
//    boolean existsByLogin(String name);
}

