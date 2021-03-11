package me.cubert3d.palladium;

import me.cubert3d.palladium.cmd.CommandListener;
import me.cubert3d.palladium.event.listener.PacketListener;
import me.cubert3d.palladium.module.ModuleList;
import net.fabricmc.api.ModInitializer;

public final class Main implements ModInitializer {

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
