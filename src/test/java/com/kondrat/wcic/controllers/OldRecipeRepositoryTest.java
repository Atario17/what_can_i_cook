package com.kondrat.wcic.controllers;

import com.kondrat.wcic.repository.OldRecipeRepository;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Ignore
public class OldRecipeRepositoryTest {

    @Test
    public void getRecipesByIdTest(){//нужен ли он нам еще?
        OldRecipeRepository oldRecipeRepository = new OldRecipeRepository();
        assertNull(oldRecipeRepository.getRecipesById(4));
        assertNotNull(oldRecipeRepository.getRecipesById(2));
        assertEquals("Драники", oldRecipeRepository.getRecipesById(2).getName());
    }
    @Test
    public void addNewRecipeTest() {
        OldRecipeRepository oldRecipeRepository = new OldRecipeRepository();
        List<String> params = Arrays.asList("мука", "яйца куриные", "молоко", "масло подсолнечное", "соль", "сахар");
        oldRecipeRepository.addNewRecipe("Блины", params, "Смешать, раскалить сковороду, пожарить.");
        assertEquals(3, oldRecipeRepository.recipes.size());
        assertEquals(3, oldRecipeRepository.recipes.get(2).getId());
        List<String> params2 = Arrays.asList("картошка", "чеснок", "мука", "яйца куриные", "сметана", "перец");
        oldRecipeRepository.addNewRecipe("Драники", params, "Смешать, раскалить сковороду, пожарить.");
        assertEquals(3, oldRecipeRepository.recipes.size());
        assertEquals(3, oldRecipeRepository.recipes.get(2).getId());

    }
    @Test
    public void getSuitableRecipes() {
        OldRecipeRepository oldRecipeRepository = new OldRecipeRepository();
        List<String> params = Arrays.asList("картофель", "яйца куриные", "лук", "масло подсолнечное", "соль", "мука");
        List<String> suitables = Arrays.asList("Жареная картошка", "Драники");
        oldRecipeRepository.getSuitableRecipes(params);
        assertEquals("Жареная картошка", oldRecipeRepository.getSuitableRecipes(params).get(0).getName());
        assertEquals("Драники", oldRecipeRepository.getSuitableRecipes(params).get(1).getName());
        List<String> params2 = Arrays.asList("молоко", "яйца куриные", "сахар", "масло подсолнечное", "соль", "мука");
        assertNull( oldRecipeRepository.getSuitableRecipes(params2));
    }
    @Test
    public void getRecipesForIngredient(){
        OldRecipeRepository oldRecipeRepository = new OldRecipeRepository();//как подавать список на проверку срарзу, а не по частям
        assertEquals("Жареная картошка", oldRecipeRepository.getRecipesForIngredient("картофель").get(0).getName());
        assertEquals("Драники", oldRecipeRepository.getRecipesForIngredient("картофель").get(1).getName());
    }
    @Test
    public void removeElement(){
        OldRecipeRepository oldRecipeRepository = new OldRecipeRepository();
        oldRecipeRepository.removeElement("Блины");
        assertEquals(2, oldRecipeRepository.recipes.size());
        oldRecipeRepository.removeElement("Жареная картошка");
        assertEquals(1, oldRecipeRepository.recipes.size());
        oldRecipeRepository.removeElement("Драники");
        assertEquals(0, oldRecipeRepository.recipes.size());
        oldRecipeRepository.removeElement("Пельмени");
        assertEquals(0, oldRecipeRepository.recipes.size());

    }
    }

