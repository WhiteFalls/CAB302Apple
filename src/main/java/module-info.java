module com.example.gardenplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gardenplanner to javafx.fxml;
    exports com.example.gardenplanner;
    exports com.example.gardenplanner.controller;
    opens com.example.gardenplanner.controller to javafx.fxml;
}