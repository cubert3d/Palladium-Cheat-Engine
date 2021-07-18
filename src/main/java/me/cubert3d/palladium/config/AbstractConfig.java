package me.cubert3d.palladium.config;

import me.cubert3d.palladium.Palladium;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

abstract class AbstractConfig {

    public static final String KEY_VALUE_DELIMITER = ": ";
    public static final String LIST_DELIMITER = ", ";

    protected final String directoryName = "palladium";
    private final File directory;
    private final File file;
    protected int readCounter;
    protected int writeCounter;

    protected AbstractConfig(String fileName) {
        this.directory = new File(directoryName);
        this.file = new File(directoryName + "/" + fileName);
    }

    protected final @NotNull String getConfigName() {
        return getClass().getSimpleName();
    }

    protected final File getDirectory() {
        return directory;
    }

    protected final File getFile() {
        return file;
    }

    private boolean createFile() {
        try {
            boolean pathCreated = getDirectory().mkdir();
            boolean fileCreated = getFile().createNewFile();
            if (pathCreated) {
                Palladium.getLogger().info("Created Palladium directory file");
            }
            if (fileCreated) {
                Palladium.getLogger().info(String.format("Created %s file", getConfigName()));
            }
            return pathCreated || fileCreated;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void load() {
        boolean createdFile = createFile();
        if (!createdFile) {
            Palladium.getLogger().info(String.format("Loading %s...", getConfigName()));
            read();
            Palladium.getLogger().info(String.format("Done loading %s!", getConfigName()));
        }
    }

    public final void save() {
        Palladium.getLogger().info("Saving module config...");
        createFile();
        write();
        Palladium.getLogger().info("Done saving module config!");
    }

    protected abstract void read();

    protected abstract void write();

    protected final void printReadException(@NotNull Exception e) {
        String exceptionMessage = e.getClass().getSimpleName() + ", " + e.getMessage();
        Palladium.getLogger().error("Error reading module config file at line " + readCounter + ": " + exceptionMessage);
    }

    protected final void printWriteException(@NotNull Exception e) {
        String exceptionMessage = e.getClass().getSimpleName() + ", " + e.getMessage();
        Palladium.getLogger().error("Error writing to module config file at line " + writeCounter + ": " + exceptionMessage);
    }
}
