package com.calabi.pixelator.view.dialog;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class MissingTexturesFinderDialog extends BasicDialog {

    public MissingTexturesFinderDialog() {
        super(560, 360);
        setTitle("Missing Textures Finder");
        setOkText("Close");

        VBox box = new VBox(8);
        box.setPadding(new Insets(10));

        ListView<String> issues = new ListView<>(FXCollections.observableArrayList(
                "Missing block: oak_planks", "Wrong-size: stone.png (32x32 expected 16x16)"));
        issues.setPrefHeight(220);

        Button scan = new Button("Scan pack");
        Button fix = new Button("Attempt auto-fix (best-effort)");

        box.getChildren().addAll(new Label("Problems found:"), issues, scan, fix);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(box);
    }

    @Override
    public void focus() {
    }
}
