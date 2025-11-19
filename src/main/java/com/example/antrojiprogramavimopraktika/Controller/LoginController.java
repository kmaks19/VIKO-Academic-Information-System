package com.example.antrojiprogramavimopraktika.Controller;

import com.example.antrojiprogramavimopraktika.Entities.Person;
import com.example.antrojiprogramavimopraktika.Entities.Session;
import com.example.antrojiprogramavimopraktika.Files.LoginManager;
import com.example.antrojiprogramavimopraktika.Files.LoginValidator;
import com.example.antrojiprogramavimopraktika.Files.ValidationResult;
import com.example.antrojiprogramavimopraktika.Utils.UIErrors;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField login_username;

    @FXML
    private TextField login_password;

    @FXML
    private Button login_submitBtn;

    @FXML
    private Label login_wronguserid;

    @FXML
    private Label login_wrongpsw;

    private final LoginValidator validator;
    private final LoginManager loginManager;

    public LoginController() {
        this.validator = new LoginValidator();
        this.loginManager = new LoginManager();
    }

    @FXML
    private void handleLogin() throws IOException {
        UIErrors.hideLabelError(login_wronguserid);
        UIErrors.hideLabelError(login_wrongpsw);

        String usernameInput = login_username.getText().trim();
        String passwordInput = login_password.getText().trim();

        ValidationResult userIDResult = validator.validateUserID(usernameInput);
        ValidationResult passwordResult = validator.validatePassword(passwordInput);

        boolean valid = true;

        if (userIDResult.isInvalid()) {
            UIErrors.showLabelError(login_wronguserid, userIDResult.getErrorMessage());
            valid = false;
        }

        if (passwordResult.isInvalid()) {
            UIErrors.showLabelError(login_wrongpsw, passwordResult.getErrorMessage());
            valid = false;
        }

        if (!valid) return;

        Person user = loginManager.login(usernameInput, passwordInput);

        if (user == null) {
            UIErrors.showLabelError(login_wronguserid, "Neteisingi prisijungimo duomenys");
            UIErrors.showLabelError(login_wrongpsw, "Neteisingi prisijungimo duomenys");
            return;
        }

        Session.getInstance().setCurrentUser(user);

        switch (user.getRole()) {
            case "admin": {
                MainController.ShowAdminPanel(
                        (Stage) login_submitBtn.getScene().getWindow()
                );
                break;
            }
            case "teacher": {
                MainController.ShowTeacherPanel(
                        (Stage) login_submitBtn.getScene().getWindow()
                );
                break;
            }
            case "student": {
                MainController.ShowStudentPanel(
                        (Stage) login_submitBtn.getScene().getWindow()
                );
            }
        }
    }
}
