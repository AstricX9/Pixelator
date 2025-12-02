package com.calabi.pixelator.view.dialog;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class ModIntegrationDialog extends BasicDialog {

    public ModIntegrationDialog() {
        super(640, 420);
        setTitle("Mod Integration");
        setOkText("Close");

        VBox root = new VBox(8);
        root.setPadding(new Insets(10));

        HBox searchRow = new HBox(8, new TextField(), new Button("Search Modrinth"));

        ListView<String> results = new ListView<>(FXCollections.observableArrayList("mod-a", "mod-b"));
        results.setPrefHeight(220);

        HBox actions = new HBox(8, new Button("Import mod textures"), new Button("Extract structure"));

        root.getChildren().addAll(new Label("Mod browser (placeholder):"), searchRow, results, actions);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(root);
    }

    @Override
    public void focus() {
    }
}
