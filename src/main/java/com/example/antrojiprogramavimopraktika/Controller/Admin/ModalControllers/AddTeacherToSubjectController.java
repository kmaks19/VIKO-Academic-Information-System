package com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers;

import com.example.antrojiprogramavimopraktika.Controller.Admin.TeachersController;
import com.example.antrojiprogramavimopraktika.Entities.Subject;
import com.example.antrojiprogramavimopraktika.Entities.Teacher;
import com.example.antrojiprogramavimopraktika.Interfaces.ISubjectRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.ITeacherRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.ITeacherSubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.SubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.TeacherRepository;
import com.example.antrojiprogramavimopraktika.Repositories.TeacherSubjectRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public final class AddTeacherToSubjectController {
    public AddTeacherToSubjectController() {}

    private final ITeacherSubjectRepository tsRepository = new TeacherSubjectRepository();
    private final ITeacherRepository tRepository = new TeacherRepository();
    private final ISubjectRepository sRepository = new SubjectRepository();

    private TeachersController parent;

    @FXML
    private ChoiceBox<Subject> teachersubject_selectSubjectBtn;

    @FXML
    private ChoiceBox<Teacher> teachersubject_selectTeacherBtn;

    @FXML
    private Label addteachersubject_errorLabel;

    @FXML
    private Button addteachersubject_submitBtn;

    @FXML
    private Button addteachersubject_cancelBtn;

    public void setParent(TeachersController parent) {
        this.parent = parent;
    }

    public void initialize() {
        addteachersubject_cancelBtn.setOnAction(_ -> close());

        loadTeachers();

        teachersubject_selectTeacherBtn.getSelectionModel().selectedItemProperty().addListener((_, _, newTeacher) -> {
            if(newTeacher != null) {
                loadUnassignedSubjects();
            }
        });

        addteachersubject_submitBtn.setOnAction(_ -> {
            if(save()) {
                if(parent != null) {
                    parent.loadTeacherSubjects();
                }
                close();
            }
        });
    }

    private void loadTeachers() {
        List<Teacher> teachers = tRepository.getAllTeachers();
        teachersubject_selectTeacherBtn.setItems(FXCollections.observableArrayList(teachers));

        teachersubject_selectTeacherBtn.setConverter(new StringConverter<>() {
            @Override
            public String toString(Teacher t) {
                return t == null ? "" : t.getFirstName() + " " + t.getLastName();
            }

            @Override
            public Teacher fromString(String s) {
                return null;
            }
        });
    }

    private void loadUnassignedSubjects() {
        List<Subject> subjects = sRepository.getAllUnassignedSubjects();

        teachersubject_selectSubjectBtn.setItems(FXCollections.observableArrayList(subjects));
    }

    private boolean save() {
        addteachersubject_errorLabel.setVisible(false);

        Teacher teacher = teachersubject_selectTeacherBtn.getValue();
        Subject subject = teachersubject_selectSubjectBtn.getValue();

        if (teacher == null) {
            addteachersubject_errorLabel.setText("Privalote pasirinkti dėstytoją");
            addteachersubject_errorLabel.setVisible(true);
            return false;
        }
        if(subject == null) {
            addteachersubject_errorLabel.setText("Privalote pasirinkti dalyką");
            addteachersubject_errorLabel.setVisible(true);
            return false;
        }

        boolean success = tsRepository.addTeacherSubject(teacher.getID(), subject.getID());

        if(!success) {
            addteachersubject_errorLabel.setText("Nepavyko priskirti dėstytojo");
            addteachersubject_errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private void close() {
        ((Stage) addteachersubject_cancelBtn.getScene().getWindow()).close();
    }
}
