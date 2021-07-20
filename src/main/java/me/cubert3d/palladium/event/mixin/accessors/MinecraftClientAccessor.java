package me.cubert3d.palladium.event.mixin.accessors;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@ClassInfo(
        authors = "cubert3d",
        date = "6/22/2021",
        type = ClassType.MIXIN
)

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {

    @Accessor("renderTickCounter")
    RenderTickCounter getRenderTickCounter();

    @Accessor
    int getAttackCooldown();

    @Accessor("currentFPS")
    static int getCurrentFPS() {
        throw new AssertionError();
    }

    @Invoker
    void invokeOpenChatScreen(String text);
}
