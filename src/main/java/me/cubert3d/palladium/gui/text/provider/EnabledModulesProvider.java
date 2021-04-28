package me.cubert3d.palladium.gui.text.provider;

import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class EnabledModulesProvider extends TextProvider {

    private int numberEnabledModules = 0;

    public EnabledModulesProvider() {

    }

    @Override
    public @NotNull ColorText getHeader() {
        return new ColorText("Enabled Modules (" + numberEnabledModules + ")");
    }

    @Override
    public @NotNull ArrayList<ColorText> getBody() {
        ArrayList<ColorText> text = new ArrayList<>();

        int counter = 0;
        for (Module module : ModuleManager.getModules()) {
            if (module.isEnabled()) {
                text.add(new ColorText(module.getName()));
                counter++;
            }
        }
        numberEnabledModules = counter;

        return text;
    }
}
