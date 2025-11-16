package com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers;

import com.example.antrojiprogramavimopraktika.Controller.Admin.StudentsController;
import com.example.antrojiprogramavimopraktika.Entities.Group;
import com.example.antrojiprogramavimopraktika.Entities.Student;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.IStudentGroupRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GroupRepository;
import com.example.antrojiprogramavimopraktika.Repositories.StudentGroupRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public final class AssignStudentToGroupController {
    public AssignStudentToGroupController() {}

    private StudentsController parent;

    private final IGroupRepository groupRepo = new GroupRepository();

    private final IStudentGroupRepository sgroupRepo = new StudentGroupRepository();

    public void setParent(StudentsController parent) {
        this.parent = parent;
    }

    @FXML
    private ChoiceBox<Student> assignstudent_selectstudentBtn;

    @FXML
    private ChoiceBox<Group> assignstudent_selectgroupBtn;

    @FXML
    private Button assignstudent_submitBtn;

    @FXML
    private Button assignstudent_cancelBtn;

    @FXML
    private Label assignstudent_errorLabel;

    public void initialize() {
        assignstudent_cancelBtn.setOnAction(_ -> close());

        loadStudents();

        assignstudent_selectstudentBtn.getSelectionModel().selectedItemProperty().addListener((_, _, newStudent) -> {
            if(newStudent != null) {
                loadGroups();
            }
        });

        assignstudent_submitBtn.setOnAction(_ -> {
            if(save()) {
                if(parent != null) {
                    parent.loadStudentGroups();
                }
                close();
            }
        });
    }

    private void loadStudents() {
        List<Student> students = sgroupRepo.getStudentsWithoutGroup();
        assignstudent_selectstudentBtn.setItems(FXCollections.observableArrayList(students));

        assignstudent_selectstudentBtn.setConverter(new StringConverter<>() {
            @Override
            public String toString(Student s) {
                return s == null ? "" : s.getFullName();
            }

            @Override
            public Student fromString(String s) {
                return null;
            }
        });
    }

    private boolean save() {
        assignstudent_errorLabel.setVisible(false);

        Student selectedStudent = assignstudent_selectstudentBtn.getValue();
        Group selectedGroup = assignstudent_selectgroupBtn.getValue();

        if(selectedStudent == null) {
            assignstudent_errorLabel.setText("Privalote pasirinkti studentą");
            assignstudent_errorLabel.setVisible(true);
            return false;
        }

        if(selectedGroup == null) {
            assignstudent_errorLabel.setText("Privalote pasirinkti grupę");
            assignstudent_errorLabel.setVisible(true);
            return false;
        }

        boolean success = sgroupRepo.assignStudentToGroup(selectedStudent.getUserID(), selectedGroup.getId());

        if(!success) {
            assignstudent_errorLabel.setText("Nepavyko priskirti studento prie grupės");
            assignstudent_errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private void loadGroups() {
        List<Group> groups = groupRepo.getGroups();
        assignstudent_selectgroupBtn.setItems(FXCollections.observableArrayList(groups));
    }

    private void close() {
        ((Stage) assignstudent_cancelBtn.getScene().getWindow()).close();
    }
}
