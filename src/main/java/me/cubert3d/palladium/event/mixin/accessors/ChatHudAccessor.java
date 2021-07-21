package me.cubert3d.palladium.event.mixin.accessors;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@ClassInfo(
        authors = "cubert3d",
        date = "7/21/2021",
        type = ClassType.MIXIN
)

@Mixin(ChatHud.class)
public interface ChatHudAccessor {
    @Invoker("addMessage")
    void addMessageInvoker(Text message, int messageId);
}
