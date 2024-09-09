module com.example.gardenplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.gardenplanner to javafx.fxml;
    exports com.example.gardenplanner;
}