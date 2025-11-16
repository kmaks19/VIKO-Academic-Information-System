package com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers;

import com.example.antrojiprogramavimopraktika.Controller.Admin.GroupController;
import com.example.antrojiprogramavimopraktika.Interfaces.IGroupRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GroupRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class AddGroupController {
    public AddGroupController() {}

    private final IGroupRepository groupRepo = new GroupRepository();

    // Tėvinis langas yra student grupės main langas.
    private GroupController parent;

    public void setParent(GroupController parent) {
        this.parent = parent;
    }

    @FXML
    private TextField sgaddmodal_input;

    @FXML
    private Button sgadd_confirmBtn;

    @FXML
    private Button sgadd_cancelBtn;

    @FXML
    private Label sgaddgroup_error;

    public void initialize() {
        sgadd_confirmBtn.setOnAction(_ -> {
            if (save()) {
                if (parent != null) {
                    parent.loadGroups();
                }
                close();
            }
        });

        sgadd_cancelBtn.setOnAction(_ -> close());
    }

    private boolean save() {
        sgaddgroup_error.setVisible(false);
        String groupName = sgaddmodal_input.getText().trim();

        if (groupName.isEmpty()) {
            sgaddgroup_error.setText("Grupės pavadinimas negali būti tuščias!");
            sgaddgroup_error.setVisible(true);
            return false;
        }

        if (!groupRepo.checkIfUnique(groupName)) {
            sgaddgroup_error.setText("Tokia grupė jau egzistuoja!");
            sgaddgroup_error.setVisible(true);
            return false;
        }

        return groupRepo.addGroup(groupName);
    }

    private void close() {
        ((Stage) sgadd_confirmBtn.getScene().getWindow()).close();
    }
}
