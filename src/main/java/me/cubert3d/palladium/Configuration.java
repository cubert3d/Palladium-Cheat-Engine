package me.cubert3d.palladium;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.setting.Setting;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public final class Configuration {

    private final static String fileDirectory = "palladium";
    private final static String fileName = "config.txt";
    private final static String fullFileName = fileDirectory + "/" + fileName;

    public static final String EMPTY_LIST_PLACEHOLDER = "empty";
    public static final String KEY_VALUE_DELIMITER = ": ";
    public static final String LIST_DELIMITER = ", ";
    private static int readCounter = 0;

    public static void createFile() {
        File path = new File(fileDirectory);
        File file = new File(fullFileName);
        if (!file.exists()) {
            try {
                path.mkdir();
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadConfig() {
        System.out.println("Loading config...");

        try {

            Scanner scanner = new Scanner(new File(fullFileName));
            Module currentModule = null;

            while (scanner.hasNextLine()) {

                String data = scanner.nextLine();

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
                    }
                    catch (Exception e) {
                        printReadError();
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

                    try {
                        Setting setting = parseSettingName(currentModule, settingName);
                        setting.setFromString(settingValues);
                    }
                    catch (Exception e) {
                        printReadError();
                    }
                }

                readCounter++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Done loading config!");
        readCounter = 0;
    }

    public static void printReadError() {
        System.out.println("Error parsing config file at line " + readCounter);
    }

    public static void saveConfig() {
        try {
            FileWriter writer = new FileWriter(fullFileName);

            for (Module module : ModuleManager.getModules()) {

                // If the module is enabled, then add "enabled" on the right side of this line; if it is
                // disabled, then add "disabled."
                String enabledStatus = module.isEnabled() ? "enabled" : "disabled";

                // Write the module name, then the key-value delimiter, then whether the module is enabled or disabled.
                writer.write(module.getName().toLowerCase() + KEY_VALUE_DELIMITER + enabledStatus + "\n");

                for (Setting setting : module.getSettings()) {

                    // Check if the setting is a list setting that is empty; if it is, then a placeholder string needs
                    // to be substituted for the regular getAsString method, because that would produce an empty string,
                    // which does not parse properly.
                    String value;
                    if (setting.isListSetting() && setting.asListSetting().getList().isEmpty()) {
                        value = EMPTY_LIST_PLACEHOLDER;
                    }
                    else {
                        value = setting.getAsString();
                    }

                    writer.write("\t" + setting.getName().toLowerCase() + KEY_VALUE_DELIMITER + setting.getAsString() + "\n");
                }
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static @NotNull Module parseModuleName(String name) throws IOException {
        Optional<Module> optional = ModuleManager.getModule(name);
        if (optional.isPresent()) {
            return optional.get();
        }
        else {
            throw new IOException();
        }
    }

    private static boolean parseEnabledStatus(@NotNull String string) throws IOException {
        if (string.equalsIgnoreCase("enabled")) {
            return true;
        }
        else if (string.equalsIgnoreCase("disabled")) {
            return false;
        }
        else {
            throw new IOException();
        }
    }

    private static @NotNull Setting parseSettingName(@NotNull Module module, String name) throws IOException {
        name = name.trim();
        for (Setting setting : module.getSettings()) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        throw new IOException();
    }
}
