package com.example.unigroceries.model;

import java.util.ArrayList;

public class User {
    public String name, surname, email;
    public ArrayList<GroceryListModel> groceryListModels;

    public User(){

    }

    public User(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<GroceryListModel> getGroceryListModels() {
        return groceryListModels;
    }
}
