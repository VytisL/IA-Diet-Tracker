package com.benedict.minibank.Services.dao;

import com.benedict.minibank.Models.FoodType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodTypeDAO {

    private Connection conn;

    public FoodTypeDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(String name, double calories, double protein, double carbs, double fats) {
        String sql = "INSERT INTO FoodTypes (name, calories, protein, carbs, fats) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, calories);
            stmt.setDouble(3, protein);
            stmt.setDouble(4, carbs);
            stmt.setDouble(5, fats);
            stmt.executeUpdate();
            System.out.println("FoodType created successfully");
        } catch (SQLException e) {
            System.out.println("ERROR CREATING FOODTYPE" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(FoodType foodType) {

        String sql = "UPDATE FoodTypes SET name = ?, calories = ?, protein = ?, carbs = ?, fats = ? WHERE id = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, foodType.getName());
            stmt.setDouble(2, foodType.getCalories());
            stmt.setDouble(3, foodType.getProtein());
            stmt.setDouble(4, foodType.getCarbs());
            stmt.setDouble(5, foodType.getFats());
            stmt.setInt(6, foodType.getId());

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                System.out.println("FoodType updated: " + foodType);
            }else{
                System.out.println("No FoodType found with id: " + foodType.getId());
            }
        }catch (SQLException e){
            System.out.println("Error updating foodType: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public ObservableList<FoodType> findAll() {
        ObservableList<FoodType> foodTypes = FXCollections.observableArrayList();
        String sql = "SELECT id, name, calories, protein, carbs, fats FROM FoodTypes";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String calories = rs.getString("calories");
                String protein = rs.getString("protein");
                String carbs = rs.getString("carbs");
                String fats = rs.getString("fats");

                FoodType foodType = new FoodType(id, name, Double.parseDouble(calories), Double.parseDouble(protein), Double.parseDouble(carbs), Double.parseDouble(fats));
                foodTypes.add(foodType);

                System.out.printf("Fetched FoodType: %s %s (%s, %s)%n", name, calories, protein, carbs, fats);
            }

        } catch (SQLException e) {
            System.out.println("ERROR FETCHING FoodTypes: " + e.getMessage());
            e.printStackTrace();
        }

        return foodTypes;
    }

    public void delete(int id) {
        String sql = "DELETE FROM FoodTypes WHERE id = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("FoodType with id " + id +" was successfully deleted");
            } else {
                System.out.println("No FoodType was found with id " + id);
            };
        } catch (SQLException e){
            System.out.println("Error deleting FoodType with id " + id);
            e.printStackTrace();
        }
    }
}
