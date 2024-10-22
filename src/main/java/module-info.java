module com.example.gardenplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.testng;
    requires java.desktop;
    requires org.apache.commons.compress;
    requires commons.lang;
    requires org.bouncycastle.provider;
    requires java.sql;


    opens com.gardenapplication to javafx.fxml;
    exports com.gardenapplication;
    exports com.gardenapplication.controller;
    opens com.gardenapplication.controller to javafx.fxml;
    exports People;
    opens People to javafx.fxml;
    exports Tasks;
    opens Tasks to javafx.fxml;
    exports Database;
    opens Database to javafx.fxml;
    exports GardenCell;
    opens GardenCell to javafx.fxml;
}