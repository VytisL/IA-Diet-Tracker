package com.benedict.DietTracker.Models;

import javafx.beans.property.*;

public class FoodItem extends Food{
    private SimpleObjectProperty<FoodType> foodType;
    private SimpleIntegerProperty foodTypeId;
    private double portion;

    public FoodItem(SimpleObjectProperty<FoodType> foodType, double portion){

        this.foodType = foodType;
        this.portion = portion;


    }
    public FoodItem(int id, double portion, int foodTypeId, String name, double calories, double protein, double carbs, double fats) {
        this.id = new SimpleIntegerProperty(id);
        this.portion = portion;
        this.foodTypeId = new SimpleIntegerProperty(foodTypeId);
        this.name = new SimpleStringProperty(name);
        this.calories = new SimpleDoubleProperty(calories);
        this.protein = new SimpleDoubleProperty(protein);
        this.carbs = new SimpleDoubleProperty(carbs);
        this.fats = new SimpleDoubleProperty(fats);
    }

    public double getPortion() {
        return portion;
    }

    public DoubleProperty portionProperty() {
        return new SimpleDoubleProperty(portion);
    }

    public FoodType getFoodType() {
        return foodType.get();
    }

    public SimpleObjectProperty<FoodType> foodTypeProperty() {
        return foodType;
    }
}
