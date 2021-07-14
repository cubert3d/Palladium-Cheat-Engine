package me.cubert3d.palladium.module.modules;

import me.cubert3d.palladium.input.CommandError;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.module.setting.list.ListSetting;
import me.cubert3d.palladium.module.setting.single.SingleSetting;
import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@ClassInfo(
        description = "A module that can be turned on and off.",
        authors = "cubert3d",
        date = "6/29/2021",
        type = ClassType.MODULE
)

public abstract class ToggleModule extends Module {

    private boolean enabled;

    protected ToggleModule(String name, String description) {
        super(name, description);
        this.enabled = false;
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.TOGGLE;
    }

    @Override
    public final boolean isEnabled() {
        return enabled;
    }

    @Override
    public final void enable() {
        if (isAvailable()) {
            enabled = true;
            onEnable();
            Common.sendMessage(this.getName() + " is now enabled");
        }
    }

    @Override
    public final void disable() {
        if (isAvailable()) {
            enabled = false;
            onDisable();
            Common.sendMessage(this.getName() + " is now disabled");
        }
    }

    @Override
    public final boolean toggle() {
        if (isEnabled())
            disable();
        else
            enable();
        return isEnabled();
    }

    @Override
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // Called when this module is enabled.
    protected void onEnable() {}

    // Called when this module is disabled.
    protected void onDisable() {}

    @Override
    public final void onKeyPressed(int mode) {
        if (mode == 1) {
            this.toggle();
        }
    }

    @Override
    public void parseArgs(String @NotNull [] args) {
        if (args.length == 0) {
            if (this.isEnabled()) {
                Common.sendMessage(getName() + " is currently enabled");
            }
            else {
                Common.sendMessage(getName() + " is currently disabled");
            }
        }
        else if (args.length == 1) {
            /*
            Check if the first argument is enable/disable/toggle before looking for a setting.
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
                    this.displayAllSettings();
                    break;
                default:
                    Optional<Setting> optional = getSettingOptional(args[0]);
                    if (optional.isPresent()) {
                        this.displaySetting(optional.get());
                    }
                    else {
                        CommandError.sendErrorMessage(CommandError.SETTING_NOT_FOUND);
                    }
                    break;
            }
        }
        else {

            /*
            When there is more than one argument, assume that the player is trying to change a setting.
             */

            Optional<Setting> optional = getSettingOptional(args[0]);
            Setting setting;

            if (optional.isPresent()) {
                setting = optional.get();

            }
            else {
                CommandError.sendErrorMessage(CommandError.SETTING_NOT_FOUND);
                return;
            }

            if (args.length == 2) {

                // "<command> <setting> reset": reset the value of the setting to the default
                if (args[1].equalsIgnoreCase("reset")) {
                    setting.reset();
                    this.onChangeSetting();
                    Common.sendMessage(setting.getName() + " reset to default");
                }
                // "<command> <single-setting> [value]": change the value of the setting
                else if (!setting.isListSetting()) {
                    SingleSetting singleSetting = setting.asSingleSetting();
                    String input = args[1];

                    try {
                        singleSetting.setFromString(input);
                    }
                    catch (Exception e) {
                        CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                    }

                    this.onChangeSetting();
                    Common.sendMessage(setting.getName() + " is now set to " + setting.getAsString());
                }
                // Two arguments are not enough for list-type settings.
                else {
                    CommandError.sendErrorMessage(CommandError.TOO_FEW_ARGUMENTS);
                }
            }
            else if (args.length == 3) {

                if (setting.isListSetting()) {

                    ListSetting listSetting = setting.asListSetting();
                    Optional optionalElement = listSetting.convertStringToElement(args[2]);
                    Object element;

                    if (optionalElement.isPresent()) {
                        element = optionalElement.get();
                    }
                    else {
                        CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                        return;
                    }

                    // "<command> <list-setting> add/remove [value]":
                    if (args[1].equalsIgnoreCase("add")) {
                        listSetting.addElement(element);
                        Common.sendMessage("Added \"" + args[2] + "\" to " + setting.getName());
                        this.onChangeSetting();
                    }
                    else if (args[1].equalsIgnoreCase("remove")) {
                        listSetting.removeElement(element);
                        Common.sendMessage("Removed \"" + args[2] + "\" from " + setting.getName());
                        this.onChangeSetting();
                    }
                    else {
                        CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                    }
                }
                // Three arguments are too many for single-type settings.
                else {
                    CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
                }
            }
            // The number of arguments should never exceed three (for toggle modules).
            else {
                CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
            }
        }
    }
}
