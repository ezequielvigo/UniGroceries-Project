package com.example.unigroceries.model;

public class RecipeModel {
    //The main attributes a recipe has to display
    private int id;
    private String title;
    private String imageUrl;

    //The recipe constructor
    public RecipeModel(int id, String title, String imageUrl){
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    //All corresponding getters
    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

