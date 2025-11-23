package com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers;

import com.example.antrojiprogramavimopraktika.Controller.Admin.SubjectsController;
import com.example.antrojiprogramavimopraktika.Interfaces.ISubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.SubjectRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class AddSubjectController {
    public AddSubjectController () {}

    private SubjectsController parent;

    public void setParent(SubjectsController parent) {
        this.parent = parent;
    }

    private final ISubjectRepository subjectRepo = new SubjectRepository();

    @FXML
    private TextField addstudysubject_input;

    @FXML
    private Label subjectadd_errorLabel;

    @FXML
    private Button addstudysubject_addBtn;

    @FXML
    private Button addstudysubject_cancelBtn;

    public void initialize() {
        addstudysubject_cancelBtn.setOnAction(_ -> close());

        addstudysubject_addBtn.setOnAction(_ -> {
            if(save()) {
                if(parent != null) {
                    parent.loadSubjects();
                }
                close();
            }
        });
    }

    private boolean save() {
        subjectadd_errorLabel.setVisible(false);

        String subjectName = addstudysubject_input.getText().trim();

        if(subjectName.isEmpty()) {
            subjectadd_errorLabel.setText("Privalote įvesti dalyko pavadinimą!");
            subjectadd_errorLabel.setVisible(true);
            return false;
        }
        if(subjectRepo.getSubjectByName(subjectName)) {
            subjectadd_errorLabel.setText("Toks dalykas jau egzistuoja!");
            subjectadd_errorLabel.setVisible(true);
            return false;
        }

        boolean success = subjectRepo.addSubject(subjectName);

        if(!success) {
            subjectadd_errorLabel.setText("Nepavyko pridėti dalyko!");
            subjectadd_errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private void close() {
        ((Stage) addstudysubject_cancelBtn.getScene().getWindow()).close();
    }
}
