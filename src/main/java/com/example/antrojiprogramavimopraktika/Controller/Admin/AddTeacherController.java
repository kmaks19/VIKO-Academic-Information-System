package com.example.antrojiprogramavimopraktika.Controller.Admin;

import com.example.antrojiprogramavimopraktika.Repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public final class AddTeacherController {
    public AddTeacherController() {}

    private final UserRepository userRepository = new UserRepository();

    private TeachersController parent;

    public void setParent(TeachersController parent) {
        this.parent = parent;
    }

    @FXML
    private TextField addteacher_firstname;

    @FXML
    private TextField addteacher_lastname;

    @FXML
    private DatePicker addteacher_birthdate;

    @FXML
    private TextField addteacher_email;

    @FXML
    private Button addteacher_submitBtn;

    @FXML
    private Button addteacher_cancelBtn;

    @FXML
    private Label teacheradd_errorlabel;

    public void initialize() {
        addteacher_cancelBtn.setOnAction(_ -> close());

        addteacher_submitBtn.setOnAction(_ -> {
            if (save()) {
                if (parent != null) {
                    parent.loadTeachers();
                }
                close();
            }
        });
    }

    private boolean save() {
        teacheradd_errorlabel.setVisible(false);

        String firstName = addteacher_firstname.getText().trim();
        String lastName = addteacher_lastname.getText().trim();
        String email = addteacher_email.getText().trim();
        LocalDate birthDate = addteacher_birthdate.getValue();

        if(firstName.isEmpty()) {
            teacheradd_errorlabel.setText("Vardas negali būti tuščias");
            teacheradd_errorlabel.setVisible(true);
            return false;
        }

        if(lastName.isEmpty()) {
            teacheradd_errorlabel.setText("Pavardė negali būti tuščia");
            teacheradd_errorlabel.setVisible(true);
            return false;
        }

        if(email.isEmpty()) {
            teacheradd_errorlabel.setText("El. paštas negali būti tuščias");
            teacheradd_errorlabel.setVisible(true);
            return false;
        }

        if(birthDate == null) {
            teacheradd_errorlabel.setText("Privalote pasirinkti gimimo datą");
            teacheradd_errorlabel.setVisible(true);
            return false;
        }
        if(birthDate.isAfter(LocalDate.now())) {
            teacheradd_errorlabel.setText("Gimimo data negali būti vėlesnė nei šiandien");
            teacheradd_errorlabel.setVisible(true);
            return false;
        }
        return userRepository.addUser(firstName, lastName, email, birthDate, "teacher");
    }

    private void close() {
        ((Stage) addteacher_cancelBtn.getScene().getWindow()).close();
    }
}
