package com.benedict.DietTracker.Controllers;

import com.benedict.DietTracker.Models.FoodType;
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

public class AddFoodItemIntoDayController implements Initializable {
    @FXML
    public Button cancel_btn;
    @FXML
    public ComboBox<FoodType> food_type_combo_box;
    @FXML
    public TextField portion_field;
    @FXML
    public Button add_food_item_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> onCancel());
        add_food_item_btn.setOnAction(event -> onFoodItem());
        loadFoodTypeData();
        food_type_combo_box.setConverter(new StringConverter<FoodType>() {
            @Override
            public String toString(FoodType foodType) {
                return foodType.getName();
            }
            @Override
            public FoodType fromString(String string) {
                return null;
            }

        });
    }



    public void onCancel() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CLOSE_WINDOW);
    }

    private void onFoodItem(){
        //This will create duplicate FoodItems in the DB each time a user wants to add a FoodItem. Fix later.
        FoodType foodType = food_type_combo_box.getValue();
        double portion = Double.parseDouble(portion_field.getText());
        Model.getInstance().createFoodItem(foodType, portion);
        AlertUtility.displayConfirmation("Food Item created successfuly");
        emptyFields();

        //Add the part where this is added to the day table.
    }

    private void loadFoodTypeData(){
        ObservableList<FoodType> foodTypes = Model.getInstance().getFoodTypes();
        food_type_combo_box.setItems(foodTypes);
    }

    private void emptyFields() {
        portion_field.setText(null);
        food_type_combo_box.setValue(null);
    }
}
