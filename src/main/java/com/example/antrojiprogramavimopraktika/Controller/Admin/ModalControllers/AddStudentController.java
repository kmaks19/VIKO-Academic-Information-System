package com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers;

import com.example.antrojiprogramavimopraktika.Controller.Admin.StudentsController;
import com.example.antrojiprogramavimopraktika.Files.UserValidator;
import com.example.antrojiprogramavimopraktika.Interfaces.IUserRepository;
import com.example.antrojiprogramavimopraktika.Repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public final class AddStudentController {
    public AddStudentController() {}

    private StudentsController parent;

    private final IUserRepository userRepo = new UserRepository();

    public void setParent(StudentsController parent) {
        this.parent = parent;
    }

    @FXML
    private TextField addstudent_firstname;

    @FXML
    private TextField addstudent_lastname;

    @FXML
    private DatePicker addstudent_birthDate;

    @FXML
    private TextField addstudent_email;

    @FXML
    private Label addstudent_errorlabel;

    @FXML
    private Button addstudent_confirmBtn;

    @FXML
    private Button addstudent_cancelBtn;

    public void initialize() {
        addstudent_cancelBtn.setOnAction(_ -> close());

        addstudent_confirmBtn.setOnAction(_ -> {
            if (save()) {
                if (parent != null) {
                    parent.loadStudents();
                }
                close();
            }
        });
    }
    private boolean save() {
        addstudent_errorlabel.setVisible(false);

        String firstName = addstudent_firstname.getText().trim();
        String lastName = addstudent_lastname.getText().trim();
        String email = addstudent_email.getText().trim();
        LocalDate birthDate = addstudent_birthDate.getValue();

        String error = UserValidator.validate(firstName, lastName, email, birthDate);

        if(error != null) {
            addstudent_errorlabel.setText(error);
            addstudent_errorlabel.setVisible(true);
            return false;
        }

        boolean exists = userRepo.userExists(firstName, lastName);

        if (exists) {
            addstudent_errorlabel.setText("Toks vartotojas jau egzistuoja!");
            addstudent_errorlabel.setVisible(true);
            return false;
        }
        return userRepo.addUser(firstName, lastName, email, birthDate, "student");
    }


    private void close() {
        ((Stage) addstudent_cancelBtn.getScene().getWindow()).close();
    }
}
