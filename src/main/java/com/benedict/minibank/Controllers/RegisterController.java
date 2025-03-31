package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Utilities.AlertUtility;
import com.benedict.minibank.Utilities.UserAuthUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField user_name_fld;
    public Label password_lbl;
    public PasswordField password_fld;
    public PasswordField repeat_password_fld;
    public Button register_btn;
    public Label password_lbl1;
    public Label error_lbl;
    public Hyperlink login_link;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        register_btn.setOnAction(actionEvent -> onRegister());
        login_link.setOnAction(actionEvent ->onLogin());
    }

    public void onRegister() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        //Check  if user exist
        if(Model.getInstance().isUserAlreadyRegistered(user_name_fld.getText())){
            AlertUtility.displayError("Vartotojas, tokiu vardu sistemoje jau registruotas");
            emptyFields();
        }else if(!UserAuthUtils.doPasswordsMatch(password_fld.getText(),repeat_password_fld.getText())){
            AlertUtility.displayError("Nesutampa slaptažodžiai");
        }
        else{
            Model.getInstance().createUser(user_name_fld.getText(),password_fld.getText());
            AlertUtility.displayInformation("Vartotojas sėkmingai užregistruotas, galite prisijungti");
            Model.getInstance().getViewFactory().showLoginWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }
    }

    public void onLogin(){
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        Model.getInstance().getViewFactory().showLoginWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    public void emptyFields(){
        user_name_fld.setText("");
        password_fld.setText("");
    }
}
