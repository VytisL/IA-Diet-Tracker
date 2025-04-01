package com.benedict.DietTracker.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public abstract class Food {
    protected IntegerProperty id;

    protected StringProperty name;
    protected DoubleProperty calories;
    protected DoubleProperty protein;
    protected DoubleProperty carbs;
    protected DoubleProperty fats;

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getCalories() {
        return calories.get();
    }

    public DoubleProperty caloriesProperty() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories.set(calories);
    }

    public double getProtein() {
        return protein.get();
    }

    public DoubleProperty proteinProperty() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein.set(protein);
    }

    public double getCarbs() {
        return carbs.get();
    }

    public DoubleProperty carbsProperty() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs.set(carbs);
    }

    public double getFats() {
        return fats.get();
    }

    public DoubleProperty fatsProperty() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats.set(fats);
    }
}
