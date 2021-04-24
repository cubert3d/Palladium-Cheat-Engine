package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/20/2021",
        status = "in-progress"
)

public class WindowWidget extends Widget {

    public static int BORDER_COLOR = Colors.BACKGROUND_WHITE;
    public static int LABEL_COLOR = Colors.LAVENDER;

    // The string that is displayed at the top of the window.
    private final String label;
    private boolean pinned = false;
    private boolean minimized = false;

    public WindowWidget(String id, String label) {
        super(id);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // Returns the width of the box inside the borders, underneath the label.
    protected final int getWindowWidth() {
        return getScaledWidth() - 2;
    }

    // Returns the height of the box inside the borders, underneath the label.
    protected final int getWindowHeight() {
        return getScaledHeight() - DrawHelper.getTextHeight() - 1;
    }

    @Override
    public boolean shouldRender() {
        return super.shouldRender() || pinned;
    }

    @Override
    public void render(MatrixStack matrices) {

        int x2 = getX() + getScaledWidth();
        int y2 = getY() + getScaledHeight();

        // Top box
        DrawableHelper.fill(matrices, getX(), getY(), x2, getY() + DrawHelper.getTextHeight(), BORDER_COLOR);

        // Label
        DrawHelper.drawText(matrices, getLabel(), getX() + 1, getY() + 1, getScaledWidth() - 2, DrawHelper.getTextHeight(), LABEL_COLOR);

        // Main box
        DrawableHelper.fill(matrices, getX() + 1, getY() + DrawHelper.getTextHeight(), x2 - 1, y2 - 1, getColor());

        // Right Border
        DrawableHelper.fill(matrices, x2 - 1, getY() + DrawHelper.getTextHeight(), x2, y2 - 1, BORDER_COLOR);

        // Bottom Border
        DrawableHelper.fill(matrices, getX(), y2 - 1, x2, y2, BORDER_COLOR);

        // Left Border
        DrawableHelper.fill(matrices, getX(), getY() + DrawHelper.getTextHeight(), getX() + 1, y2 - 1, BORDER_COLOR);
    }
}
