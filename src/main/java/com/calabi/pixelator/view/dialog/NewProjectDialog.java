package com.calabi.pixelator.view.dialog;

import java.io.File;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import com.calabi.pixelator.project.PackMetadata;
import com.calabi.pixelator.project.Project;
import com.calabi.pixelator.project.ProjectType;
import com.calabi.pixelator.ui.control.BasicDirectoryField;
import com.calabi.pixelator.view.editor.IWC;
import com.calabi.pixelator.view.editor.window.ImageWindow;

public class NewProjectDialog extends BasicDialog {

    private final BasicDirectoryField locationField;
    private final ComboBox<String> typeField;

    public NewProjectDialog() {
        setTitle("Create a new Project");
        setOkText("Create");

        setPrefSize(700, 400);

        // Find location of last opened image
        File file = null;
        for (ImageWindow imageWindow : IWC.get().imageWindows()) {
            if (imageWindow.getFile().getFile() != null) {
                file = imageWindow.getFile().getFile();
                break;
            }
        }

        File location = file == null ? new File(".") : file.getParentFile();

        locationField = new BasicDirectoryField("Project location", location);
        GridPane.setHgrow(locationField, Priority.ALWAYS);

        typeField = new ComboBox<>();
        typeField.getItems().addAll("Sprite Project", "Minecraft Pack");
        typeField.getSelectionModel().selectFirst();

        addContent(locationField, 0, 0);
        addContent(typeField, 0, 1);
    }

    @Override
    public void focus() {
        locationField.focus();
    }

    public Project getProject() {
        File location = locationField.getValue();
        if (location != null && location.exists()) {
            Project project = new Project(location);
            String selected = typeField.getSelectionModel().getSelectedItem();
            if ("Minecraft Pack".equals(selected)) {
                project.setType(ProjectType.MINECRAFT_PACK);
                PackMetadata meta = project.getPackMetadata();
                meta.setRootFolder(location.getAbsolutePath());
                project.setPackMetadata(meta);
            }
            return project;
        } else {
            return null;
        }
    }

}
