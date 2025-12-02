package com.calabi.pixelator.view.dialog;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class NewPackDialog extends BasicDialog {

    public NewPackDialog() {
        super(520, 380);
        setTitle("New Resource Pack");
        setOkText("Create");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        TextField nameField = new TextField();
        TextArea descField = new TextArea();
        descField.setPrefRowCount(3);

        ComboBox<String> formatBox = new ComboBox<>(FXCollections.observableArrayList(
                "1.8 (Legacy)", "1.9", "1.12", "1.13", "1.14", "1.16", "1.17", "1.18", "1.19", "1.20", "1.21"));
        formatBox.getSelectionModel().selectLast();

        ComboBox<String> mcVersion = new ComboBox<>(FXCollections.observableArrayList(
                "1.8", "1.12", "1.16", "1.18", "1.20", "1.21"));
        mcVersion.getSelectionModel().selectLast();

        ComboBox<String> resolution = new ComboBox<>(FXCollections.observableArrayList("16", "32", "64", "128", "Custom"));
        resolution.getSelectionModel().selectFirst();

        TextField folderField = new TextField();
        Button chooseFolder = new Button("Choose...");

        CheckBox autofill = new CheckBox("Auto-fill assets/minecraft/textures/… structure");
        autofill.setSelected(true);
        CheckBox createMcmeta = new CheckBox("Create pack.mcmeta automatically");
        createMcmeta.setSelected(true);

        RadioButton importExisting = new RadioButton("Import existing pack instead");
        RadioButton useVanilla = new RadioButton("Use default vanilla template textures");
        ToggleGroup group = new ToggleGroup();
        importExisting.setToggleGroup(group);
        useVanilla.setToggleGroup(group);

        grid.addRow(0, new Label("Pack Name:"), nameField);
        grid.addRow(1, new Label("Description:"), descField);
        grid.addRow(2, new Label("Pack Format:"), formatBox);
        grid.addRow(3, new Label("Minecraft Version:"), mcVersion);
        grid.addRow(4, new Label("Resolution:"), resolution);
        HBox folderRow = new HBox(6, folderField, chooseFolder);
        grid.addRow(5, new Label("Target folder:"), folderRow);
        grid.add(autofill, 1, 6);
        grid.add(createMcmeta, 1, 7);
        VBox extras = new VBox(6, importExisting, useVanilla);
        grid.add(extras, 1, 8);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(grid);
    }

    @Override
    public void focus() {
        // focus handled by dialog
    }
}
