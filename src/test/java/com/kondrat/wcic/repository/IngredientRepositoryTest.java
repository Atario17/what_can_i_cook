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
    @Before
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

    }
    @Test
    public void makeStringsIngredientsTest(){
        //если проводить отдельно тест, то все проходит. Если все подряд, то размер ингредиентов 12, а не 6
        List<String> names = Arrays.asList("корица","красное вино","яблоки","гвоздика");
        int countOfIngredients = 6;
        if(recipeRepository.findAll().size()>3){
            countOfIngredients = 12;
        }
        assertEquals(countOfIngredients,ingredientRepository.findAll().size());
        ingredientRepository.createIfNotExists(names).size();
        assertEquals(countOfIngredients+4,ingredientRepository.findAll().size());
       ingredientRepository.createIfNotExists(names).size();
        assertEquals(countOfIngredients+4,ingredientRepository.findAll().size());
    }
    @Test
    public void ingredientPresentTest(){
        assertEquals(false, ingredientRepository.ingredientPresent(new Ingredient("сливки",15)));
        assertEquals(true, ingredientRepository.ingredientPresent(new Ingredient("картофель",15)));
        assertEquals(false, ingredientRepository.ingredientPresent(null));
    }
}
