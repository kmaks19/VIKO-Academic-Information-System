package com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers;

import com.example.antrojiprogramavimopraktika.Controller.Admin.SubjectsController;
import com.example.antrojiprogramavimopraktika.Entities.Group;
import com.example.antrojiprogramavimopraktika.Entities.Subject;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupSubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GroupRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GroupSubjectRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public final class AssignSubjectToGroupController {
    public AssignSubjectToGroupController() {}

    private final IGroupSubjectRepository gsRepo = new GroupSubjectRepository();
    private final IGroupRepository groupRepo = new GroupRepository();


    private SubjectsController parent;

    public void setParent(SubjectsController parent) {
        this.parent = parent;
    }

    @FXML
    private ChoiceBox<Group> subjectassigntogroup_subjectGroupSelectBtn;

    @FXML
    private ChoiceBox<Subject> subjectassigntogroup_subjectSelectBtn;

    @FXML
    private Label subjectassigntogroup_errorLabel;

    @FXML
    private Button subjectassigntogroup_submitBtn;

    @FXML
    private Button subjectassigntogroup_cancelBtn;

    public void initialize() {
        subjectassigntogroup_cancelBtn.setOnAction(_ -> close());

        loadGroups();

        subjectassigntogroup_subjectGroupSelectBtn.getSelectionModel().selectedItemProperty().addListener((_, _, newSubject) -> {
            if(newSubject != null) {
                loadSubjects(newSubject.getId());
            }
        });

        subjectassigntogroup_submitBtn.setOnAction(_ -> {
            if(save()) {
                if(parent != null) {
                    parent.loadSubjects();
                }
                close();
            }
        });
    }


    private void loadSubjects(int groupID) {
        List<Subject> subjects = gsRepo.getUnassignedSubjectsForGroup(groupID);
        subjectassigntogroup_subjectSelectBtn.setItems(FXCollections.observableArrayList(subjects));
    }

    private void loadGroups() {
        List<Group> groups = groupRepo.getGroups();
        subjectassigntogroup_subjectGroupSelectBtn.setItems(FXCollections.observableArrayList(groups));
    }

    private boolean save() {
        subjectassigntogroup_errorLabel.setVisible(false);

        Group selectedGroup = subjectassigntogroup_subjectGroupSelectBtn.getValue();
        Subject selectedSubject = subjectassigntogroup_subjectSelectBtn.getValue();

        if(selectedGroup == null) {
            subjectassigntogroup_errorLabel.setText("Privalote pasirinkti grupę");
            subjectassigntogroup_errorLabel.setVisible(true);
            return false;
        }

        if(selectedSubject == null) {
            subjectassigntogroup_errorLabel.setText("Privalote pasirinkti dalyką");
            subjectassigntogroup_errorLabel.setVisible(true);
            return false;
        }

        boolean success = gsRepo.addGroupSubject(selectedGroup.getId(), selectedSubject.getId());

        if(!success) {
            subjectassigntogroup_errorLabel.setText("Nepavyko priskirti grupės prie dalyko");
            subjectassigntogroup_errorLabel.setVisible(true);
            return false;
        }
        return true;
    }

    private void close() {
        ((Stage) subjectassigntogroup_cancelBtn.getScene().getWindow()).close();
    }


}
