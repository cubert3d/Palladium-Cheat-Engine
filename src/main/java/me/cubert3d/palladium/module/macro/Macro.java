package me.cubert3d.palladium.module.macro;

import me.cubert3d.palladium.module.command.CommandListener;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

import java.util.ArrayList;

@ClassInfo(
        authors = "cubert3d",
        date = "7/16/2021",
        type = ClassType.MACRO
)

public final class Macro {

    private final String name;
    private final String id;
    private final ArrayList<String> commands;

    public Macro(String name) {
        this.name = name.trim();
        this.id = name.trim().toLowerCase().replaceAll(" ", "_");
        this.commands = new ArrayList<>();
    }

    public final String getName() {
        return name;
    }

    public final String getID() {
        return id;
    }

    public final ArrayList<String> getCommands() {
        return commands;
    }

    public final void addCommand(String command) {
        commands.add(command);
    }

    public final void insertCommand(int index, String command) {
        commands.add(index, command);
    }

    public final String removeCommand(int index) {
        return commands.remove(index);
    }

    public final void execute() {
        for (String command : commands) {
            CommandListener.processCommand(command.split(" "));
        }
    }

    public final void executeLine(int index) {
        CommandListener.processCommand(commands.get(index).split(" "));
    }
}
