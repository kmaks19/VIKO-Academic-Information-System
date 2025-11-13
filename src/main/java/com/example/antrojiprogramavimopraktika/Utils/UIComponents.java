package com.example.antrojiprogramavimopraktika.Utils;

import javafx.scene.Node;
import javafx.scene.control.Label;

public final class UIComponents {

    private UIComponents() {}

    public static void setLabelText(Label label, String text) {
        if (label != null) {
            label.setText(text);
        }
    }

    public static void setNodeVisibility(Node node, boolean visible) {
        if (node != null) {
            node.setVisible(visible);
            node.setManaged(visible);
        }
    }
}
