module com.benedict.minibank {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.commons;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;


    opens com.benedict.DietTracker to javafx.fxml;
    opens com.benedict.DietTracker.Controllers to javafx.fxml;
    exports com.benedict.DietTracker;
    exports com.benedict.DietTracker.Controllers;
    exports com.benedict.DietTracker.Models;
    exports com.benedict.DietTracker.Views;
    exports com.benedict.DietTracker.Services.dao;
}