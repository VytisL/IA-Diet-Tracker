package com.benedict.minibank.Services.dao;

import com.benedict.minibank.Models.Author;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Models.User;
import com.benedict.minibank.Utilities.UserAuthUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AuthorDAO implements GenericDAO{
    private Connection conn;
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public AuthorDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(Author author) {
        String sql = "INSERT INTO Authors (FirstName, LastName, Email, City, Date, User_id) VALUES (?, ?, ?, ?, ?, ?)";
        int userId = Model.getInstance().getLoggedUserId();
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getEmail());
            stmt.setString(4, author.getCity());
            stmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setInt(6, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public void create(Object entity) {

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ObservableList<Author> findAll() {
        ObservableList<Author> authors = FXCollections.observableArrayList();
        String sql = "SELECT FirstName, LastName, Email, City FROM Authors";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String city = rs.getString("City");

                // Create a new Author object and add it to the list
                Author author = new Author(firstName, lastName, email, city);
                authors.add(author);

                // Debug: Print to console to verify
                System.out.printf("Fetched Author: %s %s (%s, %s)%n", firstName, lastName, email, city);
            }

        } catch (SQLException e) {
            logger.severe("Error fetching authors: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }

        return authors;
    }

}
