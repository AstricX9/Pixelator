package com.calabi.pixelator.view.dialog;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.calabi.pixelator.main.MainScene;

public class OpenPackDialog extends BasicDialog {

    public OpenPackDialog() {
        super(640, 420);
        setTitle("Open Resource Pack");
        setOkText("Open");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(8));

        HBox top = new HBox(8);
        TextField folderField = new TextField();
        Button choose = new Button("Choose...");
        Button reveal = new Button("Open root folder in Explorer");
        top.getChildren().addAll(folderField, choose, reveal);

        ListView<String> list = new ListView<>(FXCollections.observableArrayList(
                "blocks/", "items/", "entities/", "gui/", "particles/"));
        list.setPrefWidth(240);

        VBox center = new VBox(6, new Label("Detected textures (scan results):"), list);

        VBox right = new VBox(6, new Label("Metadata:"), new Label("pack_format: ?"), new Label("description: ?"));

        root.setTop(top);
        root.setLeft(list);
        root.setCenter(center);
        root.setRight(right);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(root);
    }

    @Override
    public void focus() {
    }
}
