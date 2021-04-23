package com.example.unigroceries.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GroceryListModel implements Serializable {

    //All the attributes of our GroceryListModel
    public int id;
    public String date;
    public String title = "Untitled Grocery List";
    public ArrayList<String> groceryList;

    //Empty constructor to avoid database writing errors
    public GroceryListModel(){

    }

    //Class constructor, initialises date and groceryList
    public GroceryListModel(String date, ArrayList<String> groceryList){
        this.date = date;
        this.groceryList = groceryList;
    }

    //All the respective setters and getters of our class
    public ArrayList<String> getGroceryList() {
        return groceryList;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setGroceryList(ArrayList<String> groceryList) {
        this.groceryList = groceryList;
    }
}
