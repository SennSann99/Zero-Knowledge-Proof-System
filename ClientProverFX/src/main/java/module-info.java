module com.example.proverfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.clientproverfx to javafx.fxml;
    exports com.clientproverfx;
    exports com.prover;
    opens com.prover to javafx.fxml;
}