package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.module.setting.Setting;
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
        type = ClassType.WINDOW
)

public final class ModuleWindow extends CloseableWindow implements Displayable {

    private final Module module;

    private ModuleWindow(String id, int x, int y, int width, int height, int color, Module module) {
        super(id, x, y, width, height, color);
        this.module = module;
    }

    public final Module getModule() {
        return module;
    }

    @Override
    public String getLabel() {
        return String.format("%s: %s", module.getName(), module.isEnabled() ? "Enabled" : "Disabled");
    }

    @Override
    public final int getHeight() {
        int size = module.getSettings().size();
        return DrawHelper.FONT_HEIGHT + (size * DrawHelper.FONT_HEIGHT) + 1;
    }

    @Override
    public final void setHeight(int height) {

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
        int size = module.getSettings().size();
        boolean isListTooBig = size > getListSpaceAvailable(this);
        ArrayList<String> settingNames = getText();
        ArrayList<String> settingValues = getSettingValues();
        for (int i = 0; i < settingNames.size(); i++) {

            String settingName = settingNames.get(i);
            String settingValue = settingValues.get(i);

            boolean isMouseOverHigherWindow = windowManager.isMouseOverHigherWindow(mouseX, mouseY, this);
            int color = isMouseOverLine(counter, windowManager, this)
                    && !windowManager.hasDraggedWindow()
                    && !isMouseOverHigherWindow ? Colors.HIGHLIGHT : Colors.WHITE;

            int x3 = getX() + 2;
            int y3 = getY() + DrawHelper.getTextHeight() + 1 + (DrawHelper.getTextHeight() * counter);
            int x4 = getX2() - 2 - DrawHelper.getTextWidth(settingValue);

            /*
            Check if there is enough room to keep printing the list.
            If the number of available lines is the same or greater than the number
            of strings in the text-list, then just print the whole list. But if there
            is not enough room, that needs to be determined beforehand so that this can
            stop printing the list on the second-to-last line, so as to leave space for
            an addition line that says how many lines are not displayed. (...and x more)
             */
            if (!isListTooBig || counter < getListSpaceAvailable(this) - 1) {
                DrawHelper.drawText(matrices, settingName, x3, y3, x4 - x3, color);
                DrawHelper.drawText(matrices, settingValue, x4, y3, getX2() - x4, color);
            } else if (counter == getListSpaceAvailable(this) - 1) {
                int remainder = size - counter;
                String string2 = "...and " + remainder + " more";
                DrawHelper.drawText(matrices, string2, x3, y3, getWindowWidth(), color);
            }

            counter++;
        }
    }

    @Override
    public final ArrayList<String> getText() {
        return module.getSettings().stream().map(Setting::getName).collect(Collectors.toCollection(ArrayList::new));
    }

    public final ArrayList<String> getSettingValues() {
        return module.getSettings().stream()
                .map(setting -> !setting.isListSetting() ? setting.getAsString() : "...")
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static @NotNull ModuleWindow newModuleWindow(@NotNull Module module) {
        String id = module.getName().toLowerCase().concat("_window");
        Vector2X<Integer> newPosition = getWindowManager().getNextWindowPosition();
        int color = getWindowManager().getNextColor();
        return new ModuleWindow(id, newPosition.getX(), newPosition.getY(), DEFAULT_WIDTH, DEFAULT_HEIGHT, color, module);
    }
}
