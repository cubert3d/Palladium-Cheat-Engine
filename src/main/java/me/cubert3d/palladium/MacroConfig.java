package me.cubert3d.palladium;

import me.cubert3d.palladium.module.macro.Macro;
import me.cubert3d.palladium.module.macro.MacroManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public final class MacroConfig {

    private static String fileDirectory = "palladium";
    private static String fileName = "macros.txt";
    private static String fullFileName = fileDirectory + "/" + fileName;

    private final MacroManager macroManager;
    private int readCounter;
    private int writeCounter;

    public MacroConfig(MacroManager macroManager) {
        this.macroManager = macroManager;
        readCounter = 1;
        writeCounter = 1;
    }

    private void createFile() {
        File path = new File(fileDirectory);
        File file = new File(fullFileName);
        try {
            boolean pathCreated = path.mkdir();
            boolean fileCreated = file.createNewFile();

            if (pathCreated) {
                Palladium.getLogger().info("Created Palladium directory file");
            }
            if (fileCreated) {
                Palladium.getLogger().info("Created macro config file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        Palladium.getLogger().info("Loading macro config...");
        createFile();
        read();
        Palladium.getLogger().info("Done loading macro config!");
    }

    public void saveConfig() {
        Palladium.getLogger().info("Saving macro config...");
        createFile();
        write();
        Palladium.getLogger().info("Done saving macro config!");
    }

    private void read() {

        try {
            Scanner scanner = new Scanner(new File(fullFileName));
            Macro currentMacro = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("\t")) {
                    String macroName = line.replaceAll("\n", "");
                    currentMacro = new Macro(macroName);
                    Palladium.getInstance().getMacroManager().addMacro(currentMacro);
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

    private void write() {
        try {
            FileWriter writer = new FileWriter(fullFileName);
            ArrayList<Macro> macros = Palladium.getInstance().getMacroManager().getMacroList();
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
