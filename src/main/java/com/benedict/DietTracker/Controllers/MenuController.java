package com.benedict.DietTracker.Controllers;

import com.benedict.DietTracker.Models.Model;
import com.benedict.DietTracker.Views.MenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    public Button create_client_btn;
    public Button clients_btn;
    public Button deposit_btn;
    public Button logout_btn;
    public Button authors_btn;
    public Text user_name;


    @Override
    public void initialize( URL url, ResourceBundle resourceBundle) {
        System.out.println("Userio info: " + Model.getInstance().getLoggedUserName());
        user_name.setText(Model.getInstance().getLoggedUserName());
        addListeners();
    }


    private void addListeners(){
        authors_btn.setOnAction(event->onAuthor());
        logout_btn.setOnAction(event->onLogout());
    }

    private void onAuthor(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.AUTHORS);
    }





    private void onLogout(){
        //Get stage
        Stage stage = (Stage) authors_btn.getScene().getWindow();
        //Close client window
        Model.getInstance().getViewFactory().closeStage(stage);
        //Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        //Set flag to false
        Model.getInstance().setClientAdminSuccessFlag(false);

    }


}
