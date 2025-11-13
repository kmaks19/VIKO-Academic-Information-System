module com.example.antrojiprogramavimopraktika {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.antrojiprogramavimopraktika to javafx.fxml;
    exports com.example.antrojiprogramavimopraktika;

    opens com.example.antrojiprogramavimopraktika.Controller to javafx.fxml;
    exports com.example.antrojiprogramavimopraktika.Controller;
}
