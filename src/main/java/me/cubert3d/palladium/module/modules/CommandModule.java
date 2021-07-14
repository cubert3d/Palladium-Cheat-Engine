package me.cubert3d.palladium.module.modules;

import me.cubert3d.palladium.input.CommandError;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.module.setting.list.ListSetting;
import me.cubert3d.palladium.module.setting.single.SingleSetting;
import me.cubert3d.palladium.module.setting.single.StringSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@ClassInfo(
        description = "A module that is executed once, like a command.",
        authors = "cubert3d",
        date = "6/29/2021",
        type = ClassType.MODULE
)

public abstract class CommandModule extends Module {

    protected CommandModule(String name, String description) {
        super(name, description);
        // Let the player set args that are automatically passed to the execute() method when the bound key is pressed.
        this.addSetting(new StringSetting("Arguments", ""));
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.COMMAND;
    }

    @Override
    public final boolean isEnabled() {
        return false;
    }

    @Override
    public final void enable() {

    }

    @Override
    public final void disable() {

    }

    @Override
    public final boolean toggle() {
        return false;
    }

    @Override
    public final void setEnabled(boolean enabled) {

    }

    @Override
    public void onKeyPressed(int mode) {
        if (mode == 1) {
            String[] args = getSetting("Arguments").asStringSetting().getValue().split(" ");
            this.execute(args);
        }
    }

    @Override
    public void parseArgs(String @NotNull [] args) {
        if (args.length > 0) {

            Optional<Setting> optionalSetting = getSettingOptional(args[0]);

            if (args.length == 1 && args[0].equalsIgnoreCase("settings")) {
                this.displayAllSettings();
                return;
            }
            else {

                // If there is more than 1 argument, then check if the player is trying
                // to update a setting.
                if (optionalSetting.isPresent()) {
                    Setting setting = optionalSetting.get();
                    switch (args.length) {

                        case 1:
                            this.displaySetting(setting);
                            break;

                        case 2:

                            /*
                            Only two arguments are used for single-type settings.

                            label: module name
                            args[0]: setting name
                            args[1]: "reset" or new setting value
                             */

                            // Check if the player is resetting the setting before trying to parse the input.

                            // "<command> <setting> reset": reset the value of the setting to the default
                            if (args[1].equalsIgnoreCase("reset")) {
                                setting.reset();
                                this.onChangeSetting();
                                printToChatHud(setting.getName() + " reset to default");
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
                                printToChatHud(setting.getName() + " is now set to " + setting.getAsString());
                            }
                            // Two arguments are not enough for list-type settings.
                            else {
                                CommandError.sendErrorMessage(CommandError.TOO_FEW_ARGUMENTS);
                            }
                            break;

                        case 3:
                            /*
                            Three arguments are needed to update list-type settings, because adding or removing
                            needs to be specified.

                            label: module name
                            args[0]: setting name
                            args[1]: add/remove
                            args[2]: element
                             */

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
                                    printToChatHud("Added \"" + args[2] + "\" to " + setting.getName());
                                    this.onChangeSetting();
                                }
                                else if (args[1].equalsIgnoreCase("remove")) {
                                    listSetting.removeElement(element);
                                    printToChatHud("Removed \"" + args[2] + "\" from " + setting.getName());
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
                            break;

                        default:
                            CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
                            break;
                    }
                    return;
                }
            }
        }
        this.execute(args);
    }

    protected abstract void execute(String[] args);
}
