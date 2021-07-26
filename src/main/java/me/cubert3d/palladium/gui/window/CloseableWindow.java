package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

import java.util.LinkedHashSet;
import java.util.Set;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.WINDOW
)

public abstract class CloseableWindow extends Window {

    private boolean open;

    protected CloseableWindow(String id, int x, int y, int width, int height, int color) {
        super(id, x, y, width, height, color);
        this.open = false;
    }

    @Override
    public final boolean isOpen() {
        return open;
    }

    @Override
    public void open() {
        if (!open) {
            windowManager.openWindow(this);
            this.open = true;
        }
        this.focus();
    }

    @Override
    public void close() {
        if (open) {
            windowManager.closeWindow(this);
            this.open = false;
        }
    }

    @Override
    protected Set<Control> buildControls() {
        Set<Control> controls = new LinkedHashSet<>();
        controls.add(new Control.Pin(0, this));
        controls.add(new Control.Close(1, this));
        controls.add(new Control.Minimize(2, this));
        return controls;
    }
}
