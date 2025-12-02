package com.calabi.pixelator.view.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class PackMcmetaEditorDialog extends BasicDialog {

    public PackMcmetaEditorDialog() {
        super(560, 380);
        setTitle("Edit pack.mcmeta");
        setOkText("Save");

        VBox box = new VBox(8);
        box.setPadding(new Insets(10));

        HBox top = new HBox(8);
        TextField format = new TextField();
        format.setPromptText("pack_format");
        TextField desc = new TextField();
        desc.setPromptText("description");
        top.getChildren().addAll(new Label("Format:"), format, new Label("Description:"), desc);

        Button chooseIcon = new Button("Choose icon (png)");

        TextArea jsonPreview = new TextArea();
        jsonPreview.setPrefRowCount(12);
        jsonPreview.setEditable(false);

        HBox actions = new HBox(8, new Button("Reload"), new Button("Save + Reload"));

        box.getChildren().addAll(top, chooseIcon, new Label("Live JSON preview:"), jsonPreview, actions);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(box);
    }

    @Override
    public void focus() {
    }
}
