package com.example.antrojiprogramavimopraktika.Utils;

import javafx.scene.control.Label;

public final class UIErrors {

    private UIErrors() {}

    public static void showLabelError(Label label, String message) {
        UIComponents.setLabelText(label, message);
        UIComponents.setNodeVisibility(label, true);
    }

    public static void hideLabelError(Label label) {
        UIComponents.setLabelText(label, null);
        UIComponents.setNodeVisibility(label, false);
    }
}
