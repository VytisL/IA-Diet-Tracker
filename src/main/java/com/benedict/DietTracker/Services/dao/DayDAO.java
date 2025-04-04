package com.benedict.DietTracker.Services.dao;

import com.benedict.DietTracker.Models.FoodType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DayDAO {

    private Connection conn;

    public DayDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(String date, int meal_id, int foodItem_id) {
        String sql = "INSERT INTO Days (date, meal_id, foodItem_id) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            stmt.setInt(2, meal_id);
            stmt.setInt(3, foodItem_id);
            stmt.executeUpdate();
            System.out.println("Day item created successfully");
        } catch (SQLException e) {
            System.out.println("ERROR CREATING DAY ITEM" + e.getMessage());
            e.printStackTrace();
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

    public ArrayList<Integer> findMeals(String date){
        ArrayList<Integer> mealIds = new ArrayList<>();
        String sql = "SELECT meal_id FROM Days WHERE date = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();

            int meal_id = -1;
            while(rs.next()){
                meal_id = rs.getInt("meal_id");
                if(meal_id != -1){
                    mealIds.add(meal_id);
                }
            }
            return mealIds;
        } catch (SQLException e) {
            System.err.println("Error finding MealIds: " + e.getMessage());
            return mealIds;
        }
    }

    public ArrayList<Integer> findFoodItems(String date){
        ArrayList<Integer> foodItemIds = new ArrayList<>();
        String sql = "SELECT foodItem_id FROM Days WHERE date = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();

            int foodItem_id = -1;
            while(rs.next()){
                foodItem_id = rs.getInt("foodItem_id");
                if(foodItem_id != -1){
                    foodItemIds.add(foodItem_id);
                }
            }
            return foodItemIds;
        } catch (SQLException e) {
            System.err.println("Error finding FoodItemIds " + e.getMessage());
            return foodItemIds;
        }
    }

}
