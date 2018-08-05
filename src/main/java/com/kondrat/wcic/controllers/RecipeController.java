package com.kondrat.wcic.controllers;

import com.kondrat.wcic.domain.Ingredient;
import com.kondrat.wcic.domain.SmallRecipe;
import com.kondrat.wcic.domain.User;
import com.kondrat.wcic.repository.IngredientRepository;
import com.kondrat.wcic.repository.RecipeRepository;
import com.kondrat.wcic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    UserRepository userRepository;

    public RecipeController() {
    }

//    @PostConstruct
    public void fullRecipes() {
        SmallRecipe smallrecipe1 = new SmallRecipe("Жареная картошка", 1);
        SmallRecipe smallrecipe2 = new SmallRecipe("Драники", 2);
        SmallRecipe smallrecipe3 = new SmallRecipe("Пишманье", 3);
        recipeRepository.save(smallrecipe1);
        recipeRepository.save(smallrecipe2);
        recipeRepository.save(smallrecipe3);

        User user1 = new User(1, "Ivanov", "iii");
        user1.getFavouriteRecipes().add(smallrecipe1);
        user1.getFavouriteRecipes().add(smallrecipe2);
        User user2 = new User(2, "Sidorov", "sss");

        userRepository.save(user1);
        userRepository.save(user2);

        smallrecipe1.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("картофель", "лук", "масло подсолнечное", "соль")));
        smallrecipe1.setInstruction("Почистить, пожарить");
        recipeRepository.save(smallrecipe1);

        smallrecipe2.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("картофель", "лук", "масло подсолнечное", "соль", "мука", "яйца куриные")));
        smallrecipe2.setInstruction("Смешать, пожарить.");
        recipeRepository.save(smallrecipe2);
        smallrecipe3.getIngredients().addAll(ingredientRepository.
                createIfNotExists(Arrays.asList("мука")));
        smallrecipe3.setInstruction("Пожарить");
        recipeRepository.save(smallrecipe3);
    }

    @GetMapping("/welcomeToWCIC")
    public String returnStartPage(Model model) {
        return "startPage";
    }

    @GetMapping("/returnLoginPage")
    public String returnLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/returnSignUpPage")
    public String returnSignUpPage(Model model) {
        return "signUp";
    }

    @GetMapping("/returnFavoritePage")
    public String returnFavoritePage(Model model) {
        model.addAttribute("list", userRepository.findByLogin("Ivanov").getFavouriteRecipes());
        return "favoriteRecipes";
    }

    @GetMapping("/congratulations")
    public String toCongratulateAndContinue(Model model) {
        return "congratulations";
    }

    @GetMapping("/inputFieldsForSearchRecipesByIngredients")
    public String returnPage1() {
        return "searchRecipesByIngredients";
    }

    @GetMapping("/inputFieldsForSearchRecipesByIngredient")
    public String returnPage2() {
        return "searchRecipesByIngredient";
    }

    @GetMapping("/inputFieldForAdditionRecipe")
    public String returnPage3() {
        return "addRecipe";
    }

    @GetMapping("/inputFieldForDeletionRecipe")
    public String returnPage4() {
        return "deleteRecipe";
    }

    @PostMapping("/listOfRecipes")
    // public String showListOfRecipes(Model model, String param1, String param2, String param3, String param4, String param5, String param6){
    public String showListOfRecipes(Model model, String[] param) {
        String result;
        List<String> userIngredients = new ArrayList<>();
        for (String s : param) {
            if (s != "") {
                userIngredients.add(s);
            }
        }
        if (userIngredients.size() == 0) {
            return "Error";
        }
        List<SmallRecipe> suitables = recipeRepository.getSuitableRecipes(userIngredients);
        if (suitables == null) {
            result = "Error";
        } else {
            model.addAttribute("usersIngredients", param);
            model.addAttribute("list", suitables);
            result = "result";
        }
        return result;
    }

    @PostMapping("/recipesForIngredient")
    public String showRecipesForIngredient(Model model, String param) {
        String result;
        if (param == "") {
            return "Error";
        }
        List<SmallRecipe> recipesForIngredient = recipeRepository.getRecipesForIngredient(param);
        if (recipesForIngredient == null) {
            result = "Error";
        } else {
            model.addAttribute("list", recipesForIngredient);
            model.addAttribute("ingredient", param);
            result = "recipeForIngredient";
        }
        return result;
    }

    @PostMapping("/removeRecipe")
    public String removeRecipe(Model model, Integer recipeId, String param) {
        if (recipeId != null) {
            SmallRecipe smallRecipe = recipeRepository.findById(recipeId).orElse(null);
            recipeRepository.deleteById(smallRecipe.getId());
            model.addAttribute("list", recipeRepository.findAll());
            model.addAttribute("remoteRecipe", smallRecipe);
            return "updatedList";
        } else if (param != null) {
            SmallRecipe smallRecipe = recipeRepository.findByName(param);
            if (smallRecipe != null) {
                recipeRepository.deleteById(smallRecipe.getId());
                model.addAttribute("list", recipeRepository.findAll());
                model.addAttribute("remoteRecipe", smallRecipe);
                return "updatedList";
            }else{
                return "Error";
            }
        } return "Error";
    }

    @PostMapping("/removeIngredient")
    public String removeIngredient(Model model, Integer ingredientId, String ingredientName) {
        if (ingredientId != null) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
            ingredientRepository.deleteById(ingredient.getId());
            model.addAttribute("list", ingredientRepository.findAll());
            model.addAttribute("remoteRecipe", ingredient);
            return "updatedList";
        } else if (ingredientName != null) {
            Ingredient ingredient = ingredientRepository.findByName(ingredientName);
            if (ingredient != null) {
                ingredientRepository.deleteById(ingredient.getId());
                model.addAttribute("list", ingredientRepository.findAll());
                model.addAttribute("remoteRecipe", ingredient);
                return "updatedList";
            }else{
                return "Error";
            }
        } return "Error";
    }

    @PostMapping("/addRecipe")
    //public String showNewRecipe(Model model, String name, String [] param, String desc){
    public String showNewRecipe(Model model, CreateRecipeRequest createRecipeRequest) {
        if (createRecipeRequest.getIngredients().size() == 0) {
            return "Error";
        }
        List<String> usersIngredients = new ArrayList<>();
        List<String> usersIngredients2 = createRecipeRequest.getIngredients();//Вот тут лучше создавать список
        // еще или каждый раз вызывать в foreach метод у createRecipeRepository?
        for (String s : usersIngredients2) {
            if (s != "") {
                usersIngredients.add(s);
            }
        }
        List<Ingredient> params = new ArrayList<>();
        params.addAll(ingredientRepository.createIfNotExists(usersIngredients));
        SmallRecipe rp4 = recipeRepository.createIfNotExists(createRecipeRequest.getName(), params, createRecipeRequest.getDescription());
        String result;
        if (rp4 != null) {
            model.addAttribute("recipe", rp4.getName());
            model.addAttribute("list", rp4.getProducts());
            model.addAttribute("instruction", rp4.getInstruction());
            model.addAttribute("name", "/inputFieldForAdditionRecipe");
            result = "recipeInstruction";
        } else {
            result = "Error";
        }
        return result;
    }

    //работает только с GET. нужен ли Post в этом случае?
    @GetMapping("/descriptionOfRecipe")
    public String getDescriptionById(Model model, int id) {
        SmallRecipe smallRecipe = recipeRepository.findById(id).orElse(null);
        model.addAttribute("recipe", smallRecipe.getName());
        model.addAttribute("list", smallRecipe.getProducts());
        model.addAttribute("instruction", smallRecipe.getInstruction());
        return "recipeInstruction";
    }

    @GetMapping("/showAllRecipes")
    public String returnAllRecipes(Model model) {
        model.addAttribute("allRecipes", recipeRepository.findAll());
        return "alLRecipes";
    }

    @PostMapping("/toRegisterUser")
    public String toRegisterUser(String name, String password, String email, String rePassword) {
        if(password.equals(rePassword)) {
            User user = new User((int) (Math.random() * 1000000), name, password, email);
            if (!userRepository.createIfNotExists(user)) {
                return "Error";//пока что. потом красным выводитЬ, что есть уже такой юзер
            } else {
                return "congratulations";
            }
        }else{
            return "Error";
        }
    }

    @PostMapping("/verifyUser")
//    public String verifyUser(Model model, String name, String password) {
    public String verifyUser(Model model, CreateUserRequest createUserRequest) {
        User user = new User();
        user.setLogin(createUserRequest.getLogin());
        user.setPassword(createUserRequest.getPassword());
        if (!userRepository.dataVerification(createUserRequest)) {
            return "Error";
        } else {
            model.addAttribute("name", createUserRequest.getLogin());
            return "success";
        }
    }
}



