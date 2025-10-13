module com.example.donacijefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    opens com.example.donacijefx to javafx.fxml;
    exports com.example.donacijefx;
}