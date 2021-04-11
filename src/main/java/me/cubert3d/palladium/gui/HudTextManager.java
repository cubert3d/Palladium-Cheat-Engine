package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.util.annotation.ClassDescription;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/9/2021",
        status = "in-progress"
)

public final class HudTextManager {

    private Supplier<ArrayList<String>> topLeftSupplier;
    private Supplier<ArrayList<String>> topRightSupplier;

    protected HudTextManager() {

    }

    public final Optional<ArrayList<String>> getTopLeftStrings() {
        if (topLeftSupplier != null)
            return Optional.of(topLeftSupplier.get());
        else
            return Optional.empty();
    }

    public final Optional<ArrayList<String>> getTopRightStrings() {
        if (topRightSupplier != null)
            return Optional.of(topRightSupplier.get());
        else
            return Optional.empty();
    }

    public final void setTopLeftSupplier(Supplier<ArrayList<String>> topLeftSupplier) {
        this.topLeftSupplier = topLeftSupplier;
    }

    public final void setTopRightSupplier(Supplier<ArrayList<String>> supplier) {
        this.topRightSupplier = supplier;
    }

    public final void clearTopLeftSupplier() {
        this.topLeftSupplier = null;
    }

    public final void clearTopRightSupplier() {
        this.topRightSupplier = null;
    }
}
