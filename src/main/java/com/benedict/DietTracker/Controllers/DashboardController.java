package com.benedict.DietTracker.Controllers;

import com.benedict.DietTracker.Models.FoodItem;
import com.benedict.DietTracker.Models.FoodType;
import com.benedict.DietTracker.Models.Meal;
import com.benedict.DietTracker.Models.Model;
import com.benedict.DietTracker.Utilities.AlertUtility;
import com.benedict.DietTracker.Utilities.DialogUtility;
import com.benedict.DietTracker.Views.MenuOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    public Button add_food_type_btn;
    @FXML
    public Button add_food_into_day_one_btn;
    @FXML
    public Button add_food_into_day_two_btn;
    @FXML
    public Button create_meal_btn;
    @FXML
    public Button add_meal_into_day_one_btn;
    @FXML
    public Button add_meal_into_day_two_btn;

    @FXML
    public Button sort_btn;
    @FXML
    public ComboBox<String> sort_comboBox;

    @FXML
    public ComboBox<String> day_sort_comboBox;
    @FXML
    public Button day_sort_btn;

    @FXML
    public TextField year_field;
    @FXML
    public TextField month_field;
    @FXML
    public TextField day_field;
    @FXML
    public Button change_date_btn;

    //FoodType
    @FXML
    public TableView<FoodType> food_types_table;
    @FXML
    public TableColumn<FoodType, String> types_col_name;
    @FXML
    public TableColumn<FoodType, String> types_col_calories;
    @FXML
    public TableColumn<FoodType, String> types_col_protein;
    @FXML
    public TableColumn<FoodType, String> types_col_carbs;
    @FXML
    public TableColumn<FoodType, String> types_col_fats;
    @FXML
    public MenuItem delete_foodType_btn;

    //Meal
    @FXML
    public TableView<Meal> meals_table;
    @FXML
    public TableColumn<Meal, String> meals_col_name;
    @FXML
    public TableColumn<Meal, String> meals_col_calories;
    @FXML
    public TableColumn<Meal, String> meals_col_protein;
    @FXML
    public TableColumn<Meal, String> meals_col_carbs;
    @FXML
    public TableColumn<Meal, String> meals_col_fats;
    @FXML
    public MenuItem delete_meal_btn;

    //Day Meal
    @FXML
    public TableView<Meal> day_meals_table;
    @FXML
    public TableColumn<Meal, String> day_meal_col;
    @FXML
    public TableColumn<Meal, String> day_meal_cal_col;
    @FXML
    public TableColumn<Meal, String> day_meal_protein_col;
    @FXML
    public TableColumn<Meal, String> day_meal_carbs_col;
    @FXML
    public TableColumn<Meal, String> day_meal_fats_col;
    @FXML
    public MenuItem delete_meal_day_btn;

    //Day FoodItem
    @FXML
    public TableView<FoodItem> day_items_table;
    @FXML
    public TableColumn<FoodItem, String> day_item_col;
    @FXML
    public TableColumn<FoodItem, String> day_item_cal_col;
    @FXML
    public TableColumn<FoodItem, String> day_item_protein_col;
    @FXML
    public TableColumn<FoodItem, String> day_item_carbs_col;
    @FXML
    public TableColumn<FoodItem, String> day_item_fats_col;
    @FXML
    public MenuItem delete_item_day_btn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_food_type_btn.setOnAction(event -> onAddFoodType());
        add_food_into_day_one_btn.setOnAction(event -> onAddFoodIntoDay());
        add_food_into_day_two_btn.setOnAction(event -> onAddFoodIntoDay());
        create_meal_btn.setOnAction(event -> onCreateMeal());
        add_meal_into_day_one_btn.setOnAction(event -> onAddMealIntoDay());
        add_meal_into_day_two_btn.setOnAction(event -> onAddMealIntoDay());
        delete_foodType_btn.setOnAction(event -> onDeleteFoodType());
        delete_meal_btn.setOnAction(event -> onDeleteMeal());
        sort_btn.setOnAction(event -> onSort());
        day_sort_btn.setOnAction(event -> onDaySort());

        delete_meal_day_btn.setOnAction(event -> onDeleteDayMeal());
        delete_item_day_btn.setOnAction(event -> onDeleteDayItem());

        change_date_btn.setOnAction(event -> onChangeDate());

        setSortComboBoxValues();

        initTypesTableColumns();
        setRowFactoryForFoodTypesTable();
        loadFoodTypeData();

        initMealsTableColumns();
        loadMealData();

        initDayMealsTableColumns();
        initDayItemsTableColumns();
    }


    //==================================
    //Food and Meal tab
    //==================================
    private void onAddFoodType() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.ADD_FOOD_TYPE);
    }
    private void onAddFoodIntoDay() {
      Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.ADD_FOOD_ITEM_INTO_DAY);
    }
    private void onCreateMeal() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CREATE_MEAL);
    }
    private void onAddMealIntoDay() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.ADD_MEAL_INTO_DAY);
    }


    private void setSortComboBoxValues(){
        ObservableList<String> options = FXCollections.observableArrayList("Name", "Calories", "Protein", "Carbs", "Fats");
        sort_comboBox.setItems(options);
        day_sort_comboBox.setItems(options);
    }

    //FoodTypes table

    private void editFoodType(FoodType foodType) {
        Optional<FoodType> result = DialogUtility.showEditFoodTypeDialog(foodType);
        result.ifPresent(updateFoodType -> {
            Model.getInstance().updateFoodType(foodType);
            System.out.println("Update result");
        });
    }

    private void initTypesTableColumns() {
        types_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        types_col_calories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        types_col_protein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        types_col_carbs.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        types_col_fats.setCellValueFactory(new PropertyValueFactory<>("fats"));
    }

    private void setRowFactoryForFoodTypesTable() {
        food_types_table.setRowFactory(tv -> {
           TableRow<FoodType> row = new TableRow<>();
           row.setOnMouseClicked(event ->{
               if(event.getClickCount() == 2 && (!row.isEmpty())){
                   FoodType selectedFoodType = row.getItem();
                   editFoodType(selectedFoodType);
               }
           });
           return row;
        });
    }

    private void loadFoodTypeData() {
        ObservableList<FoodType> foodTypes = Model.getInstance().getFoodTypes();
        food_types_table.setItems(foodTypes);
    }

    private void onDeleteFoodType() {
                                    //converts to FoodType
        FoodType selectedFoodType = (FoodType) food_types_table.getSelectionModel().getSelectedItem();
        if(selectedFoodType == null){
            AlertUtility.displayError("Error selecting FoodType");
        }
        boolean confirmed = AlertUtility.displayConfirmation("Are you sure you want to delete this Food Type?");
        if(confirmed){
            Model.getInstance().deleteFoodType(selectedFoodType.getId());
            ObservableList<FoodType> foodTypes = food_types_table.getItems();
            foodTypes.remove(selectedFoodType);
            AlertUtility.displayConfirmation("FoodType deleted successfully");
        }

    }

    //meals table

    private void initMealsTableColumns() {
        meals_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        meals_col_calories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        meals_col_protein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        meals_col_carbs.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        meals_col_fats.setCellValueFactory(new PropertyValueFactory<>("fats"));
    }



    private void onDeleteMeal() {
        //converts to Meal
        Meal selectedMeal = (Meal) meals_table.getSelectionModel().getSelectedItem();
        if(selectedMeal == null){
            AlertUtility.displayError("Error selecting Meal");
        }
        boolean confirmed = AlertUtility.displayConfirmation("Are you sure you want to delete this Meal?");
        if(confirmed){
            Model.getInstance().deleteMeal(selectedMeal.getId());
            ObservableList<Meal> meals = meals_table.getItems();
            meals.remove(selectedMeal);
            AlertUtility.displayConfirmation("FoodType deleted successfully");
        }

    }

    private void loadMealData() {
        ObservableList<Meal> meals = Model.getInstance().getMeals();
        meals_table.setItems(meals);
    }



    private void onSort() {
        if(sort_comboBox.getValue()==null){
            AlertUtility.displayError("Select what to sort by first");
        } else {
            ObservableList<Meal> meals = meals_table.getItems();
            ObservableList<FoodType> foodTypes = food_types_table.getItems();

            String choice = sort_comboBox.getValue();
            switch (choice){
                case "Name":
                    FXCollections.sort(meals, Comparator.comparing(Meal::getName).reversed());
                    FXCollections.sort(foodTypes, Comparator.comparing(FoodType::getName).reversed());
                    break;
                case "Calories":
                    FXCollections.sort(meals, Comparator.comparingDouble(Meal::getCalories).reversed());
                    FXCollections.sort(foodTypes, Comparator.comparingDouble(FoodType::getCalories).reversed());
                    break;
                case "Protein":
                    FXCollections.sort(meals, Comparator.comparingDouble(Meal::getProtein).reversed());
                    FXCollections.sort(foodTypes, Comparator.comparingDouble(FoodType::getProtein).reversed());
                    break;
                case "Carbs":
                    FXCollections.sort(meals, Comparator.comparingDouble(Meal::getCarbs).reversed());
                    FXCollections.sort(foodTypes, Comparator.comparingDouble(FoodType::getCarbs).reversed());
                    break;
                case "Fats":
                    FXCollections.sort(meals, Comparator.comparingDouble(Meal::getFats).reversed());
                    FXCollections.sort(foodTypes, Comparator.comparingDouble(FoodType::getFats).reversed());
                    break;
                default:
                    meals = Model.getInstance().getMeals();
                    foodTypes = Model.getInstance().getFoodTypes();
                    break;
            }

            meals_table.setItems(meals);
            food_types_table.setItems(foodTypes);
        }
    }

    //==================================
    //Day tab
    //==================================

    String date = "";
    private void onChangeDate(){
        String year = year_field.getText();
        String month = month_field.getText();
        String day = day_field.getText();

        date = year+"-"+month+"-"+day;

        loadDayMealData();
        loadDayFoodItemData();
    }


    private void initDayMealsTableColumns() {
        day_meal_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        day_meal_cal_col.setCellValueFactory(new PropertyValueFactory<>("calories"));
        day_meal_protein_col.setCellValueFactory(new PropertyValueFactory<>("protein"));
        day_meal_carbs_col.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        day_meal_fats_col.setCellValueFactory(new PropertyValueFactory<>("fats"));
    }

    public void loadDayMealData() {
        ObservableList<Meal> dayMeals = Model.getInstance().getIdMeals(Model.getInstance().dayMealIds(date));
        day_meals_table.setItems(dayMeals);
    }


    private void initDayItemsTableColumns() {
        day_item_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        day_item_cal_col.setCellValueFactory(new PropertyValueFactory<>("calories"));
        day_item_protein_col.setCellValueFactory(new PropertyValueFactory<>("protein"));
        day_item_carbs_col.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        day_item_fats_col.setCellValueFactory(new PropertyValueFactory<>("fats"));
    }

    public void loadDayFoodItemData() {
        ObservableList<FoodItem> dayItems = Model.getInstance().getIdFoodItems(Model.getInstance().dayFoodItemIds(date));
        day_items_table.setItems(dayItems);
    }

    private void onDeleteDayMeal() {

        Meal selectedMeal = (Meal) day_meals_table.getSelectionModel().getSelectedItem();
        if(selectedMeal == null){
            AlertUtility.displayError("Error selecting Meal");
        } else {
            Model.getInstance().removeMealFromDay(selectedMeal.getId());
            loadDayMealData();
        }

    }

    private void onDeleteDayItem() {

        FoodItem selectedItem = (FoodItem) day_items_table.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            AlertUtility.displayError("Error selecting FoodItem");
        } else {
            Model.getInstance().removeFoodItemFromDay(selectedItem.getId());
            loadDayFoodItemData();
        }

    }

    private void onDaySort() {
        if(day_sort_comboBox.getValue()==null){
            AlertUtility.displayError("Select what to sort by first");
        } else {
            ObservableList<Meal> meals = day_meals_table.getItems();
            ObservableList<FoodItem> foodItems = day_items_table.getItems();

            String choice = day_sort_comboBox.getValue();
            switch (choice){
                case "Name":
                    FXCollections.sort(meals, Comparator.comparing(Meal::getName).reversed());
                    FXCollections.sort(foodItems, Comparator.comparing(FoodItem::getName).reversed());
                    break;
                case "Calories":
                    FXCollections.sort(meals, Comparator.comparingDouble(Meal::getCalories).reversed());
                    FXCollections.sort(foodItems, Comparator.comparingDouble(FoodItem::getCalories).reversed());
                    break;
                case "Protein":
                    FXCollections.sort(meals, Comparator.comparingDouble(Meal::getProtein).reversed());
                    FXCollections.sort(foodItems, Comparator.comparingDouble(FoodItem::getProtein).reversed());
                    break;
                case "Carbs":
                    FXCollections.sort(meals, Comparator.comparingDouble(Meal::getCarbs).reversed());
                    FXCollections.sort(foodItems, Comparator.comparingDouble(FoodItem::getCarbs).reversed());
                    break;
                case "Fats":
                    FXCollections.sort(meals, Comparator.comparingDouble(Meal::getFats).reversed());
                    FXCollections.sort(foodItems, Comparator.comparingDouble(FoodItem::getFats).reversed());
                    break;
                default:
                    meals = Model.getInstance().getIdMeals(Model.getInstance().dayMealIds(date));
                    foodItems = Model.getInstance().getIdFoodItems(Model.getInstance().dayFoodItemIds(date));
                    break;
            }

            day_meals_table.setItems(meals);
            day_items_table.setItems(foodItems);
        }
    }


}
