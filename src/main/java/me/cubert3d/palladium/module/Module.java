package me.cubert3d.palladium.module;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.cmd.CommandError;
import me.cubert3d.palladium.module.setting.*;
import me.cubert3d.palladium.module.setting.list.*;
import me.cubert3d.palladium.module.setting.single.*;
import me.cubert3d.palladium.util.Conversion;
import me.cubert3d.palladium.util.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.InternalOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private boolean enabled;
    private final ModuleDevStatus status;
    private final Set<BaseSetting> settings = new HashSet<>();

    protected Module(String name, String description, ModuleType moduleType, ModuleDevStatus status) {
        this.name = name;
        this.description = description;
        this.moduleType = moduleType;
        this.enabled = false;
        this.status = status;
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

    public final boolean isEnabled() {
        if (moduleType.equals(ModuleType.TOGGLE))
            return enabled;
        else
            return false;
    }

    public final void enable() {
        if (status.equals(ModuleDevStatus.AVAILABLE) || Palladium.isDebugModeEnabled()) {
            if (moduleType.equals(ModuleType.TOGGLE)) {
                enabled = true;
                onEnable();
                Common.sendMessage(this.getName() + " is now enabled");
            }
        }
    }

    public final void disable() {
        if (status.equals(ModuleDevStatus.AVAILABLE) || Palladium.isDebugModeEnabled()) {
            if (moduleType.equals(ModuleType.TOGGLE)) {
                enabled = false;
                onDisable();
                Common.sendMessage(this.getName() + " is now disabled");
            }
        }
    }

    public final boolean toggle() {
        if (moduleType.equals(ModuleType.TOGGLE)) {
            if (isEnabled())
                disable();
            else
                enable();
            return isEnabled();
        }
        return false;
    }



    // SETTING

    public final Set<BaseSetting> getSettings() {
        return settings;
    }

    // Nullable method with no Optional, used for code references,
    // where the setting's existence should be guaranteed.
    @InternalOnly
    public final @Nullable BaseSetting getSetting(String name) {
        name = name.trim();
        for (BaseSetting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name))
                return setting;
        }
        return null;
    }

    // Not-null method that returns an optional, used for user input,
    // where the setting may or may not exist.
    public final Optional<BaseSetting> getSettingOptional(String name) {
        name = name.trim();
        for (BaseSetting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name))
                return Optional.of(setting);
        }
        return Optional.empty();
    }

    protected final void addSetting(@NotNull BaseSetting setting) {
        if (isSettingNameValid(setting.getName()))
            settings.add(setting);
        else
            throw new IllegalArgumentException();
    }

    private boolean isSettingNameValid(String name) {

        // First, check if the name is one of the default
        // forbidden names ("enable," "disable," "toggle").
        for (String forbiddenSettingName : BaseSetting.FORBIDDEN_SETTING_NAMES) {
            if (forbiddenSettingName.equalsIgnoreCase(name))
                return false;
        }

        // Second, check if this module already has a setting
        // of the same name.
        for (BaseSetting setting : settings) {
            if (setting.getName().equalsIgnoreCase(name))
                return false;
        }

        // If both checks are passed, then return true.
        return true;
    }

    protected void onChangeSetting() {

    }



    // ON-UPDATE METHODS

    // Called when the abstract module's constructor is used.
    protected void onConstruct() {}

    // Called when this module is loaded by the module manager.
    protected void onLoad() {}

    // Called when this module is enabled.
    protected void onEnable() {}

    // Called when this module is disabled.
    protected void onDisable() {}

    public void execute(String @NotNull [] args) {
        if (args.length == 0) {
            if (moduleType.equals(ModuleType.TOGGLE)) {
                if (this.isEnabled()) {
                    Common.sendMessage(this.getName() + " is currently enabled");
                }
                else {
                    Common.sendMessage(this.getName() + " is currently disabled");
                }
            }
        }
        else if (args.length == 1) {
            if (moduleType.equals(ModuleType.TOGGLE)) {
                /*
                If this module is a toggle-able module, then check if the first
                argument is enable/disable/toggle before looking for a setting.
                 */
                switch (args[0].toLowerCase()) {
                    case "enable":
                        this.enable();
                        break;
                    case "disable":
                        this.disable();
                        break;
                    case "toggle":
                        this.toggle();
                        break;
                    case "settings":
                        listSettings();
                        break;
                    default:
                        listSetting(args[0]);
                        break;
                }
            }
            else {
                if (args[0].equalsIgnoreCase("settings")) {
                    listSettings();
                }
                else {
                    listSetting(args[0]);
                }
            }
        }
        else {

            Optional<BaseSetting> optional = getSettingOptional(args[0]);
            BaseSetting setting;

            if (!optional.isPresent()) {
                Common.sendMessage("Setting not found!");
                return;
            }
            else {
                setting = optional.get();
            }

            if (args.length == 2) {

                // "<command> <setting> reset": reset the value of the setting to the default
                if (args[1].equalsIgnoreCase("reset")) {
                    setting.reset();
                    this.onChangeSetting();
                    Common.sendMessage(setting.getName() + " reset to default");
                }

                // "<command> <single-setting> [value]": change the value of the setting
                else if (setting instanceof SingleSetting) {
                    SingleSetting<?> singleSetting = (SingleSetting<?>) setting;

                    switch (singleSetting.getType()) {
                        case BOOLEAN:
                            Optional<Boolean> optionalBoolean = Conversion.parseBoolean(args[1]);
                            if (optionalBoolean.isPresent()) {
                                ((BooleanSetting) singleSetting).setValue(optionalBoolean.get());
                            }
                            else {
                                CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                                return;
                            }
                            break;
                        case INTEGER:
                            Optional<Integer> optionalInteger = Conversion.parseInteger(args[1]);
                            if (optionalInteger.isPresent()) {
                                ((IntegerSetting) singleSetting).setValue(optionalInteger.get());
                            }
                            else {
                                CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                                return;
                            }
                            break;
                        case DOUBLE:
                            Optional<Double> optionalDouble = Conversion.parseDouble(args[1]);
                            if (optionalDouble.isPresent()) {
                                ((DoubleSetting) singleSetting).setValue(optionalDouble.get());
                            }
                            else {
                                CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                                return;
                            }
                            break;
                        case STRING:
                            ((StringSetting) singleSetting).setValue(args[1]);
                            break;
                    }

                    this.onChangeSetting();
                    Common.sendMessage(setting.getName() + " is now set to " + ((SingleSetting<?>) setting).getValue().toString());
                }
            }
            else if (args.length == 3) {

                if (setting.isListSetting()) {

                    // "<command> <list-setting> add/remove [value]":
                    if (args[1].equalsIgnoreCase("add")) {
                        switch (setting.getType()) {
                            case LIST_STRING:
                                ((StringListSetting) setting).addElement(args[2]);
                                Common.sendMessage("Added \"" + args[2] + "\" to " + setting.getName());
                                break;
                            case LIST_BLOCK:
                                Block block = Common.getBlockFromString(args[2]);
                                ((BlockListSetting) setting).addElement(block);
                                Common.sendMessage("Added " + block.getName().getString() + " to " + setting.getName());
                                break;
                            case LIST_ENTITY:
                                EntityType<? extends Entity> entityType = Common.getEntityTypeFromString(args[2]);
                                ((EntityListSetting) setting).addElement(entityType);
                                Common.sendMessage("Added " + entityType.getName().getString() + " to " + setting.getName());
                                break;
                            case LIST_ITEM:
                                Item item = Common.getItemFromString(args[2]);
                                ((ItemListSetting) setting).addElement(item);
                                Common.sendMessage("Added " + item.getName().getString() + " to " + setting.getName());
                                break;
                        }
                        this.onChangeSetting();
                    }
                    else if (args[1].equalsIgnoreCase("remove")) {
                        switch (setting.getType()) {
                            case LIST_STRING:
                                ((StringListSetting) setting).removeElement(args[2]);
                                Common.sendMessage("Removed \"" + args[2] + "\" from " + setting.getName());
                                break;
                            case LIST_BLOCK:
                                Block block = Common.getBlockFromString(args[2]);
                                ((BlockListSetting) setting).removeElement(block);
                                Common.sendMessage("Removed " + block.getName().getString() + " from " + setting.getName());
                                break;
                            case LIST_ENTITY:
                                EntityType<? extends Entity> entityType = Common.getEntityTypeFromString(args[2]);
                                Common.sendMessage("Removed " + entityType.getName().getString() + " from " + setting.getName());
                                ((EntityListSetting) setting).removeElement(entityType);
                                break;
                            case LIST_ITEM:
                                Item item = Common.getItemFromString(args[2]);
                                ((ItemListSetting) setting).removeElement(item);
                                Common.sendMessage("Removed " + item.getName().getString() + " from " + setting.getName());
                                break;
                        }
                        this.onChangeSetting();
                    }
                    else {
                        CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                    }
                }
                else {
                    CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                }
            }
            else {
                CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
            }
        }


        /*
        Check the number of arguments:

        1: toggle, or check the value(s) of a setting (DONE)
        2: change the value of a single-type-setting
            or reset any kind of setting (DONE)
        3: add or remove from a list-type-setting
         */
    }



    // EXTRA

    private void listSetting(String settingName) {
        Optional<BaseSetting> optional = getSettingOptional(settingName);
        if (optional.isPresent()) {
            Common.sendMessage(optional.get().getName() + ": " + optional.get().toString());
        }
        else {
            Common.sendMessage("Setting not found!");
        }
    }

    private void listSettings() {
        String listOfSettings;
        if (this.getSettings().size() > 0) {

            // What will be sent to the player's chatHUD.
            listOfSettings = this.getName() + " settings: ";
            // Counter for the loop through the settings.
            int counter = 0;
            // Limit the number of settings displayed to 50 at the most.
            int limit = Math.min(this.getSettings().size(), StringListSetting.MAX_DISPLAY_COUNT);

            for (BaseSetting setting : this.getSettings()) {

                // Make sure this loop doesn't exceed that limit.
                if (counter < limit) {
                    listOfSettings = listOfSettings.concat(setting.getName());
                    if (!setting.isListSetting())
                        listOfSettings = listOfSettings.concat(" (" + setting.toString() + ")");
                }

                // Append a comma and space if this is not the last iteration in the list.
                if (counter < (limit - 1))
                    listOfSettings = listOfSettings.concat(", ");
                counter++;
            }
        }
        else {
            listOfSettings = this.getName() + " has no settings";
        }
        Common.sendMessage(listOfSettings);
    }
}
