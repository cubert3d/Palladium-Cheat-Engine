package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.gui.text.TextList;
import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/9/2021",
        status = "in-progress"
)

public final class HudTextManager {

    private TextList topLeftList;
    private TextList topRightList;
    private TextList bottomRightList;

    protected HudTextManager() {

    }

    public final Optional<TextList> getTopLeftList() {
        return Optional.ofNullable(topLeftList);
    }

    public final Optional<TextList> getTopRightList() {
        return Optional.ofNullable(topRightList);
    }

    public final Optional<TextList> getBottomRightList() {
        return Optional.ofNullable(bottomRightList);
    }

    public final void setTopLeftList(TextList topLeftList) {
        this.topLeftList = topLeftList;
    }

    public final void setTopRightList(TextList topRightList) {
        this.topRightList = topRightList;
    }

    public final void setBottomRightList(TextList bottomRightList) {
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
