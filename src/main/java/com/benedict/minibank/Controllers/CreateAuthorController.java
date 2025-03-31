package com.benedict.minibank.Controllers;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Utilities.AlertUtility;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateAuthorController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField email_fld;
    public PasswordField city_fld;
    public Button create_author_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_author_btn.setOnAction(event -> onAuthor());
    }

    private void onAuthor() {
        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String email = email_fld.getText();
        String city  = city_fld.getText();
        Model.getInstance().createAuthor(fName, lName, email, city);
        emptyFields();
        AlertUtility.displayInformation("Autorius išsaugotas sėkmingai");
    }

    private void emptyFields() {
        fName_fld.setText("");
        lName_fld.setText("");
        email_fld.setText("");
        city_fld.setText("");
    }
}
