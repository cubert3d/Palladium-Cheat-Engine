package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@ClassInfo(
        authors = "cubert3d",
        date = "7/24/2021",
        type = ClassType.MISC
)

public final class Vector2X<N extends Number> {

    private final N x;
    private final N y;

    private Vector2X(N x, N y) {
        this.x = x;
        this.y = y;
    }

    public final N getX() {
        return x;
    }

    public final N getY() {
        return y;
    }

    @Contract("_, _ -> new")
    public static <N extends Number> @NotNull Vector2X<N> of(final N x, final N y) {
        return new Vector2X<>(Objects.requireNonNull(x), Objects.requireNonNull(y));
    }
}
