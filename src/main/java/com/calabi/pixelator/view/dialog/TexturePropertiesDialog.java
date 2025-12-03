package com.calabi.pixelator.view.dialog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import com.calabi.pixelator.file.PixelFile;
import com.calabi.pixelator.main.MainScene;
import com.calabi.pixelator.project.Project;
import com.calabi.pixelator.ui.image.WritableImage;
import com.calabi.pixelator.view.editor.IWC;

public class TexturePropertiesDialog extends BasicDialog {

    private final PixelFile pixelFile;

    private final TextField width = new TextField();
    private final TextField height = new TextField();
    private final ComboBox<String> res = new ComboBox<>();
    private final CheckBox animated = new CheckBox("Is Animated");
    private final Spinner<Integer> frames = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1024, 1));
    private final Spinner<Integer> duration = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 100));
    private final ComboBox<String> type = new ComboBox<>();
    private final ComboBox<String> transparency = new ComboBox<>();
    private final CheckBox emissive = new CheckBox("Emissive texture (mod)");

    public TexturePropertiesDialog() {
        super(420, 260);
        setTitle("Texture Properties");
        setOkText("Apply");

        this.pixelFile = IWC.get().getCurrentFile();

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        res.getItems().addAll("16", "32", "64", "128", "Custom");
        res.getSelectionModel().selectFirst();

        type.getItems().addAll("block", "item", "gui", "entity");
        type.getSelectionModel().selectFirst();

        transparency.getItems().addAll("Opaque", "Cutout", "Blend");
        transparency.getSelectionModel().selectFirst();

        grid.addRow(0, new Label("Width:"), width);
        grid.addRow(1, new Label("Height:"), height);
        grid.addRow(2, new Label("Resolution:"), res);
        grid.addRow(3, animated, new Label("Frames:"));
        grid.add(frames, 2, 3);
        grid.addRow(4, new Label("Frame Duration (ms):"), duration);
        grid.addRow(5, new Label("Type:"), type);
        grid.addRow(6, new Label("Transparency:"), transparency);
        grid.addRow(7, emissive);

        loadFromCurrent();

        setOnOk(b -> apply());

        getDialogPane().getStylesheets().addAll(MainScene.getStyle());
        setDialogContent(grid);
    }

    @Override
    public void focus() {
    }

    private void apply() {
        if (pixelFile == null || !Project.active() || pixelFile.getFile() == null) {
            return;
        }

        WritableImage img = pixelFile.getImage();
        if (img != null) {
            try {
                int w = Integer.parseInt(width.getText());
                int h = Integer.parseInt(height.getText());
                if (w != img.getWidth() || h != img.getHeight()) {
                    // At this stage we only store values; resizing image is out of scope.
                }
            } catch (NumberFormatException ignored) {
            }
        }

        if (!Project.get().isMinecraftPack()) {
            return;
        }

        File textureFile = pixelFile.getFile();
        if (textureFile == null) {
            return;
        }

        Path mcmetaPath = Paths.get(textureFile.getAbsolutePath() + ".mcmeta");
        int frameCount = frames.getValue();
        int frameTime = duration.getValue();
        boolean isAnimated = animated.isSelected();

        if (!isAnimated) {
            try {
                java.nio.file.Files.deleteIfExists(mcmetaPath);
            } catch (IOException ignored) {
            }
            return;
        }

        String mcmetaJson = buildMcmetaJson(frameCount, frameTime);
        try {
            Files.write(mcmetaPath, mcmetaJson.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {
        }
    }

    private void loadFromCurrent() {
        if (pixelFile == null) {
            return;
        }
        WritableImage img = pixelFile.getImage();
        if (img != null) {
            width.setText(Integer.toString((int) img.getWidth()));
            height.setText(Integer.toString((int) img.getHeight()));
        }

        if (!Project.active() || !Project.get().isMinecraftPack() || pixelFile.getFile() == null) {
            return;
        }

        Path mcmetaPath = Paths.get(pixelFile.getFile().getAbsolutePath() + ".mcmeta");
        if (!java.nio.file.Files.exists(mcmetaPath)) {
            return;
        }

        try {
            String json = new String(Files.readAllBytes(mcmetaPath), StandardCharsets.UTF_8);
            parseMcmeta(json);
        } catch (IOException ignored) {
        }
    }

    private void parseMcmeta(String json) {
        String lower = json.replace("\n", " ").replace("\r", " ");
        int animIndex = lower.indexOf("\"animation\"");
        if (animIndex == -1) {
            return;
        }
        animated.setSelected(true);

        int timeIndex = lower.indexOf("\"frametime\"", animIndex);
        if (timeIndex != -1) {
            int colon = lower.indexOf(':', timeIndex);
            if (colon != -1) {
                int end = findNumberEnd(lower, colon + 1);
                String num = lower.substring(colon + 1, end).replaceAll("[^0-9]", "");
                if (!num.isEmpty()) {
                    try {
                        duration.getValueFactory().setValue(Integer.parseInt(num) * 50);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        int framesIndex = lower.indexOf("\"frames\"", animIndex);
        if (framesIndex != -1) {
            int lb = lower.indexOf('[', framesIndex);
            int rb = lower.indexOf(']', lb + 1);
            if (lb != -1 && rb != -1) {
                String content = lower.substring(lb + 1, rb);
                int count = content.split(",").length;
                if (count > 0) {
                    frames.getValueFactory().setValue(count);
                }
            }
        }
    }

    private int findNumberEnd(String s, int start) {
        int i = start;
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || Character.isWhitespace(s.charAt(i)))) {
            i++;
        }
        return i;
    }

    private String buildMcmetaJson(int frameCount, int frameTimeMs) {
        int frameTimeTicks = Math.max(1, frameTimeMs / 50);
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"animation\": {\n");
        sb.append("    \"frametime\": ").append(frameTimeTicks).append(",\n");
        sb.append("    \"frames\": [");
        for (int i = 0; i < frameCount; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(i);
        }
        sb.append("]\n");
        sb.append("  }\n");
        sb.append("}\n");
        return sb.toString();
    }
}
