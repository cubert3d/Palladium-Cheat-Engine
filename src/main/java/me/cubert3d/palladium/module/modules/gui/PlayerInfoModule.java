package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.mixin.MinecraftClientAccessor;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.gui.text.provider.PlayerInfoProvider;
import me.cubert3d.palladium.gui.text.provider.TextProvider;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/10/2021",
        status = "in-progress"
)

public final class PlayerInfoModule extends Module {

    private static final TextProvider infoList = new PlayerInfoProvider();

    public PlayerInfoModule() {
        super("Info", "Displays information about the player and the game on-screen.",
                ModuleType.TOGGLE, ModuleDevStatus.AVAILABLE);
    }

    @Override
    protected void onEnable() {
        TextHudRenderer.getTextManager().setTopLeftList(infoList);
    }

    @Override
    protected void onDisable() {
        TextHudRenderer.getTextManager().clearTopLeftList();
    }
}
