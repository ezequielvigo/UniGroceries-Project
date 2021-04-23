package com.example.unigroceries.model;

import java.io.Serializable;

//Implemented as Serializable in order to bundle as such
public class IngredientModel implements Serializable {
    //Our IngredientModel name
    private String name;

    //Class constructor
    public IngredientModel(String name){
        this.name = name;
    }

    //Getter for the name
    public String getName() {
        return name;
    }

    //Setter for the name
    public void setName(String name) {
        this.name = name;
    }
}
