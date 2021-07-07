package me.cubert3d.palladium;

import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.exception.EnabledParseException;
import me.cubert3d.palladium.util.exception.SettingNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class Configuration {

    public static final String KEY_VALUE_DELIMITER = ": ";
    public static final String LIST_DELIMITER = ", ";

    private static String fileDirectory = "palladium";
    private static String fileName = "config.txt";
    private static String fullFileName = fileDirectory + "/" + fileName;

    private int readCounter;
    private int writeCounter;

    Configuration() {
        this.readCounter = 1;
        this.writeCounter = 1;
    }

    private void createFile() {
        File path = new File(fileDirectory);
        File file = new File(fullFileName);
        if (!file.exists()) {
            try {
                path.mkdir();
                file.createNewFile();
                Palladium.getLogger().info("Created config file");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfig() {
        Palladium.getLogger().info("Loading config...");
        createFile();
        read();
        Palladium.getLogger().info("Done loading config!");
    }

    public void saveConfig() {
        Palladium.getLogger().info("Saving config...");
        createFile();
        write();
        Palladium.getLogger().info("Done saving config!");
    }

    private void read() {
        try {

            Scanner scanner = new Scanner(new File(fullFileName));
            Module currentModule = null;

            while (scanner.hasNextLine()) {

                String data = scanner.nextLine();

                // If this line is not indented, then it is a module name + enabled status
                if (!data.startsWith("\t")) {

                    String[] strings = data.split(KEY_VALUE_DELIMITER);
                    String moduleName;
                    String enabledStatus;
                    Module module = null;
                    boolean enabled = false;

                    try {
                        moduleName = strings[0];
                        enabledStatus = strings[1];
                        module = parseModuleName(moduleName);
                        enabled = parseEnabledStatus(enabledStatus);
                    } catch (Exception e) {
                        printReadException(e);
                    }

                    if (module != null) {
                        currentModule = module;
                        currentModule.setEnabled(enabled);
                    }
                }
                else if (currentModule != null) {

                    String[] strings = data.split(KEY_VALUE_DELIMITER);
                    String settingName = strings[0];
                    String settingValues;

                    if (strings.length > 1) {
                        settingValues = strings[1];
                    }
                    else {
                        settingValues = " ";
                    }

                    Setting setting = parseSettingName(currentModule, settingName);
                    setting.setFromString(settingValues);
                }

                readCounter++;
            }
        }
        catch (Exception e) {
            printReadException(e);
            e.printStackTrace();
        }
        readCounter = 1;
    }

    private void write() {
        try {
            FileWriter writer = new FileWriter(fullFileName);

            for (Module module : Palladium.getInstance().getModuleManager().getModules()) {

                // If the module is enabled, then add "enabled" on the right side of this line; if it is
                // disabled, then add "disabled."
                String enabledStatus = module.isEnabled() ? "enabled" : "disabled";

                // Write the module name, then the key-value delimiter, then whether the module is enabled or disabled.
                writer.write(module.getName().toLowerCase() + KEY_VALUE_DELIMITER + enabledStatus + "\n");
                writeCounter++;

                for (Setting setting : module.getSettings()) {
                    writer.write("\t" + setting.getName().toLowerCase() + KEY_VALUE_DELIMITER + setting.getAsString() + "\n");
                    writeCounter++;
                }
            }

            writer.close();
            writeCounter = 1;
        }
        catch (Exception e) {
            printWriteException(e);
        }
    }

    private void printReadException(@NotNull Exception e) {
        String exceptionMessage = e.getClass().getSimpleName() + ", " + e.getMessage();
        Palladium.getLogger().error("Error reading from config file at line " + readCounter + ": " + exceptionMessage);
    }

    private void printWriteException(Exception e) {
        Palladium.getLogger().error("Error writing to config file at line " + writeCounter);
    }

    private @NotNull Module parseModuleName(String name) {
        return Palladium.getInstance().getModuleManager().getModule(name);
    }

    private boolean parseEnabledStatus(@NotNull String string) {
        if (string.equalsIgnoreCase("enabled")) {
            return true;
        }
        else if (string.equalsIgnoreCase("disabled")) {
            return false;
        }
        else {
            throw new EnabledParseException();
        }
    }

    private @NotNull Setting parseSettingName(@NotNull Module module, String name) {
        name = name.trim();
        for (Setting setting : module.getSettings()) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        throw new SettingNotFoundException();
    }
}
