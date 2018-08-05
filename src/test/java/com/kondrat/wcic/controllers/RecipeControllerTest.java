package com.kondrat.wcic.controllers;

import com.kondrat.wcic.domain.Ingredient;
import com.kondrat.wcic.domain.SmallRecipe;
import com.kondrat.wcic.repository.IngredientRepository;
import com.kondrat.wcic.repository.OldRecipeRepository;
import com.kondrat.wcic.repository.RecipeRepository;
import com.kondrat.wcic.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.Assert.*;

class MyModel implements Model {
    Object obj;
    Object obj1;

    @Override
    public Model addAttribute(String s, Object o) {
        if(s.equals("list")) {
            obj = o;
        }else if(s.equals("ingredient")){
            obj1 = o;
        }
        return this;
    }
    @Override
    public Model addAttribute(Object o) {
        return null;
    }
    @Override
    public Model addAllAttributes(Collection<?> collection) {
        return null;
    }
    @Override
    public Model addAllAttributes(Map<String, ?> map) {
        return null;
    }
    @Override
    public Model mergeAttributes(Map<String, ?> map) {
        return null;
    }
    @Override
    public boolean containsAttribute(String s) {
        return false;
    }
    @Override
    public Map<String, Object> asMap() {
        return null;
    }
}
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeControllerTest {
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    RecipeController recipeController;
    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
//        recipeRepository.deleteAll();
//        ingredientRepository.deleteAll();
        userRepository.deleteAll();
        recipeRepository.flush();
        ingredientRepository.flush();
        userRepository.flush();
        recipeController.fullRecipes();
    }


    @Test
    public void fullRecipesTest() {
        assertNotNull(recipeRepository);
        assertEquals(3, recipeRepository.findAll().size());
        SmallRecipe smallRecipe1 = recipeRepository.findById(1).orElse(null);
        SmallRecipe smallRecipe2 = recipeRepository.findById(2).orElse(null);
        assertEquals(4, smallRecipe1.getIngredients().size());
        assertEquals(6, smallRecipe2.getIngredients().size());
    }

    @Test
    public void getDescriptionById() {
        MyModel model = new MyModel();
        assertNotNull(recipeController);
        recipeController.getDescriptionById(model, 1);
        assertNotNull(model.obj);
    }

    @Test
    public void constructor() {
        MyModel model = new MyModel();
        assertNotNull(recipeController);
        recipeController.showListOfRecipes(model, new String[]{"картофель", "лук",
                "соль", "мука", "яйца куриные", "масло подсолнечное"});
        assertNotNull(model.obj);
        assertTrue(model.obj instanceof List);
        List<SmallRecipe> recipes = (List<SmallRecipe>) model.obj;
        assertEquals(3, recipes.size());
    }

    @Test
    public void recipesForIngredientTest() {
        MyModel model = new MyModel();
        recipeController.showRecipesForIngredient(model, "картофель");

        assertNotNull(model.obj);
        assertTrue(model.obj instanceof List);

        List<SmallRecipe> recipes = (List<SmallRecipe>) model.obj;

        assertEquals(2, recipes.size());
        assertEquals("картофель", model.obj1);
        assertEquals(1, recipes.get(0).getId());
        assertEquals(2, recipes.get(1).getId());

        recipeController.showRecipesForIngredient(model, "мука");
        assertNotNull(model.obj);
        assertTrue(model.obj instanceof List);
        List<SmallRecipe> recipes1 = (List<SmallRecipe>) model.obj;
        assertEquals(2, recipes1.size());
        assertEquals(2, recipes1.get(0).getId());
    }

    @Test
    public void removeRecipeTest() {
        MyModel model = new MyModel();
        recipeController.removeRecipe(model, null, "Пишманье");
        List<SmallRecipe> recipes = (List<SmallRecipe>) model.obj;
        assertTrue(model.obj instanceof List);
        assertEquals(2, recipes.size());
        assertEquals(1, recipes.get(0).getId());
        recipeController.removeRecipe(model, null, "Блины");
        List<SmallRecipe> recipes2 = (List<SmallRecipe>) model.obj;
        assertEquals(2, recipes2.size());
    }

    @Test
    public void showNewRecipеTest() {
//        OldRecipeRepository oldRecipeRepository = new OldRecipeRepository();
        MyModel model = new MyModel();
        List<String> ingredients1 = new ArrayList<>(Arrays.asList("мука", "яйца куриные",
                "молоко", "масло подсолнечное", "соль", "сахар"));
        List<String> ingredients2 = new ArrayList<>(Arrays.asList("картофель", "морковь",
                "свекла", "соль", "капуста", "помидоры"));
        recipeController.showNewRecipe(model, new CreateRecipeRequest("Блины", ingredients1,
                "Смешать, раскалить сковороду, пожарить."));
        assertTrue(model.obj instanceof List);
        assertEquals(4, recipeController.recipeRepository.findAll().size());

        recipeController.showNewRecipe(model, new CreateRecipeRequest("Драники", Arrays.asList("мука", "яйца куриные",
                "масло подсолнечное", "соль", "картофель", "лук"),
                "Смешать, раскалить сковороду, пожарить."));
        assertEquals(4, recipeController.recipeRepository.findAll().size());
        recipeController.showNewRecipe(model, new CreateRecipeRequest("Борщ", Arrays.asList("картофель", "морковь",
                "свекла", "соль", "капуста", "помидоры"),
                "Нарезать, добавить поочередно в бульон, варить"));
        assertEquals(5, recipeController.recipeRepository.findAll().size());
//        for(Ingredient ingredient : recipeRepository.findByName("Борщ").getIngredients()){
//            if(ingredient.getName()!="соль" || ingredient.getName()!="картофель") {
//                recipeController.removeIngredient(model, null, ingredient.getName());
//            }
//        }
//        recipeController.removeIngredient(model,null,"сахар");
//        recipeController.removeIngredient(model,null,"соль");
//        recipeController.removeIngredient(model,null,"молоко");
//        recipeController.removeIngredient(model,null,"сахар");
        recipeController.removeRecipe(model, null, "Блины");
        recipeController.removeRecipe(model, null, "Борщ");
//        assertEquals(6, ingredientRepository.findAll().size());

    }

    @Test
    public void toRegisterUserTest() {
        assertEquals(2, userRepository.findAll().size());
        assertEquals("congratulations", recipeController.toRegisterUser("Vasya", "ololo", "matroskin1980@mail.ru", "ololo"));
        assertEquals(3, userRepository.findAll().size());
        assertEquals("Error", recipeController.toRegisterUser("Vasya", "ololo", "matroskin1980@mail.ru", "ololo"));
        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    public void verifyUserTest() {
        assertEquals(2, userRepository.findAll().size());
        MyModel model = new MyModel();
        CreateUserRequest user1 = new CreateUserRequest("Ivanov", "iii");
        CreateUserRequest user2 = new CreateUserRequest("Ivanov", "ii");
        CreateUserRequest user3 = new CreateUserRequest("Vasilev", "123");
        assertEquals("success", recipeController.verifyUser(model, user1));
        assertEquals("Error", recipeController.verifyUser(model, user2));
        assertEquals("Error", recipeController.verifyUser(model, user3));
//        assertNotNull(model.obj);
//        assertTrue(model.obj instanceof String);
//        String name2 = (String)model.obj;
//        assertEquals("Vasya", name2);
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    public void returnAllRecipesTest() {
        //что делать, если в методе с аннотацией before нельзя удалять все рецепты(связаны с др.таблицамИ), а тут тогда не проходит
        assertEquals(3, recipeRepository.findAll().size());
    }

    @Test
    public void returnFavoritePageTest() {
        MyModel model = new MyModel();
        assertEquals("favoriteRecipes", recipeController.returnFavoritePage(model));
        List<SmallRecipe> recipes = (List<SmallRecipe>) model.obj;
        assertNotNull(model.obj);
        assertTrue(model.obj instanceof List);
    }
}
