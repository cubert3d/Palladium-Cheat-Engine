package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

@ClassInfo(
        authors = "cubert3d",
        date = "7/15/2021",
        type = ClassType.MODULE
)

public final class EchoCommand extends CommandModule {

    public EchoCommand() {
        super("Echo", "Says something to the server chat.");
    }

    @Override
    protected final void execute(String[] args) {
        String message = String.join(" ", args);
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.sendChatMessage(message);
        }
    }
}
