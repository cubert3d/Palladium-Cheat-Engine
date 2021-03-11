package me.cubert3d.palladium.module;

import me.cubert3d.palladium.module.modules.command.ListModulesCommand;
import me.cubert3d.palladium.module.modules.gui.EnabledModListModule;
import me.cubert3d.palladium.module.modules.movement.SneakModule;
import me.cubert3d.palladium.module.modules.movement.SprintModule;
import me.cubert3d.palladium.module.modules.player.AutoToolModule;
import me.cubert3d.palladium.module.modules.player.BlinkModule;
import me.cubert3d.palladium.module.modules.player.ToolSaverModule;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.module.modules.render.FullBrightModule;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ModuleList {

    private static final Map<String, AbstractModule> moduleMap = new HashMap<>();

    // Store the number of modules separately, so that the modules can be counted as they are loaded.
    private static int numModules;
    private static int numAvailableModules;

    public static void fillModuleMap() {

        numModules = 0;
        numAvailableModules = 0;

        // COMMANDS
        addModule(new ListModulesCommand());

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

        // MOVEMENT
        addModule(new SprintModule());
        addModule(new SneakModule());
    }

    private static void addModule(AbstractModule module) {
        moduleMap.put(module.getName().toLowerCase(), module);
        module.onLoad();
        // Update the module counters.
        numModules++;
        if (module.getDevStatus().equals(ModuleDevStatus.AVAILABLE))
            numAvailableModules++;
    }

    public static Map<String, AbstractModule> getModuleMap() {
        return moduleMap;
    }

    @Contract(pure = true)
    public static @NotNull Collection<AbstractModule> getModuleCollection() {
        return moduleMap.values();
    }

    public static AbstractModule getModule(@NotNull String name) {
        return getModuleMap().get(name.toLowerCase());
    }

    public static int getNumModules() {
        return numModules;
    }

    public static int getNumAvailableModules() {
        return numAvailableModules;
    }
}
