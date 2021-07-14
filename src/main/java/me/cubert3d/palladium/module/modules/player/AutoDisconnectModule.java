package me.cubert3d.palladium.module.modules.player;

import me.cubert3d.palladium.event.callback.HealthUpdateCallback;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.single.DoubleSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.realms.gui.screen.RealmsBridgeScreen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/14/2021"
)

public final class AutoDisconnectModule extends ToggleModule {

    private final DoubleSetting threshold;

    public AutoDisconnectModule() {
        super("AutoDisconnect", "Disconnects you once you fall to a certain health.", ModuleDevStatus.AVAILABLE);
        this.threshold = new DoubleSetting("Threshold", 4.0, 19.0);
        this.addSetting(threshold);
    }

    @Override
    public final @NotNull Optional<String> getPrimaryInfo() {
        return Optional.of(threshold.getValue().toString());
    }

    @Override
    public final void onLoad() {
        HealthUpdateCallback.EVENT.register(health -> {
            if (isEnabled() && health <= threshold.getValue()) {
                disconnect();
            }
            return ActionResult.PASS;
        });
    }

    private void disconnect() {
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
