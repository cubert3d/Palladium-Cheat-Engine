package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.ArrayList;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/9/2021",
        status = "in-progress"
)

public final class HudTextManager {

    private final ArrayList<String> topLeft = new ArrayList<>();
    private final ArrayList<String> topRight = new ArrayList<>();

    protected HudTextManager() {

    }

    public final ArrayList<String> getTopLeftStrings() {
        return topLeft;
    }

    public final ArrayList<String> getTopRightStrings() {
        return topRight;
    }

    public final void changeTopLeftString (String oldString, String newString) {

    }
}
