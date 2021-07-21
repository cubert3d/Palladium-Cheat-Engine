package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.command.CommandError;
import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.module.setting.Setting;
import me.cubert3d.palladium.module.setting.single.IntegerSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "3/12/2021",
        type = ClassType.MODULE
)

public final class HelpCommand extends CommandModule {

    private final IntegerSetting pageSizeSetting;

    public HelpCommand() {
        super("Help", "Lists the modules and commands, and their descriptions.");
        this.pageSizeSetting = new IntegerSetting("PageSize", "How many modules to display per page.", 10, 1, 25);
        this.addSetting(pageSizeSetting);
    }

    @Override
    protected void execute(String[] args) {

        // Parse the page number or module name.
        if (args.length > 0) {

            // Display page.
            try {
                int pageNumber = Integer.parseInt(args[0]);
                if (args.length > 1) {
                    CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
                }
                else {
                    displayPage(pageNumber);
                }
            }

            // Display module or setting description.
            catch (NumberFormatException e) {
                Optional<Module> optionalModule = Palladium.getInstance().getModuleManager().getModuleOptional(args[0]);
                if (optionalModule.isPresent()) {
                    Module module = optionalModule.get();
                    if (args.length == 1) {
                        module.displayUsages();
                    }
                    else if (args.length == 2) {
                        Optional<Setting> optionalSetting = module.getSettingOptional(args[1]);
                        if (optionalSetting.isPresent()) {
                            displaySettingDescription(module, optionalSetting.get());
                        }
                        else {
                            CommandError.sendErrorMessage(CommandError.SETTING_NOT_FOUND);
                        }
                    }
                    else {
                        CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
                    }
                }
                else {
                    CommandError.sendErrorMessage(CommandError.MODULE_NOT_FOUND);
                }
            }
        }
        else {
            displayPage(1);
        }
    }

    private void displayPage(final int pageNumber) {
        Collection<Module> page = getPage(pageNumber);

        if (page != null && page.size() > 0) {
            printToChatHud(String.format("§6Displaying page §e%d§6 of modules: ", pageNumber));
            for (Module module : page) {
                printToChatHud(module.getName() + ": " + module.getDescription());
            }
        }
        else {
            printToChatHud("No modules found");
        }
    }

    private @Nullable LinkedHashSet<Module> getPage(final int pageNumber) {

        // Cannot access a zero or negative page.
        if (pageNumber < 1) return null;

        LinkedHashSet<Module> page = new LinkedHashSet<>();

        // Used to count the index of the modules.
        int counter = 0;
        int pageSize = pageSizeSetting.getValue();

        // The first and last index of the page.
        // Calculated once instead of every iteration.
        int firstIndex = (pageNumber - 1) * pageSize;
        int lastIndex = pageNumber * pageSize;

        // Iterate through the full set of modules to build the page.
        for (Module module : Palladium.getInstance().getModuleManager().getModules()) {

            // Add the module if it is in the page range.
            if (counter >= firstIndex && counter < lastIndex) {
                page.add(module);
            }

            // If the page reaches the maximum page size, then break out of the loop.
            if (page.size() >= pageSize) {
                break;
            }

            // Increment the index-counter.
            counter++;
        }

        return page;
    }

    private void displaySettingDescription(@NotNull Module module, @NotNull Setting setting) {
        ArrayList<String> lines = setting.getDescription();
        printToChatHud(String.format("§6%s::%s: %s", module.getName(), setting.getName(), setting.getDescription().get(0)));
        for (int i = 1; i < lines.size(); i++) {
            printToChatHud(String.format("§e<< %s", setting.getDescription().get(i)));
        }
    }
}
