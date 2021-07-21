package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.HudTextManager;
import me.cubert3d.palladium.gui.widget.WidgetManager;
import me.cubert3d.palladium.gui.widget.window.DisplayWindow;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/20/2021",
        type = ClassType.MODULE
)

abstract class AbstractHudModule extends ToggleModule {

    private DisplayWindow window;

    protected AbstractHudModule(String name, String description) {
        super(name, description);
    }

    @Override
    public void onLoad() {
        if (isEnabled()) {
            addWindow();
        }
    }

    @Override
    protected void onEnable() {
        addWindow();
    }

    @Override
    protected void onDisable() {
        removeWindow();
    }

    private void addWindow() {
        if (window == null) {
            window = createWindow();
        }
        else {
            getWidgetManager().getWidgets().add(window);
        }
    }

    protected abstract DisplayWindow createWindow();

    private void removeWindow() {
        getWidgetManager().getWidgets().remove(window);
    }

    protected final HudTextManager getTextManager() {
        return Palladium.getInstance().getGuiRenderer().getTextHudRenderer().getTextManager();
    }

    protected final WidgetManager getWidgetManager() {
        return Palladium.getInstance().getGuiRenderer().getClickGUI().getWidgetManager();
    }
}
