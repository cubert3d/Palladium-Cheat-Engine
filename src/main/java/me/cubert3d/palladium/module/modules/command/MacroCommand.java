package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.command.CommandError;
import me.cubert3d.palladium.module.macro.Macro;
import me.cubert3d.palladium.module.macro.MacroManager;
import me.cubert3d.palladium.module.modules.CommandModule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MacroCommand extends CommandModule {

    private static final int PAGE_SIZE = 10;

    public MacroCommand() {
        super("Macro", "Manages macros.");
    }

    @Override
    protected final void execute(String @NotNull [] args) {
        if (args.length == 0) {
            printToChatHud("§eThere are currently " + getMacroManager().getMacroList().size() + " macros");
        }
        else {
            switch (args[0].toLowerCase()) {
                case "list":
                    // >> macro list
                    if (args.length == 1) {
                        Optional<ArrayList<Macro>> page = getPage(1);
                        if (page.isPresent()) {
                            String list = printMacroList(page.get());
                            printToChatHud("§ePage 1 of macros: " + list);
                        }
                        else {
                            CommandError.sendErrorMessage(CommandError.NUMBER_OUT_OF_BOUNDS);
                            return;
                        }
                    }
                    // >> macro list [page/keyword]
                    else {
                        // >> macro list [page number]
                        try {
                            int pageNumber = Integer.parseInt(args[1]);

                            // Only two arguments are needed for list by page number.
                            if (args.length > 2) {
                                CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
                            }

                            Optional<ArrayList<Macro>> page = getPage(pageNumber);
                            if (page.isPresent()) {
                                String list = printMacroList(page.get());
                                printToChatHud("§ePage " + pageNumber + " of macros: " + list);
                            }
                            else {
                                CommandError.sendErrorMessage(CommandError.NUMBER_OUT_OF_BOUNDS);
                                return;
                            }
                        }
                        // If args[1] fails to parse as an integer, then use it and any remaining args as a search keyword.
                        // >> macro list [keyword]
                        catch (NumberFormatException ignored) {

                            // Copy the remaining arguments over into a string.
                            String[] remainingArgs = new String[args.length - 1];
                            System.arraycopy(args, 1, remainingArgs, 0, remainingArgs.length);
                            String keyword = String.join(" ", remainingArgs);

                            Optional<ArrayList<Macro>> search = getSearch(keyword);
                            if (search.isPresent()) {
                                String list = printMacroList(search.get());
                                printToChatHud("§eMacros containing \"" + keyword + "\": " + list);
                            }
                            else {
                                printToChatHud("§cNo macros containing \"" + keyword + "\" found!");
                                return;
                            }
                        }
                    }
                    break;

                case "search":
                    if (args.length < 2) {
                        CommandError.sendErrorMessage(CommandError.TOO_FEW_ARGUMENTS);
                    }
                    else {
                        // Copy the remaining arguments over into a string.
                        String[] remainingArgs = new String[args.length - 1];
                        System.arraycopy(args, 1, remainingArgs, 0, remainingArgs.length);
                        String keyword = String.join(" ", remainingArgs);

                        Optional<ArrayList<Macro>> search = getSearch(keyword);
                        if (search.isPresent()) {
                            String list = printMacroList(search.get());
                            printToChatHud("§eMacros containing \"" + keyword + "\": " + list);
                        }
                        else {
                            printToChatHud("§cNo macros containing \"" + keyword + "\" found!");
                            return;
                        }
                    }
                    break;

                case "create":
                    // >> macro add <name>
                    if (args.length < 2) {
                        CommandError.sendErrorMessage(CommandError.TOO_FEW_ARGUMENTS);
                        return;
                    }
                    else {
                        String[] remainingArgs = new String[args.length - 1];
                        System.arraycopy(args, 1, remainingArgs, 0, remainingArgs.length);
                        String newMacroName = String.join(" ", remainingArgs);
                        Macro macro = getMacroManager().createMacro(newMacroName);
                        printToChatHud("§eCreated new macro: " + macro.getName() + " (" + macro.getID() + ")");
                    }
                    break;

                case "delete":
                    // >> macro remove <name>
                    if (args.length < 2) {
                        CommandError.sendErrorMessage(CommandError.TOO_FEW_ARGUMENTS);
                        return;
                    }
                    else {
                        String[] remainingArgs = new String[args.length - 1];
                        System.arraycopy(args, 1, remainingArgs, 0, remainingArgs.length);
                        String newMacroName = String.join(" ", remainingArgs);
                        Optional<Macro> optional = getMacroManager().removeMacro(newMacroName);
                        if (optional.isPresent()) {
                            printToChatHud("§eDeleted macro \"" + optional.get().getName() + " (" + optional.get().getID() + ")\"");
                        }
                        else {
                            printToChatHud("§cCould not find macro \"" + newMacroName + "\"");
                        }
                    }
                    break;

                default:
                    // >> macro [macro_id]...
                    Optional<Macro> optional = getMacroManager().getMacro(args[0]);
                    if (optional.isPresent()) {
                        Macro macro = optional.get();

                        // >> macro <macro_id>
                        if (args.length == 1) {
                            printToChatHud(String.format("§eMacro %s (%s) has %d commands", macro.getName(), macro.getID(), macro.getCommands().size()));
                        }

                        // >> macro <macro_id> [execute/commands/add/insert/remove]
                        else {
                            switch (args[1].toLowerCase()) {

                                // >> macro <macro_id> execute [command index]
                                case "execute":
                                    if (args.length == 2) {
                                        printToChatHud(String.format("§eExecuting macro %s (%s)...", macro.getName(), macro.getID()));
                                        macro.execute();
                                    }
                                    else if (args.length == 3) {
                                        try {
                                            int commandIndex = Integer.valueOf(args[2]);

                                            // Check if the passed index is within the bounds.
                                            if (commandIndex >= 0 && commandIndex < macro.getCommands().size()) {
                                                macro.executeLine(commandIndex);
                                            }
                                            else {
                                                CommandError.sendErrorMessage(CommandError.NUMBER_OUT_OF_BOUNDS);
                                                return;
                                            }
                                        }
                                        catch (NumberFormatException ignored) {
                                            CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                                            return;
                                        }
                                    }
                                    else {
                                        CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
                                        return;
                                    }
                                    break;

                                // >> macro <macro_id> commands
                                case "commands":
                                    if (args.length == 2) {
                                        if (macro.getCommands().size() > 0) {
                                            printToChatHud(String.format("§eCommands in macro %s (%s):", macro.getName(), macro.getID()));
                                            for (int i = 0; i < macro.getCommands().size(); i++) {
                                                printToChatHud(String.format("§e%d: %s", i, macro.getCommands().get(i)));
                                            }
                                        }
                                    }
                                    else {
                                        CommandError.sendErrorMessage(CommandError.TOO_MANY_ARGUMENTS);
                                        return;
                                    }
                                    break;

                                // >> macro <macro_id> add <command>
                                case "add":
                                    if (args.length < 3) {
                                        CommandError.sendErrorMessage(CommandError.TOO_FEW_ARGUMENTS);
                                        return;
                                    }
                                    else {
                                        String[] remainingArgs = new String[args.length - 2];
                                        System.arraycopy(args, 2, remainingArgs, 0, remainingArgs.length);
                                        String command = String.join(" ", remainingArgs);
                                        macro.addCommand(command);
                                        printToChatHud(String.format("§eAdded command \"%s\" to macro %s (%s)", command, macro.getName(), macro.getID()));
                                    }
                                    break;

                                // >> macro <macro_id> insert <index> <command>
                                case "insert":
                                    if (args.length < 4) {
                                        CommandError.sendErrorMessage(CommandError.TOO_FEW_ARGUMENTS);
                                        return;
                                    }
                                    else {
                                        try {
                                            int index = Integer.valueOf(args[2]);

                                            // Check if the passed index is within the bounds.
                                            if (index >= 0 && index < macro.getCommands().size()) {
                                                // Create the command string from the remaining arguments after args[2]
                                                String[] remainingArgs = new String[args.length - 3];
                                                System.arraycopy(args, 3, remainingArgs, 0, remainingArgs.length);
                                                String command = String.join(" ", remainingArgs);
                                                macro.insertCommand(index, command);
                                                printToChatHud(String.format("§eInserted command \"%s\" to macro %s (%s) at index %d", command, macro.getName(), macro.getID(), index));
                                            }
                                            else {
                                                CommandError.sendErrorMessage(CommandError.NUMBER_OUT_OF_BOUNDS);
                                                return;
                                            }
                                        }
                                        catch (NumberFormatException ignored) {
                                            CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                                            return;
                                        }
                                    }
                                    break;

                                // >> macro <macro_id> remove <index>
                                case "remove":
                                    if (args.length == 3) {
                                        try {
                                            int index = Integer.valueOf(args[2]);

                                            // Check if the passed index is within the bounds.
                                            if (index >= 0 && index < macro.getCommands().size()) {
                                                String command = macro.removeCommand(index);
                                                printToChatHud(String.format("§eRemoved command \"%s\" from macro %s (%s) at index %d", command, macro.getName(), macro.getID(), index));
                                            }
                                            else {
                                                CommandError.sendErrorMessage(CommandError.NUMBER_OUT_OF_BOUNDS);
                                                return;
                                            }
                                        }
                                        catch (NumberFormatException ignored) {
                                            CommandError.sendErrorMessage(CommandError.INVALID_ARGUMENTS);
                                            return;
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                    else {
                        printToChatHud("§cCould not find macro \"" + args[0] + "\"");
                    }
                    break;
            }
        }
    }

    private String printMacroList(@NotNull ArrayList<Macro> macros) {
        String list = "";
        int counter = 0;
        for (Macro macro : macros) {
            list = list.concat(macro.getName() + " (" + macro.getID() + ")");
            if (counter < macros.size() - 1) {
                list = list.concat(", ");
            }
            counter++;
        }
        return list;
    }

    private Optional<ArrayList<Macro>> getPage(final int pageNumber) {
        if (pageNumber > 0) {

            ArrayList<Macro> macros = getMacroManager().getMacroList();
            ArrayList<Macro> page = new ArrayList<>();

            final int startIndex = (pageNumber - 1) * PAGE_SIZE;
            final int endIndex = Math.min(pageNumber * PAGE_SIZE, macros.size());

            if (endIndex > startIndex) {
                for (int i = startIndex; i < endIndex; i++) {
                    page.add(macros.get(i));
                }
                if (page.size() > 0) {
                    return Optional.of(page);
                }
            }
        }

        return Optional.empty();
    }

    private Optional<ArrayList<Macro>> getSearch(String keyword) {
        ArrayList<Macro> search = getMacroManager().getMacroList()
                .stream()
                .filter(macro -> macro.getName().toLowerCase().contains(keyword.toLowerCase()) || macro.getID().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
        if (search.size() > 0) {
            return Optional.of(search);
        }
        else {
            return Optional.empty();
        }
    }

    private MacroManager getMacroManager() {
        return Palladium.getInstance().getMacroManager();
    }
}
