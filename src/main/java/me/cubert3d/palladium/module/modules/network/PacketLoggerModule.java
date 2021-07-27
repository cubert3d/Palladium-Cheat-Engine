package me.cubert3d.palladium.module.modules.network;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.event.callback.ReceivePacketCallback;
import me.cubert3d.palladium.event.callback.SendPacketCallback;
import me.cubert3d.palladium.module.modules.ToggleModule;
import me.cubert3d.palladium.module.setting.list.StringListSetting;
import me.cubert3d.palladium.module.setting.single.IntegerSetting;
import me.cubert3d.palladium.network.PacketEntry;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.network.Packet;

import java.util.Arrays;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.MODULE
)

public final class PacketLoggerModule extends ToggleModule {

    private static final String[] defaultFilter = {
            "PositionOnly",
            "LookOnly",
            "Both",
            "MoveRelative",
            "EntitySetHeadYawS2CPacket",
            "EntityTrackerUpdateS2CPacket",
            "WorldTimeUpdateS2CPacket",
            "EntityVelocityUpdateS2CPacket",
            "RotateAndMoveRelative",
            "EntityPositionS2CPacket",
            "ChunkDataS2CPacket",
            "LightUpdateS2CPacket",
            "UnloadChunkS2CPacket"
    };

    private final StringListSetting filterSetting;
    private final IntegerSetting delaySetting;

    public PacketLoggerModule() {
        super("PacketLogger", "Prints any packets that are sent or received.");
        this.filterSetting = new StringListSetting("Filter", "Any packet the name of which is in this list is not logged.", Arrays.asList(defaultFilter));
        this.delaySetting = new IntegerSetting("Delay", "How long, in milliseconds, before the same packet should be logged again.", 0, 0, 60000);
        this.addSetting(filterSetting);
        this.addSetting(delaySetting);
    }

    @Override
    public final void onLoad() {

        SendPacketCallback.EVENT.register((packet, isCancelled) -> {
            if (isEnabled() && !shouldFilterPacket(packet)) {
                PacketEntry packetEntry = new PacketEntry(packet, PacketEntry.Type.CLIENT_TO_SERVER, isCancelled);
                Palladium.getInstance().getPacketManager().offerPacket(packetEntry, delaySetting.getValue());
            }
        });

        ReceivePacketCallback.EVENT.register(packet -> {
            if (isEnabled() && !shouldFilterPacket(packet)) {
                PacketEntry packetEntry = new PacketEntry(packet, PacketEntry.Type.SERVER_TO_CLIENT, false);
                Palladium.getInstance().getPacketManager().offerPacket(packetEntry, delaySetting.getValue());
            }
        });
    }

    private boolean shouldFilterPacket(Packet<?> packet) {
        return filterSetting.getList().contains(packet.getClass().getSimpleName());
    }
}
