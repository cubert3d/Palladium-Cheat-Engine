package me.cubert3d.palladium.event.mixin.mixins;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.mixin.accessors.MinecraftClientAccessor;
import me.cubert3d.palladium.input.Keys;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.module.modules.command.PalladiumCommand;
import me.cubert3d.palladium.module.setting.single.KeyBindingSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "6/22/2021",
        type = ClassType.MIXIN
)

@Mixin(Keyboard.class)
abstract class KeyboardMixin {

    @Inject(method = "onKey(JIIII)V", at = @At("TAIL"))
    private void onOnKey(long window, int key, int scancode, int i, int j, final CallbackInfo info) {
        /*
        The variable "i" represents whether the key is being pressed, held, or released:
        1 = pressed
        2 = held
        0 = released
         */
        // Make sure there is no 'screen' open--aka menu, chat console, etc
        if (MinecraftClient.getInstance().currentScreen == null) {

            Optional<InputUtil.Key> prefixKey = Keys.getKeyFromString(PalladiumCommand.getPrefix());
            if (prefixKey.isPresent()) {
                int prefixKeyCode = prefixKey.get().getCode();
                if (key == prefixKeyCode) {
                    ((MinecraftClientAccessor) MinecraftClient.getInstance()).invokeOpenChatScreen("");
                    return;
                }
            }

            for (Module module : Palladium.getInstance().getModuleManager().getModules()) {
                KeyBindingSetting binding = module.getBinding();
                if (binding.isSet() && binding.getValue().getCode() == key) {
                    module.onKeyPressed(i);
                    break;
                }
            }
        }
    }
}
