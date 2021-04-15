package me.cubert3d.palladium.event.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {

    @Accessor("currentFPS")
    static int getCurrentFPS() {
        throw new AssertionError();
    }

    @Invoker
    void invokeOpenChatScreen(String text);
}
