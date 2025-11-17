package com.example.antrojiprogramavimopraktika.Controller.Admin;

import com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers.AssignSubjectToGroupController;
import com.example.antrojiprogramavimopraktika.Entities.Group;
import com.example.antrojiprogramavimopraktika.Entities.GroupSubject;
import com.example.antrojiprogramavimopraktika.Entities.Subject;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupSubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GroupSubjectRepository;
import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public final class SubjectsController {

    private final IGroupSubjectRepository gsRepo = new GroupSubjectRepository();

    @FXML
    private TableView<GroupSubject> subjectsTable;

    @FXML
    private TableColumn<GroupSubject, Integer> subjectsassign_id;

    @FXML
    private TableColumn<GroupSubject, String> subjectsassign_subjectname;

    @FXML
    private TableColumn<GroupSubject, String> subjectsassign_groupname;

    @FXML
    private Button subjectsassignBtn;

    @FXML
    private Button subjectsremoveBtn;

    @FXML
    private ChoiceBox<Group> subjectassign_filterGroupChoiceBtn;

    @FXML
    private Button subjectassign_filterGroupSubmitBtn;

    @FXML
    private ChoiceBox<Subject> subjectassign_filterSubjectChoiceBtn;

    @FXML
    private Button subjectassign_filterSubjectSubmitBtn;

    public void initialize() {
        subjectsassign_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        subjectsassign_subjectname.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getSubject().getName())
        );
        subjectsassign_groupname.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getGroup().getName())
        );

        loadSubjects();

        subjectsassignBtn.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Admin/Modals/AssignSubjectToGroupModal.fxml",
                        "Grupės priskyrimas prie dalyko",
                        modalController -> ((AssignSubjectToGroupController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        subjectsremoveBtn.setOnAction(_ -> {
            GroupSubject selected = subjectsTable.getSelectionModel().getSelectedItem();

            if(selected == null) return;

            gsRepo.removeGroupSubject(selected.getID());

            loadSubjects();
        });
    }


    public void loadSubjects() {
        List<GroupSubject> list = gsRepo.getGroupSubjects();
        subjectsTable.setItems(FXCollections.observableArrayList(list));
    }

}
