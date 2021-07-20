package me.cubert3d.palladium.config;

import me.cubert3d.palladium.macro.Macro;
import me.cubert3d.palladium.macro.MacroManager;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public final class MacroConfig extends AbstractConfig {

    private final MacroManager macroManager;

    public MacroConfig(MacroManager macroManager) {
        super("macros.txt");
        this.macroManager = macroManager;
    }

    @Override
    protected final void read() {
        try {
            Scanner scanner = new Scanner(getFile());
            Macro currentMacro = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("\t")) {
                    String macroName = line.replaceAll("\n", "");
                    currentMacro = new Macro(macroName);
                    macroManager.addMacro(currentMacro);
                }
                else if (currentMacro != null) {
                    String command = line.replaceFirst("\t", "").replaceAll("\n", "");
                    currentMacro.addCommand(command);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void write() {
        try {
            FileWriter writer = new FileWriter(getFile());
            ArrayList<Macro> macros = macroManager.getMacroList();

            for (Macro macro : macros) {
                writer.write(macro.getName() + "\n");
                for (String command : macro.getCommands()) {
                    writer.write("\t" + command + "\n");
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
