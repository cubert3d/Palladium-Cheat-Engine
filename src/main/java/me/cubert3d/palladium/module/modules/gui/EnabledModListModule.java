package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.TextList;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.ArrayList;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "in-progress"
)

public final class EnabledModListModule extends Module {

    public static final TextList modList;

    public EnabledModListModule() {
        super("ModList", "Displays a list of enabled modules on your screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }

    @Override
    protected void onEnable() {
        //TextHudRenderer.getTextManager().setTopRightList(modList);
    }

    @Override
    protected void onDisable() {
        //TextHudRenderer.getTextManager().clearTopRightList();
    }

    static {
        modList = new TextList(
                () -> {
                    int counter = 0;
                    for (Module module : ModuleManager.getModules()) {
                        if (module.isEnabled()) {
                            counter++;
                        }
                    }
                    return new ColorText("Enabled Modules (" + counter + ")");
                },
                () -> {
                    ArrayList<ColorText> text = new ArrayList<>();

                    for (Module module : ModuleManager.getModules()) {
                        if (module.isEnabled()) {
                            text.add(new ColorText(module.getName()));
                        }
                    }

                    return text;
                }
        );
    }
}
