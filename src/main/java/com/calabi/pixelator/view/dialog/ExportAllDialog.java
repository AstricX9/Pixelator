package com.calabi.pixelator.view.dialog;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class ExportAllDialog extends BasicDialog {

    public ExportAllDialog() {
        super(560, 360);
        setTitle("Export Pack / Textures");
        setOkText("Export");

        VBox root = new VBox(8);
        root.setPadding(new Insets(10));

        ListView<String> selection = new ListView<>(FXCollections.observableArrayList("All textures", "Changed only", "Selected textures"));
        selection.setPrefHeight(120);

        CheckBox zip = new CheckBox("Zip into .mcpack (Bedrock)");
        ChoiceBox<String> target = new ChoiceBox<>(FXCollections.observableArrayList("Folder", "Zip"));
        target.getSelectionModel().selectFirst();

        HBox targetRow = new HBox(8, new Label("Target:"), target, new Button("Choose...") );

        CheckBox versionStamp = new CheckBox("Version stamping (pack-v1.2.zip)");

        root.getChildren().addAll(new Label("What to export:"), selection, targetRow, zip, versionStamp);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(root);
    }

    @Override
    public void focus() {
    }
}
