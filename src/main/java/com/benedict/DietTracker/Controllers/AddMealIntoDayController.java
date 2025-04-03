package com.benedict.DietTracker.Controllers;

import com.benedict.DietTracker.Models.FoodType;
import com.benedict.DietTracker.Models.Meal;
import com.benedict.DietTracker.Models.Model;
import com.benedict.DietTracker.Utilities.AlertUtility;
import com.benedict.DietTracker.Views.MenuOptions;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMealIntoDayController implements Initializable {
    @FXML
    public Button cancel_btn;
    @FXML
    public Button add_meal_btn;
    @FXML
    public ComboBox<Meal> meal_comboBox;
    @FXML
    public TextField date_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> onCancel());
        add_meal_btn.setOnAction(event -> onAddMeal());

        meal_comboBox.setConverter(new StringConverter<Meal>() {
            @Override
            public String toString(Meal meal) {
                return meal.getName();
            }
            @Override
            public Meal fromString(String string) {
                return null;
            }

        });
        loadMealData();
    }

    public void onCancel() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CLOSE_WINDOW);
    }

    private void loadMealData(){
        ObservableList<Meal> meals = Model.getInstance().getMeals();
       meal_comboBox.setItems(meals);
    }

    private void onAddMeal(){
        if (meal_comboBox.getValue()==null || date_field.getText()==null || date_field.getText().trim().isEmpty()){
            AlertUtility.displayError("Please select valid data");
        } else{
            int meal_id = meal_comboBox.getValue().getId();
            String date = date_field.getText().trim();
            Model.getInstance().createDay(date, meal_id, -1);
            emptyFields();
        }
    }

    private void emptyFields(){
        meal_comboBox.setValue(null);
        date_field.setText(null);
    }
}
