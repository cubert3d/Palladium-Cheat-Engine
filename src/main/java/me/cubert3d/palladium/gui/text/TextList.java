package me.cubert3d.palladium.gui.text;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Supplier;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/23/2021"
)

public final class TextList {

    private final Supplier<ColorText> headerSupplier;
    private final Supplier<ArrayList<ColorText>> bodySupplier;

    public TextList(Supplier<ColorText> headerSupplier, Supplier<ArrayList<ColorText>> bodySupplier) {
        this.headerSupplier = headerSupplier;
        this.bodySupplier = bodySupplier;
    }

    public final ColorText getHeader() {
        return headerSupplier.get();
    }

    public final ArrayList<ColorText> getBody() {
        return bodySupplier.get();
    }

    public final @NotNull ArrayList<ColorText> getAll() {
        ArrayList<ColorText> list = new ArrayList<>();
        list.add(getHeader());
        list.addAll(getBody());
        return list;
    }
}
