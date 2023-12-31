module com.example.clientverifierfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.clientverifierfx to javafx.fxml;
    exports com.clientverifierfx;
}