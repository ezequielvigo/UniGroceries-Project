package com.example.unigroceries.model;

import java.util.ArrayList;

public class User {
    //All the attributes of our User class
    public String name, surname, email;
    public ArrayList<GroceryListModel> groceryListModels;

    //Empty constructor to avoid Database writing errors
    public User(){

    }

    //Class constructor used to initialise name, surname and email
    public User(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    //All the respective getters for the variables
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
