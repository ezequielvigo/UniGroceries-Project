package com.example.unigroceries.model;

import java.io.Serializable;

public class IngredientModel implements Serializable {
    private String name;

    public IngredientModel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
