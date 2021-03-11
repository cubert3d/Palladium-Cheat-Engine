package me.cubert3d.palladium.cmd;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.event.callback.PlayerChatCallback;
import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.annotation.ClassDescription;
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

    public static void registerListener() {
        PlayerChatCallback.EVENT.register((player, message) -> {

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
            for (int i = 0; i < args.length; i++)
                args[i] = words[i + 1];

            AbstractModule module = ModuleList.getModule(label.toLowerCase());

            if (module != null) {

                // Check whether the module is a toggleable module,
                // or an execute-once command.

                switch (module.getType()) {

                    case TOGGLE:

                        // Check the number of arguments.
                        // 0: report whether the module is enabled or disabled.
                        // 1: enable or disable the module. (args[0] = enable/disable/toggle)
                        // 2: change a setting. (args[0] = setting name, args[1] = new value)

                        switch (args.length) {
                            // Report whether the module is enabled or disabled.
                            case 0:
                                if (module.isEnabled())
                                    Common.sendMessage(module.getName() + " is currently enabled");
                                else
                                    Common.sendMessage(module.getName() + " is currently disabled");
                                break;
                            // Enable, disable, or toggle the module.
                            case 1:
                                switch (args[0].toLowerCase()) {
                                    case "enable":
                                        module.enable();
                                        Common.sendMessage(module.getName() + " is now enabled");
                                        break;
                                    case "disable":
                                        module.disable();
                                        Common.sendMessage(module.getName() + " is now disabled");
                                        break;
                                    case "toggle":
                                        if (module.toggle())
                                            Common.sendMessage(module.getName() + " is now enabled");
                                        else
                                            Common.sendMessage(module.getName() + " is now disabled");
                                        break;
                                    default:
                                        CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                                }
                                break;
                            // Change a setting (NOT IMPLEMENTED YET)
                            case 2:
                                break;
                            // Too many arguments!
                            default:
                                CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
                                break;
                        }
                        break;

                    case EXECUTE_ONCE:

                        // For now, just pass the arguments to the execute method
                        module.execute(args);

                        break;
                }
            }
            else {
                CommandError.sendErrorMessage(CommandError.MODULE_NOT_FOUND);
            }


            return ActionResult.FAIL;
        });
    }
}
