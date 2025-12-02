package com.calabi.pixelator.view.dialog;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.calabi.pixelator.config.BuildInfo;
import com.calabi.pixelator.config.Theme;
import com.calabi.pixelator.main.ExceptionHandler;
import com.calabi.pixelator.main.MainScene;

public class AboutDialog extends BasicDialog {

    public AboutDialog() {
        super(420, 180);
        setTitle("About");
        setOkText("Close");

        HBox root = new HBox();
        root.setSpacing(12);
        root.setPadding(new Insets(12));

        ImageView iconView = new ImageView();
        iconView.setFitWidth(48);
        iconView.setFitHeight(48);
        updateIcon(iconView, MainScene.themeProperty().get());
        MainScene.themeProperty().addListener((ov, o, n) -> updateIcon(iconView, n));

        VBox textBox = new VBox();
        textBox.setSpacing(6);
        textBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Pixelator");
        title.getStyleClass().add("dialog-title");
        Label version = new Label("Version: " + BuildInfo.getVersion());
        Label author = new Label("Author: Calabiyaur");
        Label license = new Label("License: MIT");

        HBox links = new HBox();
        links.setSpacing(10);

        Hyperlink website = new Hyperlink("Project site");
        website.setOnAction(e -> openURI("https://calabiyaur.github.io/Pixelator/", "Open project site"));

        Hyperlink source = new Hyperlink("Source code");
        source.setOnAction(e -> openURI("https://github.com/Calabiyaur/Pixelator", "Open source repository"));

        links.getChildren().addAll(website, source);

        textBox.getChildren().addAll(title, version, author, license, links);

        root.getChildren().addAll(iconView, textBox);

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());

        setDialogContent(root);
    }

    private void updateIcon(ImageView view, Theme theme) {
        try {
            String folder = theme == Theme.DARK ? "dark" : "bright";
            String path = "/images/" + folder + "/icon48.png";
            Image img = new Image(MainScene.class.getResourceAsStream(path));
            if (img.isError()) {
                // fallback to larger icon
                img = new Image(MainScene.class.getResourceAsStream("/images/" + folder + "/icon256.png"));
            }
            view.setImage(img);
        } catch (Exception e) {
            ExceptionHandler.handle(e);
        }
    }

    private void openURI(String uri, String desc) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(uri));
            }
        } catch (IOException | URISyntaxException e) {
            ExceptionHandler.handle(e);
        }
    }

    @Override
    public void focus() {
        ok.requestFocus();
    }
}
