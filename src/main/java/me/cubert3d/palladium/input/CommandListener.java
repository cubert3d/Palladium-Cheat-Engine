package me.cubert3d.palladium.input;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.PlayerChatCallback;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.util.ActionResult;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/6/2021",
        status = "in-progress"
)

public final class CommandListener {

    private static final String commandPrefix = ".";

    private CommandListener() {}

    public static void registerListener() {
        PlayerChatCallback.EVENT.register((player, message) -> {

            if (!message.startsWith(commandPrefix)) {
                return ActionResult.PASS;
            }

            // Everything in the message except for the command prefix.
            String content = message.substring(commandPrefix.length());

            // Split the content into words.
            String[] words = content.split(" ");

            // The first word--used to get a module.
            String label = words[0];

            // The arguments to be passed to the command.
            String[] args = new String[words.length - 1];
            System.arraycopy(words, 1, args, 0, args.length);

            Optional<Module> optional = Palladium.getInstance().getModuleManager().getModuleOptional(label.toLowerCase());

            if (optional.isPresent()) {
                optional.get().parseArgs(args);
            }
            else {
                CommandError.sendErrorMessage(CommandError.MODULE_NOT_FOUND);
            }

            return ActionResult.FAIL;
        });
    }
}
