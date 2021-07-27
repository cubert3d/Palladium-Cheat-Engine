package me.cubert3d.palladium.network;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

import java.util.HashMap;
import java.util.Map;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.MANAGER
)

public final class PacketLogger {

    private final Map<String, Long> delayMap;
    private boolean alternateColor;
    private final PacketFormatter formatter;

    public PacketLogger() {
        this.delayMap = new HashMap<>(0);
        this.alternateColor = false;
        this.formatter = new PacketFormatter();
    }

    public final void offerPacket(PacketEntry packetEntry, int delay) {
        boolean accept = true;
        Long prevTime = delayMap.get(packetEntry.getPacketName());
        if (prevTime != null) {
            accept = (packetEntry.getTime() - prevTime) >= delay;
        }
        if (accept) {
            pushPacket(packetEntry);
        }
    }

    private void pushPacket(PacketEntry packetEntry) {
        this.delayMap.put(packetEntry.getPacketName(), packetEntry.getTime());
        this.printPacket(packetEntry);
    }

    private void printPacket(PacketEntry packetEntry) {
        String message = formatter.formatPacket(packetEntry);
        printToChatHud(message);
    }

    private void printToChatHud(String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText(getNextColorFormatter().concat(message)));
    }

    private String getNextColorFormatter() {
        alternateColor = !alternateColor;
        return alternateColor ? "§b" : "§3";
    }
}
