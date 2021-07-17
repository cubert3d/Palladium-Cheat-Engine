package me.cubert3d.palladium.module.command;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.CommandCallback;
import me.cubert3d.palladium.module.modules.Module;
import me.cubert3d.palladium.module.modules.command.PalladiumCommand;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;

import java.util.Optional;

@ClassInfo(
        description = "Listens for commands in the player's chat HUD.",
        authors = "cubert3d",
        date = "3/6/2021",
        type = ClassType.LISTENER
)

public final class CommandListener {

    private CommandListener() {}

    public static void registerListener() {
        CommandCallback.EVENT.register((player, message) -> {
            final String prefix = PalladiumCommand.getPrefix();
            if (!message.startsWith(prefix)) {
                return ActionResult.PASS;
            }
            else {
                // Put the command to the player's chat HUD.
                String[] labelAndArgs = message.substring(prefix.length()).split(" ");
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText(">> " + String.join(" ", labelAndArgs)));
                processCommand(labelAndArgs);
                return ActionResult.FAIL;
            }
        });
    }

    public static void processCommand(String[] labelAndArgs) {



        // The first word--used to get a module.
        String label = labelAndArgs[0];

        // The arguments to be passed to the command.
        String[] args = new String[labelAndArgs.length - 1];
        System.arraycopy(labelAndArgs, 1, args, 0, args.length);

        Optional<Module> optional = Palladium.getInstance().getModuleManager().getModuleOptional(label.toLowerCase());

        if (optional.isPresent()) {
            optional.get().parseArgs(args);
        }
        else {
            CommandError.sendErrorMessage(CommandError.MODULE_NOT_FOUND);
        }
    }
}
