package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.ChatFilter;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021",
        status = "complete"
)

@Mixin(ChatHud.class)
public final class ChatHudMixin {
    @Inject(at = @At(value = "HEAD"),
            method = "addMessage(Lnet/minecraft/text/Text;IIZ)V",
            cancellable = true)
    private void onAddMessage(Text message, int messageId, int timestamp, boolean refresh, final CallbackInfo info) {
        if (ModuleList.getModule("ChatFilter").get().isEnabled()) {
            String msg = message.getString().trim().toLowerCase();
            if (ChatFilter.shouldMsgBeFiltered(msg)) {
                info.cancel();
            }
        }
    }
}
