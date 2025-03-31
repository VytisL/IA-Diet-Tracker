package com.benedict.minibank.Services.dao;

import com.benedict.minibank.Models.FoodType;
import com.benedict.minibank.Models.Meal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealDAO {

    private Connection conn;

    public MealDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(String name, ObservableList<FoodType> foodTypes, ObservableList<Double> portions) {
        String insertMealsSQL = "INSERT INTO Meals (name, calories, protein, carbs, fats) VALUES (?, ?, ?, ?, ?)";
        String getIdSQL = "SELECT last_insert_rowid()";
        String insertMealItemsSQL = "INSERT INTO MealItems (meal_id, foodType_id, portion) VALUES (?, ?, ?)";

        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFats = 0;
        double fraction;

        // Loop to calculate total nutrients
        for (int i = 0; i < foodTypes.size(); i++) {
            fraction = portions.get(i) / 100;
            totalCalories += foodTypes.get(i).getCalories() * fraction;
            totalProtein += foodTypes.get(i).getProtein() * fraction;
            totalCarbs += foodTypes.get(i).getCarbs() * fraction;
            totalFats += foodTypes.get(i).getFats() * fraction;
        }

        try (
                PreparedStatement mealStmt = conn.prepareStatement(insertMealsSQL);
                PreparedStatement getIdStmt = conn.prepareStatement(getIdSQL);
                PreparedStatement mealItemStmt = conn.prepareStatement(insertMealItemsSQL)
        ) {
            // Insert meal
            mealStmt.setString(1, name);
            mealStmt.setDouble(2, totalCalories);
            mealStmt.setDouble(3, totalProtein);
            mealStmt.setDouble(4, totalCarbs);
            mealStmt.setDouble(5, totalFats);
            mealStmt.executeUpdate();
            System.out.println("Meal created successfully");

            // Get generated meal ID
            ResultSet rs = getIdStmt.executeQuery();
            int mealId = -1;
            if (rs.next()) {
                mealId = rs.getInt(1);
                System.out.println("Generated Meal ID: " + mealId);
            }

            // Insert MealItems
            for (int k = 0; k < foodTypes.size(); k++) {
                mealItemStmt.setInt(1, mealId);
                mealItemStmt.setInt(2, foodTypes.get(k).getId());
                mealItemStmt.setDouble(3, portions.get(k));
                mealItemStmt.executeUpdate(); // <-- missing line
            }

        } catch (SQLException e) {
            System.out.println("ERROR CREATING MEAL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Meal meal) {

        String sql = "UPDATE Meals SET name = ?, calories = ?, protein = ?, carbs = ?, fats = ? WHERE id = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, meal.getName());
            stmt.setDouble(2, meal.getCalories());
            stmt.setDouble(3, meal.getProtein());
            stmt.setDouble(4, meal.getCarbs());
            stmt.setDouble(5, meal.getFats());
            stmt.setInt(6, meal.getId());

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                System.out.println("Meal updated: " + meal);
            }else{
                System.out.println("No Meal found with id: " + meal.getId());
            }
        }catch (SQLException e){
            System.out.println("Error updating Meal: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public ObservableList<Meal> findAll() {
        ObservableList<Meal> meals = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Meals";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double calories = rs.getDouble("calories");
                double protein = rs.getDouble("protein");
                double carbs = rs.getDouble("carbs");
                double fats = rs.getDouble("fats");

                Meal temp = new Meal(id, name, calories, protein, carbs, fats);
                meals.add(temp);
            }

        } catch (SQLException e) {
            System.out.println("ERROR GETTING MEALS: " + e.getMessage());
            e.printStackTrace();
        }

        return meals;
    }

    public void getAllMealsWithItems() {
        String sql = "SELECT m.id AS meal_id, m.name, mi.portion, ft.id AS ft_id, ft.name AS ft_name " +
                "FROM Meals m " +
                "JOIN MealItems mi ON m.id = mi.meal_id " +
                "JOIN FoodTypes ft ON mi.foodType_id = ft.id " +
                "ORDER BY m.id";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int currentMealId = -1;
            String currentMealName = "";

            while (rs.next()) {
                int mealId = rs.getInt("meal_id");
                String mealName = rs.getString("name");
                double portion = rs.getDouble("portion");
                int foodTypeId = rs.getInt("ft_id");
                String foodTypeName = rs.getString("ft_name");

                if (mealId != currentMealId) {
                    currentMealId = mealId;
                    currentMealName = mealName;
                    System.out.println("\nMeal: " + currentMealName + " (ID: " + currentMealId + ")");
                }

                System.out.println("   - Food Type: " + foodTypeName + " (ID: " + foodTypeId + "), Portion: " + portion + "g");
            }

        } catch (SQLException e) {
            System.out.println("ERROR GETTING MEALS WITH ITEMS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Meals WHERE id = ?";
        String itemsSql = "DELETE FROM MealItems WHERE meal_id = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql);
            PreparedStatement itemStmt = this.conn.prepareStatement(itemsSql)) {
            itemStmt.setInt(1, id);
            itemStmt.executeUpdate();
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Meal with id " + id +" was successfully deleted");
            } else {
                System.out.println("No Meal was found with id " + id);
            };
        } catch (SQLException e){
            System.out.println("Error deleting Meal with id " + id);
            e.printStackTrace();
        }
    }


}
