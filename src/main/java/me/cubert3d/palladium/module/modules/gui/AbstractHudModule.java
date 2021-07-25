package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.HudTextManager;
import me.cubert3d.palladium.gui.window.TextProviderWindow;
import me.cubert3d.palladium.gui.window.WindowManager;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/20/2021",
        type = ClassType.MODULE
)

public abstract class AbstractHudModule extends ToggleModule {

    private TextProviderWindow window;

    protected AbstractHudModule(String name, String description) {
        super(name, description);
    }

    @Override
    public void onLoad() {
        if (isEnabled()) {
            openWindow();
        }
    }

    @Override
    protected void onEnable() {
        openWindow();
    }

    @Override
    protected void onDisable() {
        closeWindow();
    }

    private void openWindow() {
        if (window == null) {
            window = createWindow();
        }
        if (!window.isOpen()) {
            window.open();
        }
    }

    protected abstract TextProviderWindow createWindow();

    private void closeWindow() {
        if (window != null && window.isOpen()) {
            window.close();
        }
    }

    protected final HudTextManager getTextManager() {
        return Palladium.getInstance().getGuiRenderer().getTextHudRenderer().getTextManager();
    }

    protected final WindowManager getWindowManager() {
        return Palladium.getInstance().getGuiRenderer().getClickGUI().getWindowManager();
    }
}
