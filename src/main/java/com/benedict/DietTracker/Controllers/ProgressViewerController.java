package com.benedict.DietTracker.Controllers;

import com.benedict.DietTracker.Models.Model;
import com.benedict.DietTracker.Views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgressViewerController implements Initializable {
    @FXML
    public Button cancel_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel_btn.setOnAction(event -> onCancel());
    }

    public void onCancel() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CLOSE_WINDOW);
    }
}
