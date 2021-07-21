package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.event.callback.ChatFilterCallback;
import me.cubert3d.palladium.event.mixin.MixinCaster;
import me.cubert3d.palladium.event.mixin.accessors.ChatHudAccessor;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassInfo(
        authors = "cubert3d",
        date = "3/12/2021",
        type = ClassType.MIXIN
)

@Mixin(ChatHud.class)
public abstract class ChatHudMixin extends DrawableHelper implements MixinCaster<ChatHud> {

    @Inject(at = @At(value = "HEAD"),
            method = "addMessage(Lnet/minecraft/text/Text;)V",
            cancellable = true)
    private void addMessageInject(Text message, final CallbackInfo info) {
        boolean shouldFilter = ChatFilterCallback.EVENT.invoker().shouldFilter(message.getString());
        if (shouldFilter) {
            info.cancel();
            Text newMessage = new LiteralText("§4[REDACTED]");
            ((ChatHudAccessor) this).addMessageInvoker(newMessage, 0);
        }
    }
}
