package me.cubert3d.palladium.event.mixin.accessors;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {

    @Accessor("renderTickCounter")
    RenderTickCounter getRenderTickCounter();

    @Accessor("currentFPS")
    static int getCurrentFPS() {
        throw new AssertionError();
    }

    @Invoker
    void invokeOpenChatScreen(String text);
}
