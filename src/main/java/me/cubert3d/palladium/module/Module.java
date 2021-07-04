package me.cubert3d.palladium.module;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.cmd.CommandError;
import me.cubert3d.palladium.module.setting.*;
import me.cubert3d.palladium.module.setting.list.*;
import me.cubert3d.palladium.module.setting.single.*;
import me.cubert3d.palladium.util.Conversion;
import me.cubert3d.palladium.util.annotation.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.InternalOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/3/2021",
        status = "in-progress"
)

public abstract class Module implements Named {

    // The name of this module must be unique.
    private final String name;
    // Should be brief and concise.
    private final String description;

    private final ModuleType moduleType;
    private final Set<Setting> settings = new HashSet<>();
    private final KeyBindingSetting bindingSetting;

    private final ModuleDevStatus status;

    protected Module(String name, String description, ModuleType moduleType, ModuleDevStatus status) {
        this.name = name;
        this.description = description;
        this.moduleType = moduleType;
        this.status = status;
        this.bindingSetting = new KeyBindingSetting("Binding", null);
        this.addSetting(bindingSetting);
        this.onConstruct();
    }

    @Override
    public final boolean equals(Object obj) {
        // Two modules should be equal if their names are equal, case-sensitively.
        if (obj instanceof Module) {
            Module otherModule = (Module) obj;
            return this.getName().equals(otherModule.getName());
        }
        return false;
    }

    @Override
    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public Optional<String> getPrimaryInfo() {
        return Optional.empty();
    }

    public final ModuleType getType() {
        return moduleType;
    }

    public final ModuleDevStatus getDevStatus() {
        return status;
    }

    public final boolean isAvailable() {
        return status.equals(ModuleDevStatus.AVAILABLE) || Palladium.isDebugModeEnabled();
    }



    // ENABLE

    public abstract boolean isEnabled();

    public abstract void enable();

    public abstract void disable();

    public abstract boolean toggle();

    // This method is to be used in the config reading, and not anywhere else; it skips
    // over the onEnable and onDisable methods so that no unwanted effects occur when
    // loading the modules.
    public abstract void setEnabled(boolean enabled);



    // SETTING

    public final Set<Setting> getSettings() {
        return settings;
    }

    // Nullable method with no Optional, used for code references,
    // where the setting's existence should be guaranteed.
    @InternalOnly
    public final @NotNull Setting getSetting(String name) {
        name = name.trim();
        for (Setting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name))
                return setting;
        }
        throw new IllegalArgumentException();
    }

    // Not-null method that returns an optional, used for user input,
    // where the setting may or may not exist.
    public final Optional<Setting> getSettingOptional(String name) {
        name = name.trim();
        for (Setting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name))
                return Optional.of(setting);
        }
        return Optional.empty();
    }

    protected final void addSetting(@NotNull Setting setting) {
        if (isSettingNameValid(setting.getName()))
            settings.add(setting);
        else
            throw new IllegalArgumentException();
    }

    private boolean isSettingNameValid(String name) {

        // First, check if the name is one of the default
        // forbidden names ("enable," "disable," "toggle").
        for (String forbiddenSettingName : Setting.FORBIDDEN_SETTING_NAMES) {
            if (forbiddenSettingName.equalsIgnoreCase(name))
                return false;
        }

        // Second, check if this module already has a setting
        // of the same name.
        for (Setting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name))
                return false;
        }

        // If both checks are passed, then return true.
        return true;
    }

    protected void onChangeSetting() {

    }

    public final KeyBindingSetting getBinding() {
        return bindingSetting;
    }



    // ON-UPDATE METHODS

    // Called when the abstract module's constructor is used.
    protected void onConstruct() {}

    // Called when this module is loaded by the module manager.
    protected void onLoad() {}

    public abstract void onKeyPressed(int mode);

    public abstract void parseArgs(String @NotNull [] args);



    // EXTRA

    // Used to display a setting by passing the name of the setting.
    protected void displaySettingFromString(String settingName) {
        Optional<Setting> optional = getSettingOptional(settingName);
        if (optional.isPresent()) {
            this.displaySetting(optional.get());
        }
        else {
            Common.sendMessage("Setting not found");
        }
    }

    // Used to a display a setting that has already been gotten.
    protected void displaySetting(@NotNull Setting setting) {
        if (setting.isSet()) {
            Common.sendMessage(this.getName() + ", " + setting.getName() + ": " + setting.getAsString());
        }
        else {
            Common.sendMessage(this.getName() + ", " + setting.getName() + " is not set");
        }
    }

    protected void displayAllSettings() {
        String listOfSettings;
        if (this.getSettings().size() > 0) {

            // What will be sent to the player's chatHUD.
            listOfSettings = this.getName() + " settings: ";
            // Counter for the loop through the settings.
            int counter = 0;
            // Limit the number of settings displayed to 50 at the most.
            int limit = Math.min(this.getSettings().size(), StringListSetting.MAX_DISPLAY_COUNT);

            for (Setting setting : this.getSettings()) {

                // Make sure this loop doesn't exceed that limit.
                if (counter < limit) {
                    listOfSettings = listOfSettings.concat(setting.getName());
                    if (!setting.isListSetting() && setting.isSet()) {
                        listOfSettings = listOfSettings.concat(" (" + setting.getAsString() + ")");
                    }
                }

                // Append a comma and space if this is not the last iteration in the list.
                if (counter < (limit - 1)) {
                    listOfSettings = listOfSettings.concat(", ");
                }
                counter++;
            }
        }
        else {
            listOfSettings = this.getName() + " has no settings";
        }
        Common.sendMessage(listOfSettings);
    }
}
