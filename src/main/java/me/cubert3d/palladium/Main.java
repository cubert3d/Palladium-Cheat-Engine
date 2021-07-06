package me.cubert3d.palladium;

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

	@Override
	public void onInitialize() {
		Palladium client = new Palladium();
		client.initialize();
	}
}
