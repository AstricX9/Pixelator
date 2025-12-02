package com.calabi.pixelator.view.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import com.calabi.pixelator.main.MainScene;

public class TexturePropertiesDialog extends BasicDialog {

    public TexturePropertiesDialog() {
        super(420, 260);
        setTitle("Texture Properties");
        setOkText("Apply");

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        TextField width = new TextField();
        TextField height = new TextField();
        ComboBox<String> res = new ComboBox<>();
        res.getItems().addAll("16", "32", "64", "128", "Custom");
        res.getSelectionModel().selectFirst();

        CheckBox animated = new CheckBox("Is Animated");
        Spinner<Integer> frames = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1024, 1));
        Spinner<Integer> duration = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 100));

        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll("block", "item", "gui", "entity");
        type.getSelectionModel().selectFirst();

        ComboBox<String> transparency = new ComboBox<>();
        transparency.getItems().addAll("Opaque", "Cutout", "Blend");
        transparency.getSelectionModel().selectFirst();

        CheckBox emissive = new CheckBox("Emissive texture (mod)");

        grid.addRow(0, new Label("Width:"), width);
        grid.addRow(1, new Label("Height:"), height);
        grid.addRow(2, new Label("Resolution:"), res);
        grid.addRow(3, animated, new Label("Frames:"));
        grid.add(frames, 2, 3);
        grid.addRow(4, new Label("Frame Duration (ms):"), duration);
        grid.addRow(5, new Label("Type:"), type);
        grid.addRow(6, new Label("Transparency:"), transparency);
        grid.addRow(7, emissive);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(grid);
    }

    @Override
    public void focus() {
    }
}
