package me.cubert3d.palladium.gui.window;

import me.cubert3d.palladium.gui.DrawHelper;
import me.cubert3d.palladium.util.Vector2X;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.WIDGET
)

public interface Displayable {

    ArrayList<String> getText();

    default int getListSpaceAvailable(@NotNull Window window) {
        return window.getWindowHeight() / (DrawHelper.getTextHeight());
    }

    // Returns the index of the line that was clicked, or -1 if none were clicked.
    default int getLineMouseOver(int mouseX, int mouseY, @NotNull Window window) {
        if (mouseX > window.getX() && mouseX < window.getX2() && mouseY > window.getY() + DrawHelper.FONT_HEIGHT && mouseY < window.getY2()) {
            /*
            Subtract the Y-mouse-position by the Y-position of this window, so that
            it is relative to the position of this window. Then subtract 9 to account
            for the toolbar. Finally, divide by 9, the height of a line of text, to
            determine which line of text--that is, module--was clicked.
             */
            return (mouseY - window.getY() - 9) / 9;
        }
        else {
            return -1;
        }
    }

    default boolean isMouseOverLine(int index, @NotNull WindowManager windowManager, Window window) {
        Mouse mouse = MinecraftClient.getInstance().mouse;
        Vector2X<Integer> position = windowManager.scaleMousePosition(mouse.getX(), mouse.getY());
        int lineIndex = getLineMouseOver(position.getX(), position.getY(), window);
        return index == lineIndex;
    }
}
