package com.benedict.minibank.Models;

import javafx.beans.property.*;

public class FoodType extends Food{


    public FoodType(String name, double calories, double protein, double carbs, double fats) {
        this.name = new SimpleStringProperty(name);
        this.calories = new SimpleDoubleProperty(calories);
        this.protein = new SimpleDoubleProperty(protein);
        this.carbs = new SimpleDoubleProperty(carbs);
        this.fats = new SimpleDoubleProperty(fats);
    }
    public FoodType(int id, String name, double calories, double protein, double carbs, double fats) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.calories = new SimpleDoubleProperty(calories);
        this.protein = new SimpleDoubleProperty(protein);
        this.carbs = new SimpleDoubleProperty(carbs);
        this.fats = new SimpleDoubleProperty(fats);
    }





}
