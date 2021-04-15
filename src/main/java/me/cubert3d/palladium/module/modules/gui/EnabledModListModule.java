package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.TextHudRenderer;
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
        TextHudRenderer.getTextManager().setTopRightSupplier(modListSupplier);
    }

    @Override
    protected void onDisable() {
        TextHudRenderer.getTextManager().clearTopRightSupplier();
    }

    static {
        modListSupplier = () -> {
            ArrayList<String> strings = new ArrayList<>();

            int counter = 0;
            for (Module module : ModuleManager.getModules()) {
                if (module.isEnabled()) {
                    strings.add(module.getName());
                    counter++;
                }
            }
            strings.add(0, "Enabled Modules (" + counter + ")");

            return strings;
        };
    }
}
