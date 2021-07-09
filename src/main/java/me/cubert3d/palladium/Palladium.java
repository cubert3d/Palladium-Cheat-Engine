package me.cubert3d.palladium;

import me.cubert3d.palladium.gui.GUIRenderer;
import me.cubert3d.palladium.input.CommandListener;
import me.cubert3d.palladium.module.ModuleGroupManager;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.network.PacketListener;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "7/5/2021"
)

public final class Palladium {

    public static final String NAME = "Palladium Cheat Engine";
    public static final String VERSION = "0.1.4";
    private static final Logger LOGGER = LogManager.getLogger();
    private static Palladium INSTANCE;

    private ModuleManager moduleManager;
    private ModuleGroupManager moduleGroupManager;
    private Configuration configuration;
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
        this.configuration = new Configuration();
        this.guiRenderer = new GUIRenderer();
        this.debug = true;

        // Various initialization tasks
        getModuleManager().initialize();
        getModuleGroupManager().initialize();
        getGuiRenderer().initialize();

        // Listeners
        PacketListener.registerListener();
        CommandListener.registerListener();
    }

    public void close() {
        getLogger().info("Closing Palladium Cheat Engine...");
        getConfiguration().saveConfig();
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

    public final Configuration getConfiguration() {
        return configuration;
    }

    public final GUIRenderer getGuiRenderer() {
        return guiRenderer;
    }

    public final boolean isDebugModeEnabled() {
        return debug;
    }
}
