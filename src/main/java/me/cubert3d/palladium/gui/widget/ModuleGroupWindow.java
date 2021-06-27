package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleGroup;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

public class ModuleGroupWindow extends Window {

    private ModuleGroup moduleGroup;

    public ModuleGroupWindow(String id) {
        super(id, "Window");
    }

    public ModuleGroupWindow(String id, ModuleGroup moduleGroup) {
        this(id);
        this.setModuleGroup(moduleGroup);
    }

    @Override
    public final String getLabel() {
        return moduleGroup.getName();
    }

    public final ModuleGroup getModuleGroup() {
        return moduleGroup;
    }

    public final void setModuleGroup(@NotNull ModuleGroup moduleGroup) {
        this.moduleGroup = moduleGroup;
        this.setHeight(10 + (9 * moduleGroup.getModules().size()));
    }

    @Override
    public void render(MatrixStack matrices) {
        if (!minimized) {
            drawMainWindow(matrices);
            drawWindowControls(matrices);
            drawModules(matrices);
        }
        else {
            drawMinimizedWindow(matrices);
            drawWindowControls(matrices);
        }
    }

    private void drawModules(MatrixStack matrices) {

        int counter = 0;

        for (Module module : moduleGroup.getModules()) {

            int x3 = getX() + 2;
            int y3 = getY() + DrawHelper.getTextHeight() + 1 + (DrawHelper.getTextHeight() * counter);

            DrawHelper.drawText(matrices, module.getName(), x3 + 9, y3, getWindowWidth() - 9, DrawHelper.getTextHeight(), Colors.WHITE);

            // Draw the toggle outline box.
            DrawHelper.drawOutlineBox(matrices, x3, y3, x3 + 7, y3 + 7, Colors.WHITE);

            // If the module is enabled, draw the inside of the box.
            if (module.isEnabled()) {
                DrawHelper.drawBox(matrices, x3 + 2, y3 + 2, x3 + 5, y3 + 5, Colors.WHITE);
            }

            counter++;
        }
    }

    @Override
    protected void onClick(int mousePosX, int mousePosY, boolean isRelease) {
        ClickLocation location = getClickLocation(mousePosX, mousePosY);

        switch (location) {
            case TOOLBAR:
                cursorDistanceX = mousePosX - getX();
                cursorDistanceY = mousePosY - getY();
                break;

            case MINIMIZE_CONTROL:
                if (!isRelease)
                    minimized = !minimized;
                break;

            case PIN_CONTROL:
                if (!isRelease)
                    pinned = !pinned;
                break;

            case WINDOW:
                int index = getModuleClicked(mousePosX, mousePosY);
                if (index >= 0 && !isRelease) {
                    moduleGroup.getModules().get(index).toggle();
                }
        }
    }

    // Returns the index of the module that was clicked, or -1 if none were clicked.
    private int getModuleClicked(int mousePosX, int mousePosY) {
        if (mousePosX <= getX() + 9) {
            /*
            Subtract the Y-mouse-position by the Y-position of this window, so that
            it is relative to the position of this window. Then subtract 9 to account
            for the toolbar. Finally, divide by 9, the height of a line of text, to
            determine which line of text--that is, module--was clicked.
             */
            return (mousePosY - getY() - 9) / 9;
        }
        else {
            return -1;
        }
    }
}
