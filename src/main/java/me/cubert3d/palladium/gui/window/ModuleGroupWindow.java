package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.module.ModuleGroup;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.util.Vector2X;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.WIDGET
)

public final class ModuleGroupWindow extends CloseableWindow implements Displayable {

    private final ModuleGroup moduleGroup;

    private ModuleGroupWindow(String id, int x, int y, int width, int height, int color, ModuleGroup moduleGroup) {
        super(id, x, y, width, height, color);
        this.moduleGroup = moduleGroup;
    }

    public final ModuleGroup getModuleGroup() {
        return moduleGroup;
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
    public void render(MatrixStack matrices, int mouseX, int mouseY) {
        if (!isMinimized()) {
            drawMainWindow(matrices);
            drawWindowControls(matrices);
            drawModules(matrices, mouseX, mouseY);
        }
        else {
            drawMinimizedWindow(matrices);
            drawWindowControls(matrices);
        }
    }

    private void drawModules(MatrixStack matrices, int mouseX, int mouseY) {
        int counter = 0;
        for (Module module : moduleGroup.getModules()) {

            MouseOver mouseOver = isMouseOverControlOrText(mouseX, mouseY, counter);
            boolean mouseOverHigherWindow = windowManager.isMouseOverHigherWindow(mouseX, mouseY, this);
            int controlColor = mouseOver.equals(MouseOver.CONTROL)
                    && !windowManager.hasDraggedWindow()
                    && !mouseOverHigherWindow
                    ? Colors.HIGHLIGHT : Colors.WHITE;

            int moduleColor = mouseOver.equals(MouseOver.MODULE)
                    && !windowManager.hasDraggedWindow()
                    && !mouseOverHigherWindow
                    ? Colors.HIGHLIGHT : Colors.WHITE;

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
    private MouseOver isMouseOverControlOrText(int mouseX, int mouseY, int index) {
        int lineIndex = getLineMouseOver(mouseX, mouseY, this);
        if (index == lineIndex) {
            if (mouseX < getX() + 11) {
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
        int index = getLineMouseOver(mouseX, mouseY, this);
        MouseOver mouseOver = isMouseOverControlOrText(mouseX, mouseY, index);
        if (index >= 0 && !isRelease) {
            if (mouseOver == MouseOver.CONTROL) {
                moduleGroup.getModules().get(index).toggle();
            }
        }
    }

    @Override
    public ArrayList<String> getText() {
        return moduleGroup.getModules().stream().map(Module::getName).collect(Collectors.toCollection(ArrayList::new));
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
