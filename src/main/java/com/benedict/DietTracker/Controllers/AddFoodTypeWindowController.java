package com.benedict.DietTracker.Controllers;

import com.benedict.DietTracker.Models.Model;
import com.benedict.DietTracker.Utilities.AlertUtility;
import com.benedict.DietTracker.Views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddFoodTypeWindowController implements Initializable {
    @FXML
    public Button cancel_btn;
    public TextField food_type_name_field;
    public TextField calories_field;
    public TextField protein_field;
    public TextField carbs_field;
    public TextField fats_field;
    public Button add_food_type_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> onCancel());
        add_food_type_btn.setOnAction(event -> onFoodType());
    }

    public void onCancel() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CLOSE_WINDOW);
    }

    private void onFoodType() {
        if (food_type_name_field.getText().isEmpty()) {
            AlertUtility.displayError("Please enter a food type name");
        } else {
            String name = food_type_name_field.getText();

            double calories = 0;
            double protein = 0;
            double carbs = 0;
            double fats = 0;

            try {
                String caloriesS = calories_field.getText();
                String proteinS = protein_field.getText();
                String carbsS = carbs_field.getText();
                String fatsS = fats_field.getText();
                if (!caloriesS.isEmpty()) {
                    calories = Double.parseDouble(calories_field.getText());
                }
                if (!proteinS.isEmpty()) {
                    protein = Double.parseDouble(protein_field.getText());
                }
                if (!carbsS.isEmpty()) {
                    carbs = Double.parseDouble(carbs_field.getText());
                }
                if (!fatsS.isEmpty()) {
                    fats = Double.parseDouble(fats_field.getText());
                }
                Model.getInstance().createFoodType(name, calories, protein, carbs, fats);
                AlertUtility.displayConfirmation("Food Type created successfuly");
                emptyFields();
            } catch (Exception E) {
                AlertUtility.displayError("Invalid data");
                emptyFields();
            }
        }
    }


    private void emptyFields() {
        food_type_name_field.setText(null);
        calories_field.setText(null);
        protein_field.setText(null);
        carbs_field.setText(null);
        fats_field.setText(null);
    }
}
