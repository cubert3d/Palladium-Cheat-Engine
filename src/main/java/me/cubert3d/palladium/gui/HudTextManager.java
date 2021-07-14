package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "4/9/2021",
        type = ClassType.MANAGER
)

public final class HudTextManager {

    private TextProvider topLeftList;
    private TextProvider topRightList;
    private TextProvider bottomRightList;

    HudTextManager() {

    }

    public final Optional<TextProvider> getTopLeftList() {
        return Optional.ofNullable(topLeftList);
    }

    public final Optional<TextProvider> getTopRightList() {
        return Optional.ofNullable(topRightList);
    }

    public final Optional<TextProvider> getBottomRightList() {
        return Optional.ofNullable(bottomRightList);
    }

    public final void setTopLeftList(TextProvider topLeftList) {
        this.topLeftList = topLeftList;
    }

    public final void setTopRightList(TextProvider topRightList) {
        this.topRightList = topRightList;
    }

    public final void setBottomRightList(TextProvider bottomRightList) {
        this.bottomRightList = bottomRightList;
    }

    public final void clearTopLeftList() {
        this.topLeftList = null;
    }

    public final void clearTopRightList() {
        this.topRightList = null;
    }

    public final void clearBottomRightList() {
        this.bottomRightList = null;
    }
}
