package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.event.callback.DrawTextCallback;
import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.DebugOnly;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

// Written by cubert3d on 3/8/2021

public final class EnabledModListModule extends AbstractModule {

    public EnabledModListModule() {
        super("ModList", "Displays a list of enabled modules on your screen.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onLoad() {


        DrawTextCallback.EVENT.register((matrices, text, x, y, color) -> {

            if (!isEnabled())
                return ActionResult.PASS;

            String message = String.format("text=\"%s\", x=%f, y=%f, color=%d", text, x, y, color);

            System.out.println(message);
            Common.sendMessage(message);

            return ActionResult.PASS;
        });


    }

    @Override
    protected void onEnable() {



    }

    @Override
    protected void onDisable() {

    }

    @DebugOnly
    private void test() {

        Common.getMC().getProfiler().push("test");

        MatrixStack matrices = new MatrixStack();

        matrices.push();

        while (isEnabled()) {
            Common.getMC().textRenderer.draw(matrices, "test", 314.0F, 100.0F, 8453920);
        }


        matrices.pop();

        Common.getMC().getProfiler().pop();

    }
}
