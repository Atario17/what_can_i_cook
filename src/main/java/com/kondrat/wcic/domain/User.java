package com.kondrat.wcic.domain;

import com.kondrat.wcic.controllers.CreateUserRequest;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user_account")
public class User {
    int id;
    String login;
    String password;
    String email;
    List<SmallRecipe> favouriteRecipes = new ArrayList<>();

    public User() {
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @ManyToMany
    public List<SmallRecipe> getFavouriteRecipes() {
        return favouriteRecipes;
    }

    @Transient
    public List<String> getFavourite() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < favouriteRecipes.size(); i++) {
            names.add(favouriteRecipes.get(i).getName());
        }
        return names;
    }

    public void setFavouriteRecipes(List<SmallRecipe> favouriteRecipes) {
        this.favouriteRecipes = favouriteRecipes;
    }


    public User(int id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getLogin().equals(user.getLogin())) return false;
        return getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getLogin().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}
