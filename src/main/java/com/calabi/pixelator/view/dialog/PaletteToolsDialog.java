package com.calabi.pixelator.view.dialog;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class PaletteToolsDialog extends BasicDialog {

    public PaletteToolsDialog() {
        super(520, 360);
        setTitle("Palette Tools");
        setOkText("Close");

        VBox root = new VBox(8);
        root.setPadding(new Insets(10));

        ListView<String> presets = new ListView<>(FXCollections.observableArrayList("Vanilla Extract", "Biome: Taiga", "Biome: Jungle"));
        presets.setPrefHeight(160);

        ComboBox<String> dithers = new ComboBox<>(FXCollections.observableArrayList("None", "Floyd-Steinberg", "Atkinson"));
        dithers.getSelectionModel().selectFirst();

        HBox actions = new HBox(8, new Button("Extract from texture"), new Button("Generate from biome"));

        root.getChildren().addAll(new Label("Presets:"), presets, new Label("Dithering:"), dithers, actions);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(root);
    }

    @Override
    public void focus() {
    }
}
