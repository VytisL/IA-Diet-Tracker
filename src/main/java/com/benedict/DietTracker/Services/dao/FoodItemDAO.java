package com.benedict.DietTracker.Services.dao;

import com.benedict.DietTracker.Models.FoodItem;
import com.benedict.DietTracker.Models.FoodType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodItemDAO {
    private Connection conn;

    public FoodItemDAO(Connection conn){
        this.conn = conn;
    }


    public void create(FoodType foodType, double portion) {
        String sql = "INSERT INTO FoodItems (portion, foodTypeId, name, calories, protein, carbs, fats) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setDouble(1, portion);
            stmt.setInt(2, foodType.getId());
            stmt.setString(3, (foodType.getName() + ", " + portion + "g"));
            stmt.setDouble(4, foodType.getCalories()*(portion/100));
            stmt.setDouble(5, foodType.getProtein()*(portion/100));
            stmt.setDouble(6, foodType.getCarbs()*(portion/100));
            stmt.setDouble(7, foodType.getFats()*(portion/100));
            stmt.executeUpdate();
            System.out.println("FoodItem created successfully");
        } catch (SQLException e) {
            System.out.println("ERROR CREATING FOODITEM" + e.getMessage());
            e.printStackTrace();
        }
    }

    public ObservableList<FoodItem> findAll() {
        ObservableList<FoodItem> foodItems = FXCollections.observableArrayList();
        String sql = "SELECT id, portion, foodTypeId, name, calories, protein, carbs, fats FROM FoodTypes";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String portion = rs.getString("portion");
                int foodTypeId = rs.getInt("foodTypeId");
                String name = rs.getString("name");
                String calories = rs.getString("calories");
                String protein = rs.getString("protein");
                String carbs = rs.getString("carbs");
                String fats = rs.getString("fats");


                FoodItem foodItem = new FoodItem(id, Double.parseDouble(portion), foodTypeId, name, Double.parseDouble(calories), Double.parseDouble(protein), Double.parseDouble(carbs), Double.parseDouble(fats));
                foodItems.add(foodItem);

                System.out.printf("Fetched FoodType: %s %s (%s, %s)%n", name, calories, protein, carbs, fats);
            }

        } catch (SQLException e) {
            System.out.println("ERROR FETCHING FoodTypes: " + e.getMessage());
            e.printStackTrace();
        }

        return foodItems;
    }


}
