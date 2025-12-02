package com.calabi.pixelator.view.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class SaveToPackDialog extends BasicDialog {

    public SaveToPackDialog(String suggestedPath) {
        super(420, 160);
        setTitle("Save To Resource Pack");
        setOkText("Save");

        VBox box = new VBox(8);
        box.setPadding(new Insets(10));

        HBox pathRow = new HBox(6);
        TextField pathField = new TextField(suggestedPath == null ? "textures/block/stone.png" : suggestedPath);
        Button reveal = new Button("Reveal file in Explorer");
        pathRow.getChildren().addAll(pathField, reveal);

        CheckBox overwrite = new CheckBox("Overwrite if exists");
        CheckBox autoExport = new CheckBox("Auto-export on save from now on");

        box.getChildren().addAll(new Label("Target path (inside pack):"), pathRow, overwrite, autoExport);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(box);
    }

    @Override
    public void focus() {
    }
}
