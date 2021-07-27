package me.cubert3d.palladium.network;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.network.Packet;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.MISC
)

public final class PacketEntry {

    private final Packet<?> packet;
    private final Type type;
    private final boolean cancelled;
    private final long time;

    public PacketEntry(Packet<?> packet, Type type, boolean cancelled) {
        this.packet = packet;
        this.type = type;
        this.cancelled = cancelled;
        this.time = System.currentTimeMillis();
    }

    public final Packet<?> getPacket() {
        return packet;
    }

    public final String getPacketName() {
        return packet.getClass().getSimpleName();
    }

    /**
     * <p>
     *     Returns whether this packet is client-to-server, or server-to-client.
     * </p>
     * @return {@code CLIENT_TO_SERVER} if this packet is client-to-server, or {@code SERVER_TO_CLIENT} if it is server-to-client
     */
    public final Type getType() {
        return type;
    }

    /**
     * <p>
     *     Returns whether or not this packet was cancelled. Only client-to-server packets can be cancelled;
     *     therefore, this method will return {@code true} if and only if this packet is client-to-server,
     *     and if this packet was actually cancelled.
     * </p>
     * @return {@code true} if this packet was cancelled, {@code false} if not
     */
    public final boolean wasCancelled() {
        return getType().equals(Type.CLIENT_TO_SERVER) && cancelled;
    }

    /**
     * <p>
     *     Returns the time at which this packet was logged.
     * </p>
     * @return the system time when this packet was logged, in milliseconds
     */
    public final long getTime() {
        return time;
    }

    public enum Type {
        CLIENT_TO_SERVER,
        SERVER_TO_CLIENT
    }
}
