package com.benedict.DietTracker.Views;

import com.benedict.DietTracker.Controllers.RouteController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewFactory {
    private final ObjectProperty<MenuOptions> adminSelectedMenuItem;
    private Pane addFoodTypeView;
    private Pane addFoodItemIntoDayView;
    private Pane addMealIntoDayView;
    private Pane CreateMealView;
    private AnchorPane dashboard;


    public Pane getAddFoodTypeView() {
            try {
                addFoodTypeView = new FXMLLoader(getClass().getResource("/Fxml/AddFoodTypeWindow.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        return addFoodTypeView;
    }

    public Pane getAddFoodItemIntoDayView() {
            try {
                addFoodItemIntoDayView = new FXMLLoader(getClass().getResource("/Fxml/AddFoodItemIntoDayWindow.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        return addFoodItemIntoDayView;
    }

    public Pane getAddMealIntoDayView() {
            try {
                addMealIntoDayView = new FXMLLoader(getClass().getResource("/Fxml/AddMealIntoDayWindow.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        return addMealIntoDayView;
    }

    public Pane getCreateMealView() {
            try {
                CreateMealView = new FXMLLoader(getClass().getResource("/Fxml/CreateMealWindow.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        return CreateMealView;
    }


    public Pane returnToMainWindow() {
            try {
                dashboard = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            }catch (Exception e){
                System.out.println(e);
            }
        return dashboard;
    }


    public ViewFactory(){
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }



    public void showLoginWindow (){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }



    public void showRegisterWindow (){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Register.fxml"));
        createStage(loader);
    }

    public ObjectProperty<MenuOptions> getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }
    
    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        RouteController controller = new RouteController();
        loader.setController(controller);
        createStage(loader);
    }

    public void createStage(FXMLLoader loader){
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("DietTracker");
        stage.show();
    }

    public void closeStage(Stage stage){
        Platform.runLater(stage::close);
    }


}
