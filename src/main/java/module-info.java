module com.example.gardenplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.testng;
    requires java.desktop;
    requires java.sql;
    requires org.junit.jupiter.api;


    opens com.example.gardenplanner to javafx.fxml;
    exports com.example.gardenplanner;
    exports com.example.gardenplanner.controller;
    opens com.example.gardenplanner.controller to javafx.fxml;
    exports People;
    opens People to javafx.fxml;
    exports Database;
    opens Database to javafx.fxml;
}