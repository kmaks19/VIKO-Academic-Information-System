package com.example.antrojiprogramavimopraktika.Controller.Admin;

import com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers.AddGroupController;
import com.example.antrojiprogramavimopraktika.Entities.Group;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GroupRepository;
import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public final class GroupController {

    @FXML
    private TableView<Group> sg_table;

    @FXML
    private TableColumn<Group, Integer> sg_id;

    @FXML
    private TableColumn<Group, String> sg_groupname;

    @FXML
    private Button sg_addGroup;

    @FXML
    private Button sg_removeGroup;

    private final IGroupRepository groupRepo = new GroupRepository();

    public void initialize() {

        sg_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        sg_groupname.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadGroups();

        // Delete group button event handler on click
        sg_removeGroup.setOnAction(_ -> {
            Group selected = sg_table.getSelectionModel().getSelectedItem();

            if(selected == null) return;

            groupRepo.deleteGroup(selected.getId());

            loadGroups();
        });

        // Add group button event handler on click
        sg_addGroup.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Admin/Modals/AddStudentGroupModal.fxml",
                        "Studentų grupės pridėjimas",
                        modalController -> ((AddGroupController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loadGroups() {
        List<Group> groups = groupRepo.getGroups();
        sg_table.setItems(FXCollections.observableArrayList(groups));
    }
}
