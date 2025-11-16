package com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers;

import com.example.antrojiprogramavimopraktika.Controller.Admin.TeachersController;
import com.example.antrojiprogramavimopraktika.Files.UserValidator;
import com.example.antrojiprogramavimopraktika.Interfaces.IUserRepository;
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

    private final IUserRepository userRepository = new UserRepository();

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

        String error = UserValidator.validate(firstName, lastName, email, birthDate);

        if(error != null) {
            teacheradd_errorlabel.setText(error);
            teacheradd_errorlabel.setVisible(true);
            return false;
        }

        boolean exists = userRepository.userExists(firstName, lastName);

        if (exists) {
            teacheradd_errorlabel.setText("Toks vartotojas jau egzistuoja!");
            teacheradd_errorlabel.setVisible(true);
            return false;
        }

        return userRepository.addUser(firstName, lastName, email, birthDate, "teacher");
    }

    private void close() {
        ((Stage) addteacher_cancelBtn.getScene().getWindow()).close();
    }
}
