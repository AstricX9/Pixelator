package com.calabi.pixelator.view.dialog;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class AnimationTimelineDialog extends BasicDialog {

    public AnimationTimelineDialog() {
        super(520, 360);
        setTitle("Animation Timeline");
        setOkText("Close");

        VBox root = new VBox(8);
        root.setPadding(new Insets(10));

        ListView<String> frames = new ListView<>(FXCollections.observableArrayList("frame0.png", "frame1.png"));
        frames.setPrefHeight(180);

        Spinner<Integer> duration = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 100));
        CheckBox onion = new CheckBox("Onion-skin");

        HBox controls = new HBox(8, new Label("Duration (ms):"), duration, onion, new Button("Export all frames"));

        Button genMcmeta = new Button("Generate .mcmeta animation files");

        root.getChildren().addAll(new Label("Frames:"), frames, controls, genMcmeta);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(root);
    }

    @Override
    public void focus() {
    }
}
