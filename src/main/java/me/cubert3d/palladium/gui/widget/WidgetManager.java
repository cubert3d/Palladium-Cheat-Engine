package me.cubert3d.palladium.gui.widget;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/20/2021",
        status = "in-progress"
)

public final class WidgetManager {

    private static final Set<Widget> widgets = new HashSet<>();
    private static Widget focusedWidget;

    private WidgetManager() {}

    public static Set<Widget> getWidgets() {
        return widgets;
    }

    static void addWidget(Widget widget) {
        widgets.add(widget);
    }

    static void setFocusedWidget(@NotNull Widget widget) {
        Widget previousFocusedWidget = focusedWidget;
        focusedWidget = widget;
        widget.setFocused(true);
        previousFocusedWidget.setFocused(false);
    }

    public static int getScaledWidth() {
        return Common.getMC().getWindow().getScaledWidth();
    }

    public static int getScaledHeight() {
        return Common.getMC().getWindow().getScaledHeight();
    }

    public static void render(MatrixStack matrices) {
        for (Widget widget : widgets) {
            widget.render(matrices);
        }
    }
}
