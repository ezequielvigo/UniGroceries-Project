package com.example.unigroceries;

public class Recipe {
    //The main attributes a recipe has to display
    int id;
    String title;
    String imageUrl;

    //The recipe constructor
    public Recipe(int id, String title, String imageUrl){
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    //All corresponding getters
    public int getId(){
        return id;
    }

    public String getTitle(){
        return getTitle();
    }

    public String getImageUrl(){
        return imageUrl;
    }


}
