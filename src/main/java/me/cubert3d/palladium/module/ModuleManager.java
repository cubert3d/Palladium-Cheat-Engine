package me.cubert3d.palladium.module;

import me.cubert3d.palladium.module.modules.command.*;
import me.cubert3d.palladium.module.modules.gui.*;
import me.cubert3d.palladium.module.modules.movement.*;
import me.cubert3d.palladium.module.modules.player.*;
import me.cubert3d.palladium.module.modules.render.*;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/4/2021",
        status = "complete"
)

@UtilityClass
public final class ModuleManager {

    private static final LinkedHashSet<Module> moduleSet = new LinkedHashSet<>();

    // Store the number of modules separately, so that the modules can be counted as they are loaded.
    private static int numModules;
    private static int numAvailableModules;

    private ModuleManager() {}

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
        addModule(new AutoDisconnectModule());

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



    // GETTERS

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



    // CLASS GETTERS

    public static @Nullable Module getModuleByClass(Class<? extends Module> clazz) {
        for (Module module : moduleSet) {
            if (module.getClass().equals(clazz))
                return module;
        }
        return null;
    }

    public static boolean isModuleEnabled(Class<? extends Module> clazz) {
        for (Module module : moduleSet) {
            if (module.getClass().equals(clazz))
                return module.isEnabled();
        }
        return false;
    }

    public static int getNumModules() {
        return numModules;
    }

    public static int getNumAvailableModules() {
        return numAvailableModules;
    }
}