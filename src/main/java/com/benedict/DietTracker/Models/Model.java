package com.benedict.DietTracker.Models;

import com.benedict.DietTracker.Services.dao.*;
import com.benedict.DietTracker.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    public final UserDAO userDAO;
    public final FoodTypeDAO foodTypeDAO;
    public final FoodItemDAO foodItemDAO;
    public final MealDAO mealDAO;
    public final DayDAO dayDAO;
    private boolean loginSuccessFlag;
    private final ObservableList<FoodType> foodTypes;
    private  User currentUser;



    private Model(){
        this.viewFactory = new ViewFactory();
        this.userDAO = new UserDAO(new DatabaseDriver().getConnection());
        this.foodTypeDAO = new FoodTypeDAO(new DatabaseDriver().getConnection());
        this.foodItemDAO = new FoodItemDAO(new DatabaseDriver().getConnection());
        this.mealDAO = new MealDAO(new DatabaseDriver().getConnection());
        this.dayDAO = new DayDAO(new DatabaseDriver().getConnection());
        this.loginSuccessFlag = false;
        this.currentUser = null;
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
    public boolean foodItemExists(FoodType foodType, double portion){
        return foodItemDAO.exists(foodType, portion);
    }
    public int foodItemId(FoodType foodType, double portion){
        return foodItemDAO.findFoodItemId(foodType, portion);
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

    //Days
    public void createDay(String date, int meal_id, int foodItem_id){
        dayDAO.create(date, meal_id, foodItem_id);
    }

}
