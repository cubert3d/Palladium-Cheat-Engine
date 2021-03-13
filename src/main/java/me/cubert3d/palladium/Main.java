package me.cubert3d.palladium;

import me.cubert3d.palladium.cmd.CommandListener;
import me.cubert3d.palladium.network.PacketListener;
import me.cubert3d.palladium.module.ModuleList;
import me.cubert3d.palladium.util.ChatFilter;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.fabricmc.api.ModInitializer;

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

public final class Main implements ModInitializer {

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
		ModuleList.fillModuleMap();
		System.out.println(String.format("Loaded %d modules (%d available, %d debug-only)",
				ModuleList.getNumModules(), ModuleList.getNumAvailableModules(), ModuleList.getNumModules() - ModuleList.getNumAvailableModules()));

		// Listeners
		PacketListener.registerListener();
		CommandListener.registerListener();
	}

	public static boolean isDebugModeEnabled() {
		return debug;
	}
}
