package me.cubert3d.palladium.module;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.module.modules.combat.KillAuraModule;
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
import me.cubert3d.palladium.module.modules.render.ESPModule;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import me.cubert3d.palladium.module.modules.render.FullBrightModule;
import me.cubert3d.palladium.module.modules.render.TooltipsModule;
import me.cubert3d.palladium.module.modules.render.WeatherModule;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import me.cubert3d.palladium.util.exception.ModuleNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@ClassInfo(
        description = "Manages all module groups, whether the default groups, or groups created by the player.",
        authors = "cubert3d",
        date = "7/5/2021",
        type = ClassType.MANAGER
)

public final class ModuleGroupManager {

    private final Map<String, ModuleGroup> groups;
    private final ModuleManager moduleManager;

    public ModuleGroupManager(ModuleManager moduleManager) {
        this.groups = new LinkedHashMap<>();
        this.moduleManager = moduleManager;
    }

    public void initialize() {
        Palladium.getLogger().info("Loading default module groups...");
        loadDefaultGroups();
        Palladium.getLogger().info("Done loading default module groups!");
    }

    void loadDefaultGroups() {
        try {
            ModuleGroup guiModules = newModuleGroup("GUI",
                    PalladiumHudModule.class,
                    ClickGUIModule.class,
                    PlayerInfoModule.class,
                    EnabledModListModule.class,
                    EffectListModule.class,
                    SuppliesModule.class);

            ModuleGroup renderModules = newModuleGroup("Render",
                    AntiOverlayModule.class,
                    TooltipsModule.class,
                    FullBrightModule.class,
                    XRayModule.class,
                    ESPModule.class,
                    FreecamModule.class,
                    WeatherModule.class);

            ModuleGroup movementModules = newModuleGroup("Movement",
                    AutoWalkModule.class,
                    SneakModule.class,
                    SprintModule.class,
                    ClickTPModule.class,
                    EntityControlModule.class);

            ModuleGroup playerModules = newModuleGroup("Player",
                    ChatFilterModule.class,
                    AutoDisconnectModule.class,
                    AutoToolModule.class,
                    ToolSaverModule.class,
                    BlinkModule.class);

            ModuleGroup combatModules = newModuleGroup("Combat",
                    KillAuraModule.class);

            addModuleGroup(guiModules);
            addModuleGroup(renderModules);
            addModuleGroup(movementModules);
            addModuleGroup(playerModules);
            addModuleGroup(combatModules);
        }
        catch (ModuleNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final @NotNull ModuleGroup newModuleGroup(@NotNull String name) {
        ModuleGroup group = new ModuleGroup(name.trim(), new ArrayList<>());
        addModuleGroup(group);
        return group;
    }

    @SafeVarargs
    public final @NotNull ModuleGroup newModuleGroup(String name, Class<? extends Module> @NotNull ... moduleClasses) throws ModuleNotFoundException {
        ModuleGroup group;
        ArrayList<Module> modules = new ArrayList<>();

        for (Class<? extends Module> moduleClass : moduleClasses) {
            try {
                modules.add(moduleManager.getModuleByClass(moduleClass));
            }
            catch (ModuleNotFoundException e) {
                e.printStackTrace();
            }
        }
        group = new ModuleGroup(name.trim(), modules);
        //addModuleGroup(group);

        return group;
    }

    public ModuleGroup getGroup(@NotNull String id) {
        return groups.get(id.trim().toLowerCase());
    }

    public Map<String, ModuleGroup> getGroups() {
        return groups;
    }

    private void addModuleGroup(@NotNull ModuleGroup group) {
        Palladium.getLogger().info("Added module group \"" + group.getID() + "\"");
        groups.put(group.getID(), group);
    }
}
