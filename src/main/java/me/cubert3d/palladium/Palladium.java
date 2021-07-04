package me.cubert3d.palladium;

import me.cubert3d.palladium.cmd.CommandListener;
import me.cubert3d.palladium.gui.ClickGUI;
import me.cubert3d.palladium.module.ModuleManager;
import me.cubert3d.palladium.network.PacketListener;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
Main class of Palladium Cheat Engine.
Handles initialization.
 */

@ClassDescription(
		authors = {
				"cubert3d"
		},
		date = "3/1/2021",
		status = "in-progress"
)

public final class Palladium implements ModInitializer {

	public static final String NAME = "Palladium Cheat Engine";
	public static final String VERSION = "0.1.1.1";
	private static final Logger LOGGER = LogManager.getLogger();

	/*
	In debug mode, modules and other features that are
	still in development can be used, and more statistics
	are reported to the console.
	 */
	private static boolean debug;

	@Override
	public void onInitialize() {

		debug = true;

		// Various initialization tasks
		ModuleManager.fillModuleSet();
		Palladium.getLogger().info(String.format("Loaded %d modules (%d available, %d debug-only)",
				ModuleManager.getNumModules(), ModuleManager.getNumAvailableModules(), ModuleManager.getNumModules() - ModuleManager.getNumAvailableModules()));

		// Listeners
		PacketListener.registerListener();
		CommandListener.registerListener();
	}

	public static boolean isDebugModeEnabled() {
		return debug;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
