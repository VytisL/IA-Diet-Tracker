package com.benedict.minibank.Models;

import com.benedict.minibank.Services.dao.*;
import com.benedict.minibank.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    public final UserDAO userDAO;
    public final AuthorDAO authorDAO;
    public final FoodTypeDAO foodTypeDAO;
    public final FoodItemDAO foodItemDAO;
    public final MealDAO mealDAO;
    private boolean loginSuccessFlag;
    private final ObservableList<Author> authors;
    private final ObservableList<FoodType> foodTypes;
    private  User currentUser;



    private Model(){
        this.viewFactory = new ViewFactory();
        this.userDAO = new UserDAO(new DatabaseDriver().getConnection());
        this.authorDAO = new AuthorDAO(new DatabaseDriver().getConnection());
        this.foodTypeDAO = new FoodTypeDAO(new DatabaseDriver().getConnection());
        this.foodItemDAO = new FoodItemDAO(new DatabaseDriver().getConnection());
        this.mealDAO = new MealDAO(new DatabaseDriver().getConnection());
        this.loginSuccessFlag = false;
        this.currentUser = null;
        this.authors = FXCollections.observableArrayList();
        this.foodTypes = FXCollections.observableArrayList();
    }

    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return  model;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }


    public boolean getAdminSuccessFlag(){
        return this.loginSuccessFlag;
    }

    public void  setClientAdminSuccessFlag(boolean flag){
        this.loginSuccessFlag = flag;
    }

    public boolean hasRegisteredUsers() {
        return userDAO.countUsers() > 0;
    }

    public boolean isUserAlreadyRegistered(String userName) {
        return userDAO.isUserExist(userName);
    }

    public void createUser(String userName, String password) {
        userDAO.createUser(userName, password, LocalDate.now());
    }

    public void checkCredentials(String userName, String password){
        User user = userDAO.findUserByCredentials(userName, password);
        if (user != null) {
            this.loginSuccessFlag = true;
            this.currentUser = user;
        }
    }

    public String getLoggedUserName(){
        return  currentUser != null ? currentUser.usernameProperty() : null;
    }

    public int getLoggedUserId(){
        return currentUser != null ? currentUser.getId() : null;
    }

    public void createAuthor(String fName, String lastName, String email, String city){
        Author author = new Author(fName, lastName, email, city);

        authorDAO.create(author);
    }

    //Authors
    public ObservableList<Author> getAuthors(){
        System.out.println(authorDAO.findAll());
        return  authorDAO.findAll();
    }

    //FoodTypes
    public ObservableList<FoodType> getFoodTypes(){
        return foodTypeDAO.findAll();
    }
    public void createFoodType(String name, double calories, double protein, double carbs, double fats){
        foodTypeDAO.create(name, calories, protein, carbs, fats);
    }
    public void updateFoodType(FoodType foodType){
        foodTypeDAO.update(foodType);
    }
    public void deleteFoodType(int id){
        foodTypeDAO.delete(id);
    }

    //FoodItems

    public void createFoodItem(FoodType foodType, double portion){
        foodItemDAO.create(foodType, portion);
    }

    //Meals
    public ObservableList<Meal> getMeals(){return mealDAO.findAll(); }
    public void createMeal(String name, ObservableList<FoodType> foodTypes, ObservableList<Double> portions){
        mealDAO.create(name, foodTypes, portions);
    }

    public void deleteMeal(int id){
        mealDAO.delete(id);
    }
    public void getMealsWithDetails(){mealDAO.getAllMealsWithItems();}


}
