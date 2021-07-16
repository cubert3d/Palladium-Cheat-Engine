package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.realms.gui.screen.RealmsBridgeScreen;
import net.minecraft.text.TranslatableText;

@ClassInfo(
        authors = "cubert3d",
        date = "7/15/2021",
        type = ClassType.MODULE
)

public final class DisconnectCommand extends CommandModule {

    public DisconnectCommand() {
        super("Disconnect", "Disconnects the player from the server.");
    }

    @Override
    protected final void execute(String[] args) {
        MinecraftClient client = MinecraftClient.getInstance();
        boolean singlePlayer = client.isInSingleplayer();
        boolean realms = client.isConnectedToRealms();

        if (client.world != null) {
            client.world.disconnect();

            // Put the "saving level" screen if the player was in single player.
            if (singlePlayer) {
                client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
            }
            else {
                client.disconnect();
            }

            /*
            Go directly to the title screen if the player was in single player,
            to the realms menu if they were in realms, or to the multiplayer
            server list if they were in multiplayer.
             */
            if (singlePlayer) {
                client.openScreen(new TitleScreen());
            }
            else if (realms) {
                RealmsBridgeScreen realmsBridgeScreen = new RealmsBridgeScreen();
                realmsBridgeScreen.switchToRealms(new TitleScreen());
            }
            else {
                client.openScreen(new MultiplayerScreen(new TitleScreen()));
            }
        }
    }
}
