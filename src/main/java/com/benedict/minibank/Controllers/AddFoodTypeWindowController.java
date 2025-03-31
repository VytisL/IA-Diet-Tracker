package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        //Add checks for all of these
        String name = food_type_name_field.getText();
        double calories = Double.parseDouble(calories_field.getText());
        //Add ability to initialize to 0 if not specified
        double protein = Double.parseDouble(protein_field.getText());
        double carbs = Double.parseDouble(carbs_field.getText());
        double fats = Double.parseDouble(fats_field.getText());
        Model.getInstance().createFoodType(name, calories, protein, carbs, fats);
        AlertUtility.displayConfirmation("Food Type created successfuly");
        emptyFields();
    }

    private void emptyFields() {
        food_type_name_field.setText(null);
        calories_field.setText(null);
        protein_field.setText(null);
        carbs_field.setText(null);
        fats_field.setText(null);
    }
}
