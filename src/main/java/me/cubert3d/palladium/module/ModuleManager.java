package me.cubert3d.palladium.module;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.module.modules.command.DisconnectCommand;
import me.cubert3d.palladium.module.modules.command.DropCommand;
import me.cubert3d.palladium.module.modules.command.EchoCommand;
import me.cubert3d.palladium.module.modules.command.ExecuteCommand;
import me.cubert3d.palladium.module.modules.command.HelpCommand;
import me.cubert3d.palladium.module.modules.command.MacroCommand;
import me.cubert3d.palladium.module.modules.command.MusicBoxCommand;
import me.cubert3d.palladium.module.modules.command.PalladiumCommand;
import me.cubert3d.palladium.module.modules.command.SearchCommand;
import me.cubert3d.palladium.module.modules.gui.ClickGUIModule;
import me.cubert3d.palladium.module.modules.gui.EffectListModule;
import me.cubert3d.palladium.module.modules.gui.EnabledModListModule;
import me.cubert3d.palladium.module.modules.gui.PalladiumHudModule;
import me.cubert3d.palladium.module.modules.gui.PlayerInfoModule;
import me.cubert3d.palladium.module.modules.gui.SuppliesModule;
import me.cubert3d.palladium.module.modules.movement.AutoWalkModule;
import me.cubert3d.palladium.module.modules.movement.ClickTPModule;
import me.cubert3d.palladium.module.modules.movement.EntityControlModule;
import me.cubert3d.palladium.module.modules.movement.SneakModule;
import me.cubert3d.palladium.module.modules.movement.SprintModule;
import me.cubert3d.palladium.module.modules.player.AutoDisconnectModule;
import me.cubert3d.palladium.module.modules.player.AutoToolModule;
import me.cubert3d.palladium.module.modules.player.BlinkModule;
import me.cubert3d.palladium.module.modules.player.ChatFilterModule;
import me.cubert3d.palladium.module.modules.player.ToolSaverModule;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.module.modules.render.ChamsModule;
import me.cubert3d.palladium.module.modules.render.ESPModule;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import me.cubert3d.palladium.module.modules.render.FullBrightModule;
import me.cubert3d.palladium.module.modules.render.TooltipsModule;
import me.cubert3d.palladium.module.modules.render.WeatherModule;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.exception.ModuleNotFoundException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.function.Consumer;

@ClassInfo(
        description = "Contains instances of all modules.",
        authors = "cubert3d",
        date = "3/4/2021",
        type = ClassType.MANAGER
)

public final class ModuleManager {

    private final LinkedHashSet<Module> moduleSet;

    // Store the number of modules separately, so that the modules can be counted as they are loaded.
    private int numModules;
    private int numAvailableModules;

    public ModuleManager() {
        this.moduleSet = new LinkedHashSet<>();
        this.numModules = 0;
        this.numAvailableModules = 0;
    }

    public void initialize() {
        Palladium.getLogger().info("Initializing Module Manager...");
        fillModuleSet();
        Palladium.getLogger().info("Done initializing Module Manager!");
    }

    private void fillModuleSet() {

        Palladium.getLogger().info("Filling module set...");

        // COMMANDS
        addModule(new PalladiumCommand());
        addModule(new MacroCommand());
        addModule(new HelpCommand());
        addModule(new SearchCommand());
        addModule(new EchoCommand());
        addModule(new ExecuteCommand());
        addModule(new DisconnectCommand());
        addModule(new DropCommand());
        addModule(new MusicBoxCommand());

        // GUI
        addModule(new PalladiumHudModule());
        addModule(new PlayerInfoModule());
        addModule(new EnabledModListModule());
        addModule(new SuppliesModule());
        addModule(new EffectListModule());
        addModule(new ClickGUIModule());

        // RENDER
        addModule(new AntiOverlayModule());
        addModule(new TooltipsModule());
        addModule(new FullBrightModule());
        addModule(new WeatherModule());
        addModule(new XRayModule());
        addModule(new ESPModule());
        addModule(new ChamsModule());
        addModule(new FreecamModule());

        // PLAYER
        addModule(new AutoToolModule());
        addModule(new ToolSaverModule());
        addModule(new BlinkModule());
        addModule(new ChatFilterModule());
        addModule(new AutoDisconnectModule());

        // MOVEMENT
        addModule(new AutoWalkModule());
        addModule(new SprintModule());
        addModule(new SneakModule());
        addModule(new ClickTPModule());
        addModule(new EntityControlModule());

        Palladium.getLogger().info(String.format("Done! Loaded %d modules (%d available, %d debug-only)", numModules, numAvailableModules, numModules - numAvailableModules));
    }

    private void addModule(Module module) {
        moduleSet.add(module);
        module.onLoad();
        // Update the module counters.
        numModules++;
        numAvailableModules++;
    }



    // GETTERS

    @Contract(pure = true)
    public final  @NotNull LinkedHashSet<Module> getModules() {
        return moduleSet;
    }

    public final Module getModule(String name) {
        name = name.trim();
        for (Module module : moduleSet) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        throw new ModuleNotFoundException("Could not find module of name \"" + name + "\"");
    }

    public final Optional<Module> getModuleOptional(@NotNull String name) {

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

    public final @NotNull Module getModuleByClass(Class<? extends Module> clazz) throws ModuleNotFoundException {
        for (Module module : moduleSet) {
            if (module.getClass().equals(clazz)) {
                return module;
            }
        }
        throw new ModuleNotFoundException("Could not find module of class " + clazz.getSimpleName());
    }

    public final boolean isModuleEnabled(Class<? extends Module> clazz) {
        for (Module module : moduleSet) {
            if (module.getClass().equals(clazz))
                return module.isEnabled();
        }
        return false;
    }

    public final void ifModuleEnabled(Class<? extends Module> clazz, Consumer<Module> action) {
        for (Module module : moduleSet) {
            if (module.getClass().equals(clazz) && module.isEnabled()) {
                action.accept(module);
                break;
            }
        }
    }
}
