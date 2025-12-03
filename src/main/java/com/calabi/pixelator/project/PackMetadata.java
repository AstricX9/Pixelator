package com.calabi.pixelator.project;

public class PackMetadata {

    private String name;
    private String minecraftVersion;
    private int packFormat;
    private int resolution;
    private String rootFolder;
    private String iconPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinecraftVersion() {
        return minecraftVersion;
    }

    public void setMinecraftVersion(String minecraftVersion) {
        this.minecraftVersion = minecraftVersion;
    }

    public int getPackFormat() {
        return packFormat;
    }

    public void setPackFormat(int packFormat) {
        this.packFormat = packFormat;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
