package com.example.unigroceries.model;

import java.util.ArrayList;
import java.util.Date;

public class GroceryListModel {

    public int id;
    public Date date;
    public ArrayList<String> groceryList;

    public GroceryListModel(Date date, ArrayList<String> groceryList){
        this.date = date;
        this.groceryList = groceryList;
    }

    public ArrayList<String> getGroceryList() {
        return groceryList;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setGroceryList(ArrayList<String> groceryList) {
        this.groceryList = groceryList;
    }
}
