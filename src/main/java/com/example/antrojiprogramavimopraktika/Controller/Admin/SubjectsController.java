package com.example.antrojiprogramavimopraktika.Controller.Admin;

import com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers.AddSubjectController;
import com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers.AssignSubjectToGroupController;
import com.example.antrojiprogramavimopraktika.Entities.GroupSubject;
import com.example.antrojiprogramavimopraktika.Entities.Subject;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupSubjectRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.ISubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GroupSubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.SubjectRepository;
import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public final class SubjectsController {

    private final IGroupSubjectRepository gsRepo = new GroupSubjectRepository();
    private final ISubjectRepository subjectRepo = new SubjectRepository();


    // Group management FXML

    @FXML
    private TableView<Subject> subjectlistTable;

    @FXML
    private TableColumn<Subject, Integer> subjectlist_id;

    @FXML
    private TableColumn<Subject, String> subjectlist_subjectName;

    @FXML
    private Button createSubjectBtn;

    @FXML
    private Button deleteSubjectBtn;

    // Assign subject to group FXML

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

    public void initialize() {

        subjectlist_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectlist_subjectName.setCellValueFactory(new PropertyValueFactory<>("name"));

        subjectsassign_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        subjectsassign_subjectname.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getSubject().getName())
        );
        subjectsassign_groupname.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getGroup().getName())
        );

        loadSubjects();
        loadGroupSubjects();

        createSubjectBtn.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Admin/Modals/AddSubjectModal.fxml",
                        "Studijų dalyko pridėjimas",
                        modalController -> ((AddSubjectController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

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

        deleteSubjectBtn.setOnAction(_ -> {
            Subject selected = subjectlistTable.getSelectionModel().getSelectedItem();

            if(selected == null) return;

            subjectRepo.removeSubject(selected.getId());

            loadSubjects();
        });

        subjectsremoveBtn.setOnAction(_ -> {
            GroupSubject selected = subjectsTable.getSelectionModel().getSelectedItem();

            if(selected == null) return;

            gsRepo.removeGroupSubject(selected.getID());

            loadGroupSubjects();
        });
    }

    public void loadSubjects() {
        List<Subject> list = subjectRepo.getAllSubjects();
        subjectlistTable.setItems(FXCollections.observableList(list));
    }

    public void loadGroupSubjects() {
        List<GroupSubject> list = gsRepo.getGroupSubjects();
        subjectsTable.setItems(FXCollections.observableArrayList(list));
    }

}
