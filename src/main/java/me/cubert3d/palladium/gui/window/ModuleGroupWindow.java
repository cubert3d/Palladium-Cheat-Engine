package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.module.ModuleGroup;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.util.Vector2X;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.WIDGET
)

public final class ModuleGroupWindow extends CloseableWindow {

    private final ModuleGroup moduleGroup;

    private ModuleGroupWindow(String id, int x, int y, int width, int height, int color, ModuleGroup moduleGroup) {
        super(id, x, y, width, height, color);
        this.moduleGroup = moduleGroup;
    }

    @Override
    public final String getLabel() {
        return moduleGroup.getName();
    }

    @Override
    public int getHeight() {
        int size = moduleGroup.getModules().size();
        return DrawHelper.FONT_HEIGHT + (size * DrawHelper.FONT_HEIGHT) + 1;
    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public void render(MatrixStack matrices) {
        if (!isMinimized()) {
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

            MouseOver mouseOver = isMouseOverControlOrText(counter);
            int controlColor = mouseOver.equals(MouseOver.CONTROL) ? Colors.HIGHLIGHT : Colors.WHITE;
            int moduleColor = mouseOver.equals(MouseOver.MODULE) ? Colors.HIGHLIGHT : Colors.WHITE;

            int x3 = getX() + 2;
            int y3 = getY() + DrawHelper.getTextHeight() + 1 + (DrawHelper.getTextHeight() * counter);

            DrawHelper.drawText(matrices, module.getName(), x3 + 9, y3, getWindowWidth() - 9, moduleColor);

            // Draw the toggle outline box.
            DrawHelper.drawOutlineBox(matrices, x3, y3, x3 + 7, y3 + 7, controlColor);

            // If the module is enabled, draw the inside of the box.
            if (module.isEnabled()) {
                DrawHelper.drawBox(matrices, x3 + 2, y3 + 2, x3 + 5, y3 + 5, controlColor);
            }

            counter++;
        }
    }

    // Returns whether the mouse is over the line (given by the index), and if it is,
    // whether it is over the module name, or the control to toggle it.
    private MouseOver isMouseOverControlOrText(int index) {
        Mouse mouse = MinecraftClient.getInstance().mouse;
        Vector2X<Integer> position = windowManager.scaleMousePosition(mouse.getX(), mouse.getY());
        int lineIndex = getLineMouseOver(position.getX(), position.getY());
        if (index == lineIndex) {
            if (position.getX() < getX() + 11) {
                return MouseOver.CONTROL;
            }
            else {
                return MouseOver.MODULE;
            }
        }
        else {
            return MouseOver.NONE;
        }
    }

    @Override
    protected void onClickWindow(int mouseX, int mouseY, boolean isRelease) {
        int index = getLineMouseOver(mouseX, mouseY);
        MouseOver mouseOver = isMouseOverControlOrText(index);
        if (index >= 0 && !isRelease) {
            if (mouseOver == MouseOver.CONTROL) {
                moduleGroup.getModules().get(index).toggle();
            }
        }
    }

    public static @NotNull ModuleGroupWindow newModuleGroupWindow(@NotNull ModuleGroup moduleGroup) {
        Vector2X<Integer> coordinate = getWindowManager().getNextWindowPosition();
        String id = moduleGroup.getName().toLowerCase().replaceAll(" ", "_").concat("_modules");
        int x = coordinate.getX();
        int y = coordinate.getY();
        int width = DEFAULT_WIDTH;
        int height = DEFAULT_HEIGHT;
        int color = getWindowManager().getNextColor();
        return new ModuleGroupWindow(id, x, y, width, height, color, moduleGroup);
    }

    private enum MouseOver {
        CONTROL,
        MODULE,
        NONE
    }
}
