package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.text.TextProvider;
import me.cubert3d.palladium.gui.window.TextProviderWindow;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "3/8/2021",
        type = ClassType.MODULE
)

public final class EnabledModListModule extends AbstractHudModule {

    private final EnabledModulesProvider modList;

    public EnabledModListModule() {
        super("ModList", "Displays a list of enabled modules on your screen.");
        this.modList = new EnabledModulesProvider();
    }

    @Override
    protected final @NotNull TextProviderWindow createWindow() {
        TextProviderWindow newWindow = TextProviderWindow.newDisplayWindow("enabled_modules", modList, this);
        newWindow.setX(25);
        newWindow.setY(25);
        newWindow.setWidth(150);
        newWindow.setHeight(91);
        newWindow.setColor(Colors.BACKGROUND_LAVENDER);
        return newWindow;
    }

    private static class EnabledModulesProvider extends TextProvider {

        private int numberEnabledModules = 0;

        private EnabledModulesProvider() {
            super();
        }

        @Override
        public ColorText getTitle() {
            return new ColorText("Enabled Modules (" + numberEnabledModules + ")");
        }

        @Override
        public ArrayList<ColorText> getBody() {
            ArrayList<ColorText> text = new ArrayList<>();

            int counter = 0;
            for (Module module : Palladium.getInstance().getModuleManager().getModules()) {
                if (module.isEnabled()) {
                    String string;
                    Optional<String> info = module.getPrimaryInfo();
                    if (info.isPresent()) {
                        string = String.format("%s (%s)", module.getName(), info.get());
                    }
                    else {
                        string = module.getName();
                    }
                    text.add(new ColorText(string));
                    counter++;
                }
            }
            numberEnabledModules = counter;

            return text;
        }
    }
}
