package me.cubert3d.palladium.gui.text.provider;

import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Supplier;

@ClassInfo(
        authors = "cubert3d",
        date = "4/23/2021",
        type = ClassType.PROVIDER
)

public abstract class TextProvider {

    private final Supplier<ColorText> headerSupplier;
    private final Supplier<ArrayList<ColorText>> bodySupplier;

    public TextProvider(Supplier<ColorText> headerSupplier, Supplier<ArrayList<ColorText>> bodySupplier) {
        this.headerSupplier = headerSupplier;
        this.bodySupplier = bodySupplier;
    }

    public TextProvider() {
        this.headerSupplier = null;
        this.bodySupplier = null;
    }

    public abstract ColorText getHeader();

    public abstract ArrayList<ColorText> getBody();

    public final @NotNull ArrayList<ColorText> getAll() {
        ArrayList<ColorText> list = new ArrayList<>();
        list.add(getHeader());
        list.addAll(getBody());
        return list;
    }
}
