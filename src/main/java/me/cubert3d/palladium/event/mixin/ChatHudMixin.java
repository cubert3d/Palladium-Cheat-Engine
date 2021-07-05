package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.event.callback.ChatFilterCallback;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021"
)

@Mixin(ChatHud.class)
abstract class ChatHudMixin {
    @Inject(at = @At(value = "HEAD"),
            method = "addMessage(Lnet/minecraft/text/Text;IIZ)V",
            cancellable = true)
    private void onAddMessage(Text message, int messageId, int timestamp, boolean refresh, final CallbackInfo info) {

        ActionResult result = ChatFilterCallback.EVENT.invoker().interact(message.getString());

        if (result.equals(ActionResult.FAIL)) {
            info.cancel();
        }
    }
}
