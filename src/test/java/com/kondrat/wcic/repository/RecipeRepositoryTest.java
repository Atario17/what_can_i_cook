package com.kondrat.wcic.repository;

import com.kondrat.wcic.domain.Ingredient;
import com.kondrat.wcic.domain.SmallRecipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeRepositoryTest {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;

//    @Before
    public void setUp() throws Exception {
//        recipeRepository.deleteAll();
//        ingredientRepository.deleteAll();
//        recipeRepository.flush();
//        ingredientRepository.flush();
        SmallRecipe smallrecipe1 = new SmallRecipe("Жареная картошка", 1);
        SmallRecipe smallrecipe2 = new SmallRecipe("Драники", 2);
        smallrecipe1.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("картофель","лук","масло подсолнечное","соль")));
        recipeRepository.save(smallrecipe1);

        smallrecipe2.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("картофель","лук","масло подсолнечное","соль","мука","яйца куриные")));
        recipeRepository.save(smallrecipe2);

    }//записать сегодняшние правила.

    @Test
    public void putInDB(){
        SmallRecipe smallRecipe = new SmallRecipe("Жареная картошка", 1);
        smallRecipe.setInstruction("Почистить, пожарить.");
        SmallRecipe smallRecipe1 = new SmallRecipe("Борщ", 2);
        smallRecipe1.setInstruction("СмешатЬ, сварить.");
        recipeRepository.save(smallRecipe);
        recipeRepository.save(smallRecipe1);
        assertNotNull(recipeRepository.findById(1).orElse(null));
        assertNotNull(recipeRepository.findById(2).orElse(null));
    }

    @Test
    public void addNewRecipeTest() {
        Integer before = recipeRepository.findAll().size();
        List<String> ingredients = Arrays.asList("мука", "яйца куриные", "молоко", "соль");
        List<Ingredient> params = new ArrayList<>();
        params.addAll(ingredientRepository.createIfNotExists(ingredients));
        SmallRecipe smallRecipe1 = recipeRepository.createIfNotExists("Блины", params,"Смешать. пожарить.");
        assertEquals(4, smallRecipe1.getIngredients().size());
        Integer after = recipeRepository.findAll().size();
        assertTrue(after>before);

    }

    @Test
    public void removeElementTest() {//больше не используем лог.метод removeElement
        Integer before = recipeRepository.findAll().size();
        recipeRepository.delete(recipeRepository.findByName("Пишманье"));// работает
        Integer after = recipeRepository.findAll().size();
        assertTrue(after<before);
    }
    @Test
    public void getRecipesForIngredientTest(){
        assertNotNull(recipeRepository.getRecipesForIngredient("картофель"));
        assertNotNull(recipeRepository.getRecipesForIngredient("лук"));
        //тут похоже тоже просто отсутствуют драники и картошка
        assertEquals(2, recipeRepository.getRecipesForIngredient("картофель").size());
        assertEquals(0, recipeRepository.getRecipesForIngredient("сахар").size());
    }

    @Test
    public void getRecipesForIngredientJpaTest(){
        assertNotNull(recipeRepository.getRecipesForIngredientJpa("картофель"));
        assertNotNull(recipeRepository.getRecipesForIngredientJpa("лук"));
        assertEquals(2, recipeRepository.getRecipesForIngredientJpa("картофель").size());
        assertEquals(0, recipeRepository.getRecipesForIngredientJpa("сахар").size());
//        assertEquals(1, recipeRepository.fjhgfjhgf("Жареная картошка"));

    }
    @Test
    public void getSuitableRecipesTestJpa(){

    }

    @Test
    public void getSuitableRecipesTest(){
//        List<String> params = new ArrayList<>();
//        params.add("картофель");
//        params.add("лук");

//        SmallRecipe smallRecipe = new SmallRecipe("Драники",1);
//        smallRecipe.setInstruction("Почистить, пожарить.");
//        smallRecipe.getIngredients().addAll(ingredientRepository.createIfNotExists(Arrays.asList("картофель","лук")));
//        recipeRepository.save(smallRecipe);

        List<String> params2 = new ArrayList<>();
        params2.add("мясо");
        params2.add("тесто");

        SmallRecipe smallRecipe2 = new SmallRecipe("Пельмени", 2);
        smallRecipe2.setInstruction("Слепить, сварить.");
        smallRecipe2.getIngredients().addAll(ingredientRepository.createIfNotExists(Arrays.asList("мясо","тесто")));
        recipeRepository.save(smallRecipe2);

//        assertEquals(1,recipeRepository.getSuitableRecipes(params).size());
//        assertNotNull(recipeRepository.getSuitableRecipes(params));
        assertEquals(1,recipeRepository.getSuitableRecipes(params2).size());
        assertNotNull(recipeRepository.getSuitableRecipes(params2));
    }
    @Test
    public void getSuitableRecipesTest2(){
        List<String> params = new ArrayList<>();
        params.add("картофель");
        params.add("лук");

        Ingredient ingredient = new Ingredient("картофель",1);
        ingredientRepository.save(ingredient);
        Ingredient ingredient2 = new Ingredient("лук", 2);
        ingredientRepository.save(ingredient2);

        SmallRecipe smallRecipe = new SmallRecipe("Драники",1);
        smallRecipe.setInstruction("Почистить, пожарить.");
        smallRecipe.getIngredients().add(ingredient);
        smallRecipe.getIngredients().add(ingredient2);
        recipeRepository.save(smallRecipe);

        Ingredient ingredient3 = new Ingredient("мясо",3);
        ingredientRepository.save(ingredient3);
        Ingredient ingredient4 = new Ingredient("тесто",4);
        ingredientRepository.save(ingredient4);

        SmallRecipe smallRecipe2 = new SmallRecipe("Пельмени",2);
        smallRecipe2.setInstruction("Слепить, сварить.");
        smallRecipe2.getIngredients().add(ingredient3);
        smallRecipe2.getIngredients().add(ingredient4);
        recipeRepository.save(smallRecipe2);

        assertEquals(1,recipeRepository.getSuitableRecipes(params).size());
        assertNotNull(recipeRepository.getSuitableRecipes(params));
    }
    @Test
    public void findByNameTest(){
        //походу в этот момент жареная картошка удалена из базы
        String randomString = UUID.randomUUID().toString();
        Integer id = ThreadLocalRandom.current().nextInt();
        //сделать с новым именем рецепт и работать с ним
        recipeRepository.save(new SmallRecipe(randomString, id));
        SmallRecipe smallRecipe = recipeRepository.findByName(randomString);
        assertEquals(recipeRepository.findById(id).orElseGet(null), smallRecipe);
    }
}