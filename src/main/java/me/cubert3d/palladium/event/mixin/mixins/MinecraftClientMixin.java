package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.KillAuraCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.util.snooper.SnooperListener;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "6/23/2021",
        type = ClassType.MIXIN
)

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements SnooperListener, WindowEventHandler, MixinCaster<MinecraftClient> {

    private MinecraftClientMixin(String string) {
        super(string);
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tickInject(final CallbackInfo info) {
        KillAuraCallback.EVENT.invoker().interact();
    }

    @Inject(method = "close()V", at = @At("HEAD"))
    private void closeInject(final CallbackInfo info) {
        Palladium.getInstance().close();
    }
}
