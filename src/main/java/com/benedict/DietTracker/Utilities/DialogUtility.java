package com.benedict.DietTracker.Utilities;

import com.benedict.DietTracker.Models.FoodType;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class DialogUtility {
    public static Optional<FoodType> showEditFoodTypeDialog(FoodType foodType){
        Dialog<FoodType> dialog = new Dialog();
        dialog.setTitle("Edit Food Type");
        dialog.setHeaderText("Edit selected Food Type");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        // Fields
        TextField nameField = new TextField(foodType.getName());
        TextField caloriesField = new TextField(String.valueOf(foodType.getCalories()));
        TextField proteinField = new TextField(String.valueOf(foodType.getProtein()));
        TextField carbsField = new TextField(String.valueOf(foodType.getCarbs()));
        TextField fatsField = new TextField(String.valueOf(foodType.getCarbs()));

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Calories:"), 0, 1);
        grid.add(caloriesField, 1, 1);
        grid.add(new Label("Protein:"), 0, 2);
        grid.add(proteinField, 1, 2);
        grid.add(new Label("Carbs:"), 0, 3);
        grid.add(carbsField, 1, 3);
        grid.add(new Label("Fats:"), 0, 4);
        grid.add(fatsField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            try {
                if (dialogButton == saveButtonType) {
                    foodType.setName(nameField.getText().trim());
                    foodType.setCalories(Double.parseDouble(caloriesField.getText().trim()));
                    foodType.setProtein(Double.parseDouble(proteinField.getText().trim()));
                    foodType.setCarbs(Double.parseDouble(carbsField.getText().trim()));
                    foodType.setFats(Double.parseDouble(fatsField.getText().trim()));
                }
                return foodType;
            }catch (Exception E) {
                AlertUtility.displayError("Invalid data");
                return foodType;
            }

        });

        return dialog.showAndWait();
    }
}
