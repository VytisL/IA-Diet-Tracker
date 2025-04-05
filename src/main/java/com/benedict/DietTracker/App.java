package com.benedict.DietTracker;

import com.benedict.DietTracker.Models.Model;
import com.benedict.DietTracker.Utilities.AlertUtility;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {


    @Override
    public void start( Stage stage){
        if (Model.getInstance().hasRegisteredUsers()){
            Model.getInstance().getViewFactory().showLoginWindow();
        } else {
            AlertUtility.displayInformation("You must register at least one user");
            Model.getInstance().getViewFactory().showRegisterWindow();

        }


    }
}
