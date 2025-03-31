package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.FoodItem;
import com.benedict.minibank.Models.FoodType;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Views.MenuOptions;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CreateMealWindowController implements Initializable {
    @FXML
    public Button cancel_btn;
    @FXML
    public Button create_meal_btn;
    @FXML
    public ComboBox<FoodType> meal_select_food_type_box;
    @FXML
    public TextField meal_portion_field;
    @FXML
    public TextField meal_name_field;
    @FXML
    public Button save_meal_item_btn;

    //Add functionality to remove items from this table
    @FXML
    public TableView<FoodItem> meal_items_table;
    @FXML
    public TableColumn<FoodItem, String> meal_items_col_type;
    @FXML
    public TableColumn<FoodItem, String> meal_items_col_portion;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> onCancel());
        create_meal_btn.setOnAction(event -> onCreateMeal());
        save_meal_item_btn.setOnAction(event -> onAddMealItem());
        loadFoodTypeData();
        meal_select_food_type_box.setConverter(new StringConverter<FoodType>() {
            @Override
            public String toString(FoodType foodType) {
                return foodType.getName();
            }
            @Override
            public FoodType fromString(String string) {
                return null;
            }

        });
        initItemsTableColumns();
    }

    private void initItemsTableColumns() {

    meal_items_col_type.setCellValueFactory(cellData -> cellData.getValue().getFoodType().nameProperty());
    meal_items_col_portion.setCellValueFactory(cellData -> cellData.getValue().portionProperty().asString());

    }

    public void onCancel() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CLOSE_WINDOW);
    }


    //This is using foodItems as wrappers
    ObservableList<FoodItem> foodItems = FXCollections.observableArrayList();
    public void onAddMealItem() {
        if(meal_select_food_type_box.getItems()==null || meal_portion_field.getText()==null){
            AlertUtility.displayError("Selection is null");
            emptyFields();
        } else{
            SimpleObjectProperty<FoodType> foodType = new SimpleObjectProperty(meal_select_food_type_box.getValue());
            System.out.println(foodType.getName());
            double portion = Double.parseDouble(meal_portion_field.getText());
            FoodItem foodItem = new FoodItem(foodType, portion);
            foodItems.add(foodItem);
            meal_items_table.setItems(foodItems);
            emptyFields();
        }
    }

    private void loadFoodTypeData(){
        ObservableList<FoodType> foodTypes = Model.getInstance().getFoodTypes();
        meal_select_food_type_box.setItems(foodTypes);
    }

    private void emptyFields(){
        meal_select_food_type_box.setValue(null);
        meal_portion_field.setText(null);
    }
    public void onCreateMeal() {

        if(meal_name_field.getText()==null || foodItems.isEmpty()){
            AlertUtility.displayError("Selection is null");
        } else {
            ObservableList<FoodType> foodTypes = FXCollections.observableArrayList();
            ObservableList<Double> portions = FXCollections.observableArrayList();
            String name = meal_name_field.getText();

            for (int i = 0; i < foodItems.size(); i++) {
                foodTypes.add(foodItems.get(i).getFoodType());
                portions.add(foodItems.get(i).getPortion());
            }

            Model.getInstance().createMeal(name, foodTypes, portions);

        }

    }
}

