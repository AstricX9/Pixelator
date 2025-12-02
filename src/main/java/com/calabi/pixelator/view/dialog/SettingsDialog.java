package com.calabi.pixelator.view.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import com.calabi.pixelator.config.Config;
import com.calabi.pixelator.config.Theme;
import com.calabi.pixelator.main.MainScene;
import com.calabi.pixelator.ui.control.BasicColorField;
import com.calabi.pixelator.ui.control.BasicComboBox;
import com.calabi.pixelator.view.ToolView;
import com.calabi.pixelator.view.editor.IWC;
import com.calabi.pixelator.view.editor.window.ImageWindow;

public class SettingsDialog extends BasicDialog {

	private final BasicComboBox<Theme> themeField;
	private final BasicColorField backgroundColorField;
	private final BasicColorField borderColorField;
	private final BasicColorField gridColorField;
	private final BasicColorField crosshairColorField;

	private final TextField resourcePackDirField;
	private final javafx.scene.control.ChoiceBox<String> defaultResolutionBox;
	private final CheckBox exportZipCheck;

	private final CheckBox autosaveCheck;
	private final Spinner<Integer> layerLimitSpinner;
	private final Spinner<Integer> undoSizeSpinner;
	private final Spinner<Double> previewLightingSpinner;

	public SettingsDialog() {
		super(520, 420);
		setTitle("Settings");
		setOkText("Apply");

		// Appearance defaults
		Theme theme = Theme.valueOf(Config.THEME.getString());
		themeField = new BasicComboBox<>("Theme", Theme.values(), theme);
		themeField.setValue(theme);

		Color backgroundColor = Color.valueOf(Config.IMAGE_BACKGROUND_COLOR.getString());
		backgroundColorField = new BasicColorField("Image background", backgroundColor);
		backgroundColorField.setValue(backgroundColor);

		Color borderColor = Color.valueOf(Config.IMAGE_BORDER_COLOR.getString());
		borderColorField = new BasicColorField("Image border", borderColor);
		borderColorField.setValue(borderColor);

		Color gridColor = Color.valueOf(Config.GRID_COLOR.getString());
		gridColorField = new BasicColorField("Grid color", gridColor);
		gridColorField.setValue(gridColor);

		Color crosshairColor = Color.valueOf(Config.CROSSHAIR_COLOR.getString());
		crosshairColorField = new BasicColorField("Crosshair color", crosshairColor);
		crosshairColorField.setValue(crosshairColor);

		// Resource pack / export defaults
		resourcePackDirField = new TextField(Config.RESOURCE_PACK_DIRECTORY.getString());
		defaultResolutionBox = new javafx.scene.control.ChoiceBox<>();
		defaultResolutionBox.getItems().addAll("16", "32", "64", "128");
		defaultResolutionBox.setValue(String.valueOf(Config.DEFAULT_RESOURCE_RESOLUTION.getInt()));
		exportZipCheck = new CheckBox("Zip into .mcpack by default");
		exportZipCheck.setSelected(Config.EXPORT_ZIP_BY_DEFAULT.getBoolean());

		// Advanced
		autosaveCheck = new CheckBox("Autosave");
		autosaveCheck.setSelected(Config.AUTOSAVE.getBoolean());
		layerLimitSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1024, Config.LAYER_LIMIT.getInt()));
		undoSizeSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, Config.UNDO_HISTORY_SIZE.getInt()));
		previewLightingSpinner = new Spinner<>(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, Config.BLOCK_PREVIEW_LIGHTING.getDouble()));

		TabPane tabs = new TabPane();

		Tab appearanceTab = new Tab("Appearance");
		GridPane appearanceGrid = new GridPane();
		appearanceGrid.setHgap(8);
		appearanceGrid.setVgap(8);
		appearanceGrid.setPadding(new Insets(10));
		appearanceGrid.addRow(0, themeField.getFrontLabel(), themeField.getControlWrapper());
		appearanceGrid.add(new Separator(), 0, 1, 3, 1);
		appearanceGrid.addRow(2, backgroundColorField.getFrontLabel(), backgroundColorField.getControlWrapper());
		appearanceGrid.addRow(3, borderColorField.getFrontLabel(), borderColorField.getControlWrapper());
		appearanceGrid.add(new Separator(), 0, 4, 3, 1);
		appearanceGrid.addRow(5, gridColorField.getFrontLabel(), gridColorField.getControlWrapper());
		appearanceGrid.addRow(6, crosshairColorField.getFrontLabel(), crosshairColorField.getControlWrapper());
		appearanceTab.setContent(appearanceGrid);

		Tab resourceTab = new Tab("Resource Pack");
		GridPane resourceGrid = new GridPane();
		resourceGrid.setHgap(8);
		resourceGrid.setVgap(8);
		resourceGrid.setPadding(new Insets(10));
		resourceGrid.addRow(0, new Label("Default resource-pack folder:"), resourcePackDirField);
		resourceGrid.addRow(1, new Label("Default resolution:"), defaultResolutionBox);
		resourceGrid.addRow(2, exportZipCheck);
		resourceTab.setContent(resourceGrid);

		Tab exportTab = new Tab("Export");
		VBox exportBox = new VBox(8);
		exportBox.setPadding(new Insets(10));
		exportBox.getChildren().addAll(new Label("Export options"), new Separator(), new Label("Default target and options are configured in the Resource Pack tab."));
		exportTab.setContent(exportBox);

		Tab advancedTab = new Tab("Advanced");
		GridPane advGrid = new GridPane();
		advGrid.setHgap(8);
		advGrid.setVgap(8);
		advGrid.setPadding(new Insets(10));
		advGrid.addRow(0, autosaveCheck);
		advGrid.addRow(1, new Label("Default block preview lighting:"), previewLightingSpinner);
		advGrid.addRow(2, new Label("Layer limit:"), layerLimitSpinner);
		advGrid.addRow(3, new Label("Undo history size:"), undoSizeSpinner);
		advancedTab.setContent(advGrid);

		tabs.getTabs().addAll(appearanceTab, resourceTab, exportTab, advancedTab);

		addContent(tabs, 0, 0);

		// Ensure dialog uses application styles (theme)
		getDialogPane().getStylesheets().addAll(MainScene.getStyle());

		setOnOk(e -> {
			apply();
			close();
		});
	}

	private void apply() {
		// Appearance
		Theme theme = themeField.getValue();
		Color color = backgroundColorField.getValue();
		Color borderColor = borderColorField.getValue();
		Color gridColor = gridColorField.getValue();
		Color crosshairColor = crosshairColorField.getValue();

		Config.THEME.putString(theme.name());
		Config.IMAGE_BACKGROUND_COLOR.putString(color.toString());
		Config.IMAGE_BORDER_COLOR.putString(borderColor.toString());
		Config.GRID_COLOR.putString(gridColor.toString());
		Config.CROSSHAIR_COLOR.putString(crosshairColor.toString());

		MainScene.setTheme(theme);

		ToolView.get().getPreviewBackground().setColor(color);
		ToolView.get().getPreviewBackground().setBorderColor(borderColor);
		ToolView.get().getPreviewBackground().refresh();

		for (ImageWindow imageWindow : IWC.get().imageWindows()) {
			imageWindow.getEditor().getImageBackground().setColor(color);
			imageWindow.getEditor().getImageBackground().setBorderColor(borderColor);
			imageWindow.getEditor().setGridColor(gridColor);
			imageWindow.getEditor().setCrosshairColor(crosshairColor);
			imageWindow.getEditor().getImageBackground().refresh();
		}

		// Resource / Export
		Config.RESOURCE_PACK_DIRECTORY.putString(resourcePackDirField.getText());
		try {
			int res = Integer.parseInt(defaultResolutionBox.getValue());
			Config.DEFAULT_RESOURCE_RESOLUTION.putInt(res);
		} catch (NumberFormatException ex) {
			// ignore
		}
		Config.EXPORT_ZIP_BY_DEFAULT.putBoolean(exportZipCheck.isSelected());

		// Advanced
		Config.AUTOSAVE.putBoolean(autosaveCheck.isSelected());
		Config.LAYER_LIMIT.putInt(layerLimitSpinner.getValue());
		Config.UNDO_HISTORY_SIZE.putInt(undoSizeSpinner.getValue());
		Config.BLOCK_PREVIEW_LIGHTING.putDouble(previewLightingSpinner.getValue());
	}

	@Override
	public void focus() {
		themeField.requestFocus();
	}
}
