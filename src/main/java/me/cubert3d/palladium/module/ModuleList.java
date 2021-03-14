package me.cubert3d.palladium.module;

import me.cubert3d.palladium.module.modules.command.HelpCommand;
import me.cubert3d.palladium.module.modules.command.SearchCommand;
import me.cubert3d.palladium.module.modules.gui.EnabledModListModule;
import me.cubert3d.palladium.module.modules.movement.ClickTPModule;
import me.cubert3d.palladium.module.modules.movement.SneakModule;
import me.cubert3d.palladium.module.modules.movement.SprintModule;
import me.cubert3d.palladium.module.modules.player.AutoToolModule;
import me.cubert3d.palladium.module.modules.player.BlinkModule;
import me.cubert3d.palladium.module.modules.player.ChatFilterModule;
import me.cubert3d.palladium.module.modules.player.ToolSaverModule;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.module.modules.render.FullBrightModule;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/4/2021",
        status = "complete"
)

public final class ModuleList {

    private static final LinkedHashSet<Module> moduleSet = new LinkedHashSet<>();

    // Store the number of modules separately, so that the modules can be counted as they are loaded.
    private static int numModules;
    private static int numAvailableModules;

    public static void fillModuleMap() {

        numModules = 0;
        numAvailableModules = 0;

        // COMMANDS
        addModule(new HelpCommand());
        addModule(new SearchCommand());

        //GUI
        addModule(new EnabledModListModule());

        // RENDER
        addModule(new AntiOverlayModule());
        addModule(new FullBrightModule());
        addModule(new XRayModule());

        // PLAYER
        addModule(new AutoToolModule());
        addModule(new ToolSaverModule());
        addModule(new BlinkModule());
        addModule(new ChatFilterModule());

        // MOVEMENT
        addModule(new SprintModule());
        addModule(new SneakModule());
        addModule(new ClickTPModule());
    }

    private static void addModule(Module module) {
        moduleSet.add(module);
        module.onLoad();
        // Update the module counters.
        numModules++;
        if (module.getDevStatus().equals(ModuleDevStatus.AVAILABLE))
            numAvailableModules++;
    }

    @Contract(pure = true)
    public static @NotNull LinkedHashSet<Module> getModuleCollection() {
        return moduleSet;
    }

    public static Optional<Module> getModule(@NotNull String name) {

        Optional<Module> optional = Optional.empty();
        name = name.trim();

        for (Module module : moduleSet) {
            if (module.getName().equalsIgnoreCase(name)) {
                optional = Optional.of(module);
                break;
            }
        }
        return optional;
    }

    public static int getNumModules() {
        return numModules;
    }

    public static int getNumAvailableModules() {
        return numAvailableModules;
    }
}
