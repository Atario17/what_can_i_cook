package com.kondrat.wcic.repository;

import com.kondrat.wcic.domain.Ingredient;
import com.kondrat.wcic.domain.SmallRecipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientRepositoryTest {
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    RecipeRepository recipeRepository;
//    @Before
    public void setUp() throws Exception {
//        recipeRepository.deleteAll();
//        ingredientRepository.deleteAll();
//        recipeRepository.flush();
//        ingredientRepository.flush();
        SmallRecipe smallrecipe1 = new SmallRecipe("Жареная картошка", 1);
        SmallRecipe smallrecipe2 = new SmallRecipe("Драники", 2);
        SmallRecipe smallrecipe3 = new SmallRecipe("Пишманье", 3);

        smallrecipe1.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("картофель","лук","масло подсолнечное","соль")));
        recipeRepository.save(smallrecipe1);

        smallrecipe2.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("картофель","лук","масло подсолнечное","соль","мука","яйца куриные")));
        recipeRepository.save(smallrecipe2);

        smallrecipe3.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("мука")));
        smallrecipe3.setInstruction("Пожарить");
        recipeRepository.save(smallrecipe3);

    } public void toFillInTheData(){
        SmallRecipe smallrecipe1 = new SmallRecipe("Жареная картошка", 1);
        SmallRecipe smallrecipe2 = new SmallRecipe("Драники", 2);
        SmallRecipe smallrecipe3 = new SmallRecipe("Пишманье", 3);

        smallrecipe1.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("картофель","лук","масло подсолнечное","соль")));
        recipeRepository.save(smallrecipe1);

        smallrecipe2.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("картофель","лук","масло подсолнечное","соль","мука","яйца куриные")));
        recipeRepository.save(smallrecipe2);

        smallrecipe3.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("мука")));
        smallrecipe3.setInstruction("Пожарить");
        recipeRepository.save(smallrecipe3);
    }
    public void toClearData(){
        recipeRepository.deleteById(1);
        recipeRepository.deleteById(2);
        recipeRepository.deleteById(3);
    }
    @Test
    public void makeStringsIngredientsTest(){
        //если проводить отдельно тест, то все проходит. Если все подряд, то размер ингредиентов 12, а не 6
        toFillInTheData();
        assertEquals(6, ingredientRepository.findAll().size());
        List<String> names = Arrays.asList("корица","красное вино","яблоки","гвоздика");
        ingredientRepository.createIfNotExists(names);
        assertEquals(10,ingredientRepository.findAll().size());
       ingredientRepository.createIfNotExists(names).size();
        assertEquals(10,ingredientRepository.findAll().size());

    }
    @Test
    public void ingredientPresentTest(){
        toFillInTheData();
        assertEquals(false, ingredientRepository.ingredientPresent(new Ingredient("сливки",15)));
        assertEquals(true, ingredientRepository.ingredientPresent(new Ingredient("картофель",15)));
        assertEquals(false, ingredientRepository.ingredientPresent(null));

    }
}
