package me.cubert3d.palladium;

import me.cubert3d.palladium.module.macro.Macro;
import me.cubert3d.palladium.module.macro.MacroManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
            FileInputStream fileInputStream = new FileInputStream(fullFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Macro> macros = (ArrayList<Macro>) objectInputStream.readObject();
            macroManager.loadList(macros);
        } catch (IOException | ClassNotFoundException e) {
            Palladium.getLogger().error("Error reading macro config file!");
        }
    }

    private void write() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fullFileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(macroManager.getMacroList());
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            Palladium.getLogger().error("Error writing to macro config file!");
        }
    }
}
