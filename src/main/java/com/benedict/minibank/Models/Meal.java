package com.benedict.minibank.Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Meal extends Food{


    private ObservableList<FoodType> foodTypes;
    private ObservableList<SimpleDoubleProperty> portions;


    public Meal(int id, ArrayList<FoodType> foodTypes, ArrayList<Double> portions, int foodTypeId, String name, double calories, double protein, double carbs, double fats) {

    }
}


