package com.benedict.minibank.Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Meal extends Food{


    private ObservableList<FoodType> foodTypes;
    private ObservableList<SimpleDoubleProperty> portions;


    public Meal(int id, ArrayList<FoodType> foodTypes, ArrayList<Double> portions, String name, double calories, double protein, double carbs, double fats) {
        this.id = new SimpleIntegerProperty(id);
        this.foodTypes = FXCollections.observableArrayList(foodTypes);
        this.portions = FXCollections.observableArrayList();
        for (Double portion : portions) {
            this.portions.add(new SimpleDoubleProperty(portion));
        }
        this.name = new SimpleStringProperty(name);
        this.calories = new SimpleDoubleProperty(calories);
        this.protein = new SimpleDoubleProperty(protein);
        this.carbs = new SimpleDoubleProperty(carbs);
        this.fats = new SimpleDoubleProperty(fats);
    }
}


