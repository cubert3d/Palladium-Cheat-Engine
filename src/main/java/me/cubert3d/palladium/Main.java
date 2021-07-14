package me.cubert3d.palladium;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.fabricmc.api.ModInitializer;

@ClassInfo(
		description = "Entrypoint class for Palladium Cheat Engine.",
		authors = "cubert3d",
		date = "3/1/2021",
		type = ClassType.MAIN
)

public final class Main implements ModInitializer {

	@Override
	public void onInitialize() {
		Palladium client = new Palladium();
		client.initialize();
	}
}
