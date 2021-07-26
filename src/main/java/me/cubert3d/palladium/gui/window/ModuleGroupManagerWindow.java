package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.module.ModuleGroup;
import me.cubert3d.palladium.module.ModuleGroupManager;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.WINDOW
)

public final class ModuleGroupManagerWindow extends Window implements Displayable {

    private final ModuleGroupManager moduleGroupManager;

    private ModuleGroupManagerWindow(String id, int x, int y, int width, int height, int color, ModuleGroupManager moduleGroupManager) {
        super(id, x, y, width, height, color);
        this.moduleGroupManager = moduleGroupManager;
    }

    @Override
    public final @NotNull String getLabel() {
        return "Module Groups";
    }

    @Override
    public final void render(MatrixStack matrices, int mouseX, int mouseY) {
        if (!isMinimized()) {
            drawMainWindow(matrices);
            drawText(matrices, mouseX, mouseY);
        }
        else {
            drawMinimizedWindow(matrices);
        }
        drawWindowControls(matrices);
    }

    private void drawText(MatrixStack matrices, int mouseX, int mouseY) {
        int counter = 0;
        int size = moduleGroupManager.getGroups().size();
        boolean isListTooBig = size > getListSpaceAvailable(this);
        for (String line : getText()) {

            int color = isMouseOverLine(counter, windowManager, this) && !windowManager.hasDraggedWindow() ? Colors.HIGHLIGHT : Colors.WHITE;
            int x3 = getX() + 2;
            int y3 = getY() + DrawHelper.getTextHeight() + 1 + (DrawHelper.getTextHeight() * counter);

            /*
            Check if there is enough room to keep printing the list.
            If the number of available lines is the same or greater than the number
            of strings in the text-list, then just print the whole list. But if there
            is not enough room, that needs to be determined beforehand so that this can
            stop printing the list on the second-to-last line, so as to leave space for
            an addition line that says how many lines are not displayed. (...and x more)
             */
            if (!isListTooBig || counter < getListSpaceAvailable(this) - 1) {
                DrawHelper.drawText(matrices, line, x3, y3, getWindowWidth(), color);
            }
            else if (counter == getListSpaceAvailable(this) - 1) {
                int remainder = size - counter;
                String string2 = "...and " + remainder + " more";
                DrawHelper.drawText(matrices, string2, x3, y3, getWindowWidth(), color);
            }

            counter++;
        }
    }

    @Override
    protected final void onClickWindow(int mouseX, int mouseY, boolean isRelease) {
        int index = getLineMouseOver(mouseX, mouseY, this);
        if (index >= 0 && !isRelease) {
            int counter = 0;
            for (ModuleGroup moduleGroup : moduleGroupManager.getGroups().values()) {
                if (counter == index) {
                    ModuleGroupWindow window = getOrCreateModuleGroupWindow(moduleGroup);
                    window.open();
                }
                counter++;
            }
        }
    }

    private ModuleGroupWindow getOrCreateModuleGroupWindow(ModuleGroup moduleGroup) {
        for (Window window : windowManager.getWindows()) {
            if (window instanceof ModuleGroupWindow && ((ModuleGroupWindow) window).getModuleGroup().equals(moduleGroup)) {
                return (ModuleGroupWindow) window;
            }
        }
        return ModuleGroupWindow.newModuleGroupWindow(moduleGroup);
    }

    @Override
    public final ArrayList<String> getText() {
        return moduleGroupManager.getGroups().values().stream().map(ModuleGroup::getName).collect(Collectors.toCollection(ArrayList::new));
    }

    @Contract(" -> new")
    public static @NotNull ModuleGroupManagerWindow newModuleGroupManagerWindow() {
        ModuleGroupManager moduleGroupManager = Palladium.getInstance().getModuleGroupManager();
        return new ModuleGroupManagerWindow("module_group_manager", 25, 25, DEFAULT_WIDTH, DEFAULT_HEIGHT, Colors.BACKGROUND_PINK, moduleGroupManager);
    }
}
