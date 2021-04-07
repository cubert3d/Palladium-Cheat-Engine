package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.UtilityClass;
import net.minecraft.client.MinecraftClient;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/15/2021",
        status = "in-progress"
)

@UtilityClass
public final class RenderUtil {

    private static boolean fullbright;

    private RenderUtil() {}

    public static void reloadRenderer() {
        MinecraftClient.getInstance().worldRenderer.reload();
    }

    public static boolean isFullbrightEnabled() {
        return fullbright;
    }

    public static void setFullbright(boolean fullbright) {
        RenderUtil.fullbright = fullbright;
    }
}
