package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.HudRenderer;
import me.cubert3d.palladium.gui.ScreenOrientation;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "in-progress"
)

public final class EnabledModListModule extends Module {

    private static final ScreenOrientation orientation = ScreenOrientation.TOP_RIGHT;

    private final ArrayList<String> stringList = new ArrayList<>();

    public EnabledModListModule() {
        super("ModList", "Displays a list of enabled modules on your screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
        this.stringList.add("Enabled Modules");
    }

    @Override
    protected void onEnable() {
        HudRenderer.getTextManager().getTopRightStrings().addAll(stringList);
    }

    @Override
    protected void onDisable() {
        HudRenderer.getTextManager().getTopRightStrings().removeAll(stringList);
    }

    // Called when any module is enabled or disabled.
    public void onEnabledModListUpdate(@NotNull Module module) {
        if (module.isEnabled())
            addString(module.getName());
        else
            removeString(module.getName());
    }

    private void addString(String string) {
        stringList.add(string);
        if (shouldUpdateOnScreenStrings())
            HudRenderer.getTextManager().getTopRightStrings().add(string);
    }

    private void removeString(String string) {
        stringList.remove(string);
        if (shouldUpdateOnScreenStrings())
            HudRenderer.getTextManager().getTopRightStrings().remove(string);
    }

    private boolean shouldUpdateOnScreenStrings() {
        return this.isEnabled();
    }
}
