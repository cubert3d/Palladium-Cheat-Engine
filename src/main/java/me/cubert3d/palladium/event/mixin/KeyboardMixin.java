package me.cubert3d.palladium.event.mixin;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.setting.single.KeyBindingSetting;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
abstract class KeyboardMixin {

    @Inject(method = "onKey(JIIII)V", at = @At("TAIL"))
    private void onOnKey(long window, int key, int scancode, int i, int j, CallbackInfo info) {

        /*
        The variable "i" represents whether the key is being pressed, held, or released:
        1 = pressed
        2 = held
        0 = released
         */

        // Make sure there is no 'screen' open--aka menu, chat console, etc
        if (MinecraftClient.getInstance().currentScreen == null) {
            for (Module module : ModuleManager.getModules()) {
                KeyBindingSetting binding = module.getBinding();
                if (binding.isSet() && binding.getValue().getCode() == key) {
                    module.onKeyPressed(i);
                    break;
                }
            }
        }
    }
}
