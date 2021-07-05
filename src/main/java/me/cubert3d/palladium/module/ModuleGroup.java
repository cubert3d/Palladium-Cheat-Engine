package me.cubert3d.palladium.module;

import me.cubert3d.palladium.module.modules.gui.*;
import me.cubert3d.palladium.module.modules.movement.ClickTPModule;
import me.cubert3d.palladium.module.modules.movement.SneakModule;
import me.cubert3d.palladium.module.modules.movement.SprintModule;
import me.cubert3d.palladium.module.modules.player.*;
import me.cubert3d.palladium.module.modules.render.AntiOverlayModule;
import me.cubert3d.palladium.module.modules.render.ChamsModule;
import me.cubert3d.palladium.module.modules.render.ESPModule;
import me.cubert3d.palladium.module.modules.render.FreecamModule;
import me.cubert3d.palladium.module.modules.render.FullBrightModule;
import me.cubert3d.palladium.module.modules.render.TooltipsModule;
import me.cubert3d.palladium.module.modules.render.XRayModule;
import me.cubert3d.palladium.util.annotation.Named;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/*
    Like a module category, but the user will be able to define their own.
    Inspired by Nodus.
 */

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/6/2021"
)

public final class ModuleGroup implements Named {

    private String name;
    private final ArrayList<Module> modules = new ArrayList<>();

    public ModuleGroup(String name) {
        this.name = name;
    }

    public ModuleGroup(String name, Class<? extends Module> @NotNull ... modules) {
        this.name = name;
        for (Class<? extends Module> moduleClass : modules) {
            this.modules.add(ModuleManager.getModuleByClass(moduleClass));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final ArrayList<Module> getModules() {
        return modules;
    }

    public final boolean addModule(@NotNull Module module) {
        if (module.getType().equals(ModuleType.TOGGLE)) {
            return modules.add(module);
        }
        else {
            return false;
        }
    }



    public static final ModuleGroup MODULES_GUI = new ModuleGroup("GUI",
            PalladiumHudModule.class,
            ClickGUIModule.class,
            PlayerInfoModule.class,
            EnabledModListModule.class,
            EffectListModule.class,
            SuppliesModule.class);

    public static final ModuleGroup MODULES_RENDER = new ModuleGroup("Render",
            AntiOverlayModule.class,
            TooltipsModule.class,
            FullBrightModule.class,
            XRayModule.class,
            ChamsModule.class,
            ESPModule.class,
            FreecamModule.class);

    public static final ModuleGroup MODULES_MOVEMENT = new ModuleGroup("Movement",
            SneakModule.class,
            SprintModule.class,
            ClickTPModule.class);

    public static final ModuleGroup MODULES_PLAYER = new ModuleGroup("Player",
            ChatFilterModule.class,
            PacketManagerModule.class,
            AutoDisconnectModule.class,
            AutoToolModule.class,
            ToolSaverModule.class,
            BlinkModule.class);
}
