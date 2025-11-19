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
    requires java.desktop;

    opens com.example.antrojiprogramavimopraktika to javafx.fxml;
    exports com.example.antrojiprogramavimopraktika;

    opens com.example.antrojiprogramavimopraktika.Controller to javafx.fxml;
    exports com.example.antrojiprogramavimopraktika.Controller;

    opens com.example.antrojiprogramavimopraktika.Controller.Admin to javafx.fxml;

    opens com.example.antrojiprogramavimopraktika.Entities to javafx.base;
    opens com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers to javafx.fxml;

    opens com.example.antrojiprogramavimopraktika.Controller.Teacher to javafx.fxml;

    opens com.example.antrojiprogramavimopraktika.Controller.Student to javafx.fxml;
}
