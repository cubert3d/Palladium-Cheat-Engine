package me.cubert3d.palladium.cmd;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.event.callback.PlayerChatCallback;
import me.cubert3d.palladium.event.mixin.MinecraftClientAccessor;
import me.cubert3d.palladium.input.Bindings;
import me.cubert3d.palladium.input.PalladiumKeyBinding;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/6/2021",
        status = "in-progress"
)

public final class CommandListener {

    private static final String commandPrefix = ".";
    private static final PalladiumKeyBinding binding = new PalladiumKeyBinding("key.palladium_command", 46, PalladiumKeyBinding.Type.HOLD) {

        // TODO: make this work properly with PRESS_ONCE

        @Override
        protected void onPressed() {
            ((MinecraftClientAccessor) MinecraftClient.getInstance()).invokeOpenChatScreen("");
        }

        @Override
        protected void onHeld() {
            this.onPressed();
        }
    };

    public static void registerListener() {
        PlayerChatCallback.EVENT.register((player, message) -> {

            System.out.println("chat: " + message);

            if (!message.startsWith(commandPrefix))
                return ActionResult.PASS;

            // Everything in the message except for the command prefix
            String content = message.substring(commandPrefix.length());

            // Split the content into words
            String[] words = content.split(" ");

            // The first word--used to get a module
            String label = words[0];

            // The arguments to be passed to the command
            String[] args = new String[words.length - 1];
            System.arraycopy(words, 1, args, 0, args.length);

            ModuleManager.getModule(label.toLowerCase()).ifPresent(module -> module.execute(args));

            return ActionResult.FAIL;
        });
    }

    static {
        Bindings.addBinding(binding);
    }
}
