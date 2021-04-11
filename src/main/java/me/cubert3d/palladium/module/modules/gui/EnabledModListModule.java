package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.HudRenderer;
import me.cubert3d.palladium.gui.HudTextManager;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.ArrayList;
import java.util.function.Supplier;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "in-progress"
)

public final class EnabledModListModule extends Module {

    private static final Supplier<ArrayList<String>> modListSupplier;

    public EnabledModListModule() {
        super("ModList", "Displays a list of enabled modules on your screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }

    @Override
    protected void onEnable() {
        HudRenderer.getTextManager().setTopRightSupplier(modListSupplier);
    }

    @Override
    protected void onDisable() {
        HudRenderer.getTextManager().clearTopRightSupplier();
    }

    static {
        modListSupplier = () -> {
            ArrayList<String> strings = new ArrayList<>();

            strings.add("Enabled Modules");
            for (Module module : ModuleManager.getModules()) {
                if (module.isEnabled()) {
                    strings.add(module.getName());
                }
            }

            return strings;
        };
    }
}
