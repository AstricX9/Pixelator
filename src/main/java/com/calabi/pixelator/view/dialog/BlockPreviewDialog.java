package com.calabi.pixelator.view.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class BlockPreviewDialog extends BasicDialog {

    public BlockPreviewDialog() {
        super(640, 480);
        setTitle("Block Preview (3D)");
        setOkText("Close");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Placeholder area for 3D preview
        Label preview = new Label("[3D preview placeholder]");
        preview.setPrefSize(360, 320);

        VBox controls = new VBox(8);
        ComboBox<String> blockType = new ComboBox<>();
        blockType.getItems().addAll("Cube", "Cross", "Sided block");
        blockType.getSelectionModel().selectFirst();

        ComboBox<String> material = new ComboBox<>();
        material.getItems().addAll("Smooth", "Rough", "Metallic");
        material.getSelectionModel().selectFirst();

        Slider lighting = new Slider(0, 100, 50);
        Button spin = new Button("Toggle Spin");

        controls.getChildren().addAll(new Label("Block type:"), blockType, new Label("Material:"), material,
                new Label("Lighting:"), lighting, spin);

        root.setLeft(preview);
        root.setRight(controls);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(root);
    }

    @Override
    public void focus() {
    }
}
