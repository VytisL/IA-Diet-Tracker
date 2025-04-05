package com.benedict.DietTracker.Services.dao;

import com.benedict.DietTracker.Models.FoodItem;
import com.benedict.DietTracker.Models.FoodType;
import com.benedict.DietTracker.Models.Meal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    public boolean exists(FoodType foodType, double portion) {
        if (foodType == null) return false;

        String sql = "SELECT EXISTS(SELECT 1 FROM FoodItems WHERE name = ? LIMIT 1)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String name = foodType.getName() + ", " + portion + "g";
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) == 1;
            }
        } catch (SQLException e) {
            System.err.println("Error checking FoodItem existence: " + e.getMessage());
            return false;
        }
    }


    public int findFoodItemId(FoodType foodType, double portion) {
        if (foodType == null) return -1;

        String name = foodType.getName() + ", " + portion + "g";
        String sql = "SELECT id FROM FoodItems WHERE name = ? LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("id") : -1;
        } catch (SQLException e) {
            System.err.println("Error finding FoodItem ID: " + e.getMessage());
            return -1;
        }
    }

    public ObservableList<FoodItem> getIdFoodItems(ArrayList<Integer> foodItemIds) {
        ObservableList<FoodItem> foodItems = FXCollections.observableArrayList();

        for (int id : foodItemIds) {
            String sql = "SELECT portion, foodTypeId, name, calories, protein, carbs, fats FROM FoodItems WHERE id = ?";

            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Double portion = rs.getDouble("portion");
                        Integer foodTypeId = rs.getInt("foodTypeId");
                        String name = rs.getString("name");
                        Double calories = rs.getDouble("calories");
                        Double protein = rs.getDouble("protein");
                        Double carbs = rs.getDouble("carbs");
                        Double fats = rs.getDouble("fats");
                        foodItems.add(new FoodItem(id, portion, foodTypeId, name, calories, protein, carbs, fats));
                    }
                }
            } catch (SQLException e) {
                System.out.println("ERROR FETCHING FOODITEMS: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return foodItems;
    }


}
