package me.cubert3d.palladium.config;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.exception.EnabledParseException;
import me.cubert3d.palladium.util.exception.ParseLineException;
import me.cubert3d.palladium.util.exception.ReadException;
import me.cubert3d.palladium.util.exception.SettingNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.util.Scanner;

@ClassInfo(
        description = "Loads and saves the config, which holds all modules and their settings.",
        authors = "cubert3d",
        date = "6/27/2021",
        type = ClassType.MAIN
)

public final class ModuleConfig extends AbstractConfig {

    public ModuleConfig() {
        super("modules.txt");
    }

    @Override
    protected final void read() {
        try {

            Scanner scanner = new Scanner(getFile());
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

                    if (strings.length >= 2) {
                        moduleName = strings[0];
                        enabledStatus = strings[1];
                        try {
                            module = parseModuleName(moduleName);
                            enabled = parseEnabledStatus(enabledStatus);
                        } catch (ReadException e) {
                            printReadException(e);
                        }
                    }
                    else {
                        printReadException(new ParseLineException());
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

    @Override
    protected final void write() {
        try {
            FileWriter writer = new FileWriter(getFile());

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
            throw new EnabledParseException("Unable to parse enabled status \"" + string + "\"");
        }
    }

    private @NotNull Setting parseSettingName(@NotNull Module module, String name) {
        name = name.trim();
        for (Setting setting : module.getSettings()) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        throw new SettingNotFoundException("Unable to parse setting \"" + name + "\" in module " + module.getName());
    }
}
