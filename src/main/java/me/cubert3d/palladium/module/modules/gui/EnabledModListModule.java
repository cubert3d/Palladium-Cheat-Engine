package me.cubert3d.palladium.module.modules.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.event.callback.DrawTextCallback;
import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/8/2021",
        status = "benched"
)

public final class EnabledModListModule extends AbstractModule {

    public EnabledModListModule() {
        super("ModList", "Displays a list of enabled modules on your screen.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onLoad() {


    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @DebugOnly
    private void test() {


    }
}
