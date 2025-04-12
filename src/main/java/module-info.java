module app.javacrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens app.javacrud to javafx.fxml;
    exports app.javacrud;
}