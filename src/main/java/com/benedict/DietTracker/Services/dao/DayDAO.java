package com.benedict.DietTracker.Services.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
