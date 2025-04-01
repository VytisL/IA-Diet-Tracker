package com.benedict.DietTracker.Controllers;
import com.benedict.DietTracker.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class RouteController implements Initializable {

    public BorderPane admin_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener(( observableValue, oldVal, newVal) -> {
            // Add switch statements
            switch (newVal){
                case ADD_FOOD_TYPE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAddFoodTypeView());
                case CLOSE_WINDOW -> admin_parent.setCenter(Model.getInstance().getViewFactory().returnToMainWindow());
                case ADD_FOOD_ITEM_INTO_DAY -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAddFoodItemIntoDayView());
                case CREATE_MEAL -> admin_parent.setCenter(Model.getInstance().getViewFactory().getCreateMealView());
                case ADD_MEAL_INTO_DAY -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAddMealIntoDayView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().returnToMainWindow());
            }
        });
    }
}
