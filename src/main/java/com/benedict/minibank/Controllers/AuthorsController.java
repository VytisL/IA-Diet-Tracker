package com.benedict.minibank.Controllers;

import com.benedict.minibank.Models.Author;
import com.benedict.minibank.Models.Model;
import com.benedict.minibank.Views.MenuOptions;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorsController implements Initializable {

    @FXML
    public Button add_author;

    @FXML
    public TableView<Author> authors_table;

    @FXML
    private TableColumn<Author, String> colFirstName;

    @FXML
    private TableColumn<Author, String> colLastName;

    @FXML
    private TableColumn<Author, String> colEmail;

    @FXML
    private TableColumn<Author, String> colCity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableColumns(); // Initialize table columns
       loadAuthorData();   // Load data into the table
        Model.getInstance().getAuthors();

        add_author.setOnAction(event -> onCreateAuthor());
    }

    private void onCreateAuthor() {
        // Change the menu to the create author screen
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(MenuOptions.CREATE_AUTHOR);
    }

    private void loadAuthorData() {
        // Fetch authors from the model and add them to the table
        ObservableList<Author> authors = Model.getInstance().getAuthors();
        authors_table.setItems(authors);
    }

    private void initTableColumns() {
        // Map TableColumns to Author class properties
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
    }
}
