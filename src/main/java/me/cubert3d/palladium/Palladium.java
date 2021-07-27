package me.cubert3d.palladium;

import me.cubert3d.palladium.config.MacroConfig;
import me.cubert3d.palladium.config.ModuleConfig;
import me.cubert3d.palladium.gui.GUIRenderer;
import me.cubert3d.palladium.macro.MacroManager;
import me.cubert3d.palladium.module.ModuleGroupManager;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.module.command.CommandListener;
import me.cubert3d.palladium.network.PacketLogger;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ClassInfo(
        description = "Primary class of Palladium Cheat Engine; holds client info, managers, etc.",
        authors = "cubert3d",
        date = "7/5/2021",
        type = ClassType.MAIN
)

public final class Palladium {

    public static final String NAME = "Palladium Cheat Engine";
    public static final String VERSION = "0.1.16.1";
    private static final Logger LOGGER = LogManager.getLogger();
    private static Palladium INSTANCE;

    private ModuleManager moduleManager;
    private ModuleGroupManager moduleGroupManager;
    private MacroManager macroManager;
    private ModuleConfig moduleConfig;
    private MacroConfig macroConfig;
    private PacketLogger packetLogger;
    private GUIRenderer guiRenderer;
    /*
    In debug mode, modules and other features that are
    still in development can be used, and more statistics
    are reported to the console.
     */
    private boolean debug;

    public Palladium() {
        INSTANCE = this;
    }

    public void initialize() {

        this.moduleManager = new ModuleManager();
        this.moduleGroupManager = new ModuleGroupManager(moduleManager);
        this.macroManager = new MacroManager();
        this.moduleConfig = new ModuleConfig();
        this.macroConfig = new MacroConfig(macroManager);
        this.packetLogger = new PacketLogger();
        this.guiRenderer = new GUIRenderer();
        this.debug = true;

        // Various initialization tasks
        getModuleManager().initialize();
        getModuleGroupManager().initialize();
        getGuiRenderer().initialize();
        getMacroConfig().load();

        // Listeners
        CommandListener.registerListener();
    }

    public void close() {
        getLogger().info("Closing Palladium Cheat Engine...");
        getModuleConfig().save();
        getMacroConfig().save();
        getLogger().info("Done!");
    }

    public static Palladium getInstance() {
        return INSTANCE;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public final ModuleManager getModuleManager() {
        return moduleManager;
    }

    public final ModuleGroupManager getModuleGroupManager() {
        return moduleGroupManager;
    }

    public final MacroManager getMacroManager() {
        return macroManager;
    }

    public final ModuleConfig getModuleConfig() {
        return moduleConfig;
    }

    public final MacroConfig getMacroConfig() {
        return macroConfig;
    }

    public final PacketLogger getPacketManager() {
        return packetLogger;
    }

    public final GUIRenderer getGuiRenderer() {
        return guiRenderer;
    }

    public final boolean isDebugModeEnabled() {
        return debug;
    }
}
