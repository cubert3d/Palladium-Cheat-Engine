package me.cubert3d.palladium.gui.text;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassInfo(
        authors = "cubert3d",
        date = "4/23/2021",
        type = ClassType.MISC
)

public abstract class TextProvider {

    protected TextProvider() {

    }

    public abstract ColorText getTitle();

    public abstract ArrayList<ColorText> getBody();

    public final @NotNull ArrayList<ColorText> getAll() {
        ArrayList<ColorText> list = new ArrayList<>();
        list.add(getTitle());
        list.addAll(getBody());
        return list;
    }
}
