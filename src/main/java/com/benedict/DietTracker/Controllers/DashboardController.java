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
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Button add_food_type_btn;
    @FXML private Button add_food_into_day_one_btn;
    @FXML private Button add_food_into_day_two_btn;
    @FXML private Button create_meal_btn;
    @FXML private Button add_meal_into_day_one_btn;
    @FXML private Button add_meal_into_day_two_btn;

    @FXML private Button sort_btn;
    @FXML private ComboBox<String> sort_comboBox;

    @FXML private ComboBox<String> day_sort_comboBox;
    @FXML private Button day_sort_btn;

    @FXML private DatePicker date_picker;
    @FXML private Button change_date_btn;

    @FXML private TableView<FoodType> food_types_table;
    @FXML private TableColumn<FoodType, String> types_col_name;
    @FXML private TableColumn<FoodType, String> types_col_calories;
    @FXML private TableColumn<FoodType, String> types_col_protein;
    @FXML private TableColumn<FoodType, String> types_col_carbs;
    @FXML private TableColumn<FoodType, String> types_col_fats;
    @FXML private MenuItem delete_foodType_btn;

    @FXML private TableView<Meal> meals_table;
    @FXML private TableColumn<Meal, String> meals_col_name;
    @FXML private TableColumn<Meal, String> meals_col_calories;
    @FXML private TableColumn<Meal, String> meals_col_protein;
    @FXML private TableColumn<Meal, String> meals_col_carbs;
    @FXML private TableColumn<Meal, String> meals_col_fats;
    @FXML private MenuItem delete_meal_btn;

    @FXML private TableView<Meal> day_meals_table;
    @FXML private TableColumn<Meal, String> day_meal_col;
    @FXML private TableColumn<Meal, String> day_meal_cal_col;
    @FXML private TableColumn<Meal, String> day_meal_protein_col;
    @FXML private TableColumn<Meal, String> day_meal_carbs_col;
    @FXML private TableColumn<Meal, String> day_meal_fats_col;
    @FXML private MenuItem delete_meal_day_btn;

    @FXML private TableView<FoodItem> day_items_table;
    @FXML private TableColumn<FoodItem, String> day_item_col;
    @FXML private TableColumn<FoodItem, String> day_item_cal_col;
    @FXML private TableColumn<FoodItem, String> day_item_protein_col;
    @FXML private TableColumn<FoodItem, String> day_item_carbs_col;
    @FXML private TableColumn<FoodItem, String> day_item_fats_col;
    @FXML private MenuItem delete_item_day_btn;

    @FXML private Label tot_cal;
    @FXML private Label tot_protein;
    @FXML private Label tot_carbs;
    @FXML private Label tot_fats;


    private String date = "";

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

        LocalDate today = LocalDate.now();
        date_picker.setValue(today);
        date = today.toString();
        onChangeDate();

        setSortComboBoxValues();
        initTypesTableColumns();
        setRowFactoryForFoodTypesTable();
        loadFoodTypeData();
        initMealsTableColumns();
        loadMealData();
        initDayMealsTableColumns();
        initDayItemsTableColumns();
    }

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

    private void editFoodType(FoodType foodType) {
        Optional<FoodType> result = DialogUtility.showEditFoodTypeDialog(foodType);
        result.ifPresent(updateFoodType -> {
            Model.getInstance().updateFoodType(foodType);
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
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    FoodType selectedFoodType = row.getItem();
                    editFoodType(selectedFoodType);
                }
            });
            return row;
        });
    }

    private void loadFoodTypeData() {
        ObservableList<FoodType> foodTypes = Model.getInstance().getFoodTypes();
        for(int i = 0; i < foodTypes.size(); i++){
            System.out.println(foodTypes.get(i).getName());
        }
        food_types_table.setItems(foodTypes);
    }

    private void onDeleteFoodType() {
        FoodType selectedFoodType = food_types_table.getSelectionModel().getSelectedItem();
        if (selectedFoodType == null) {
            AlertUtility.displayError("Error selecting FoodType");
            return;
        }
        boolean confirmed = AlertUtility.displayConfirmation("Are you sure you want to delete this Food Type?");
        if (confirmed) {
            Model.getInstance().deleteFoodType(selectedFoodType.getId());
            food_types_table.getItems().remove(selectedFoodType);
            AlertUtility.displayConfirmation("FoodType deleted successfully");
        }
    }

    private void initMealsTableColumns() {
        meals_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        meals_col_calories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        meals_col_protein.setCellValueFactory(new PropertyValueFactory<>("protein"));
        meals_col_carbs.setCellValueFactory(new PropertyValueFactory<>("carbs"));
        meals_col_fats.setCellValueFactory(new PropertyValueFactory<>("fats"));
    }

    private void onDeleteMeal() {
        Meal selectedMeal = meals_table.getSelectionModel().getSelectedItem();
        if (selectedMeal == null) {
            AlertUtility.displayError("Error selecting Meal");
            return;
        }
        boolean confirmed = AlertUtility.displayConfirmation("Are you sure you want to delete this Meal?");
        if (confirmed) {
            Model.getInstance().deleteMeal(selectedMeal.getId());
            meals_table.getItems().remove(selectedMeal);
            AlertUtility.displayConfirmation("Meal deleted successfully");
        }
    }

    private void loadMealData() {
        meals_table.setItems(Model.getInstance().getMeals());
    }

    private void onSort() {
        if (sort_comboBox.getValue() == null) {
            AlertUtility.displayError("Select what to sort by first");
            return;
        }
        String choice = sort_comboBox.getValue();
        FXCollections.sort(meals_table.getItems(), getMealComparator(choice));
        FXCollections.sort(food_types_table.getItems(), getFoodTypeComparator(choice));
    }

    private void onChangeDate(){
        LocalDate selectedDate = date_picker.getValue();
        if (selectedDate == null) {
            AlertUtility.displayError("Please select a date.");
            return;
        }
        date = selectedDate.toString();
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
        updateTotals();
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
        updateTotals();
    }

    private void onDeleteDayMeal() {
        Meal selectedMeal = day_meals_table.getSelectionModel().getSelectedItem();
        if (selectedMeal == null) {
            AlertUtility.displayError("Error selecting Meal");
            return;
        }
        Model.getInstance().removeMealFromDay(selectedMeal.getId());
        loadDayMealData();
    }

    private void onDeleteDayItem() {
        FoodItem selectedItem = day_items_table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertUtility.displayError("Error selecting FoodItem");
            return;
        }
        Model.getInstance().removeFoodItemFromDay(selectedItem.getId());
        loadDayFoodItemData();
    }

    private void onDaySort() {
        if (day_sort_comboBox.getValue() == null) {
            AlertUtility.displayError("Select what to sort by first");
            return;
        }
        String choice = day_sort_comboBox.getValue();
        FXCollections.sort(day_meals_table.getItems(), getMealComparator(choice));
        FXCollections.sort(day_items_table.getItems(), getFoodItemComparator(choice));
    }

    private Comparator<Meal> getMealComparator(String criteria) {
        return switch (criteria) {
            case "Name" -> Comparator.comparing(Meal::getName).reversed();
            case "Calories" -> Comparator.comparingDouble(Meal::getCalories).reversed();
            case "Protein" -> Comparator.comparingDouble(Meal::getProtein).reversed();
            case "Carbs" -> Comparator.comparingDouble(Meal::getCarbs).reversed();
            case "Fats" -> Comparator.comparingDouble(Meal::getFats).reversed();
            default -> (a, b) -> 0;
        };
    }

    private Comparator<FoodType> getFoodTypeComparator(String criteria) {
        return switch (criteria) {
            case "Name" -> Comparator.comparing(FoodType::getName).reversed();
            case "Calories" -> Comparator.comparingDouble(FoodType::getCalories).reversed();
            case "Protein" -> Comparator.comparingDouble(FoodType::getProtein).reversed();
            case "Carbs" -> Comparator.comparingDouble(FoodType::getCarbs).reversed();
            case "Fats" -> Comparator.comparingDouble(FoodType::getFats).reversed();
            default -> (a, b) -> 0;
        };
    }

    private Comparator<FoodItem> getFoodItemComparator(String criteria) {
        return switch (criteria) {
            case "Name" -> Comparator.comparing(FoodItem::getName).reversed();
            case "Calories" -> Comparator.comparingDouble(FoodItem::getCalories).reversed();
            case "Protein" -> Comparator.comparingDouble(FoodItem::getProtein).reversed();
            case "Carbs" -> Comparator.comparingDouble(FoodItem::getCarbs).reversed();
            case "Fats" -> Comparator.comparingDouble(FoodItem::getFats).reversed();
            default -> (a, b) -> 0;
        };
    }

    private void updateTotals(){
        double totCals = 0;
        double totProtein = 0;
        double totCarbs = 0;
        double totFats = 0;

        if(day_meals_table.getItems()!=null) {
            ObservableList<Meal> totMeals = day_meals_table.getItems();

            for (int i = 0; i < totMeals.size(); i++) {
                totCals += totMeals.get(i).getCalories();
                totProtein += totMeals.get(i).getProtein();
                totCarbs += totMeals.get(i).getCarbs();
                totFats += totMeals.get(i).getFats();
            }

        }
        if(day_items_table.getItems()!=null) {
            ObservableList<FoodItem> totFoodItems = day_items_table.getItems();
            for (int i = 0; i < totFoodItems.size(); i++) {
                totCals += totFoodItems.get(i).getCalories();
                totProtein += totFoodItems.get(i).getProtein();
                totCarbs += totFoodItems.get(i).getCarbs();
                totFats += totFoodItems.get(i).getFats();
            }
        }

        tot_cal.setText("Calories: " + totCals);
        tot_protein.setText("Protein: " + totProtein);
        tot_carbs.setText("Carbs: " + totCarbs);
        tot_fats.setText("Fats: " + totFats);
    }
}