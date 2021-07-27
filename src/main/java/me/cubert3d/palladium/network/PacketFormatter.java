package me.cubert3d.palladium.network;

import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.network.Packet;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@ClassInfo(
        authors = "cubert3d",
        date = "7/25/2021",
        type = ClassType.MISC
)

public final class PacketFormatter {

    public PacketFormatter() {

    }

    String formatPacket(PacketEntry packetEntry) {

        String sentOrReceived = packetEntry.getType().equals(PacketEntry.Type.CLIENT_TO_SERVER) ? "SENT" : "RECEIVED";
        String packetName = packetEntry.getPacketName();
        String information = formatPacketInformation(packetEntry);
        String message = String.format("%s: %s (%s)", sentOrReceived, packetName, information);

        if (packetEntry.wasCancelled()) {
            message = message.concat(" (CANCELLED)");
        }
        return message;
    }

    private String formatPacketInformation(PacketEntry packetEntry) {

        Method[] methods = Arrays.stream(packetEntry.getPacket().getClass().getDeclaredMethods())
                .filter(method -> method.getDeclaringClass().equals(packetEntry.getPacket().getClass()))
                .filter(method -> method.getParameterCount() == 0)
                .filter(method -> !method.getReturnType().equals(Void.class))
                .toArray(Method[]::new);
        StringBuilder info = new StringBuilder();

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Optional<String> methodReturnValue = formatMethod(method, packetEntry.getPacket());
            if (methodReturnValue.isPresent()) {
                info.append(String.format("%s: %s", method.getName(), methodReturnValue.get()));
                if (i < (methods.length - 1)) {
                    info.append(", ");
                }
            }
        }

        return info.toString();
    }

    private Optional<String> formatMethod(Method method, Packet<?> packet) {

        // TODO: revise this autismo method

        Object object = null;
        try {
            object = method.invoke(packet);
        } catch (ReflectiveOperationException ignored) {

        }
        // If this object is null--that is, the method's return type was void--return an empty optional.
        if (object == null) {
            return Optional.empty();
        }
        // If this object does not override toString, then use a simpler version of it instead.
        if (Arrays.stream(object.getClass().getDeclaredMethods()).noneMatch(objMethod -> objMethod.getName().equals("toString"))) {
            return Optional.of(object.getClass().getSimpleName());
        }
        if (object instanceof Collection) {
            StringBuilder stringBuilder = new StringBuilder();
            Collection collection = (Collection) object;
            int counter = 0;
            final int limit = 10;
            final int size = collection.size();

            for (Object obj : collection) {
                if (counter < limit) {
                    stringBuilder.append(obj.toString());
                    if (counter < size - 1) {
                        stringBuilder.append(", ");
                    }
                }
                else {
                    stringBuilder.append(size - counter).append(" more...");
                    break;
                }
                counter++;
            }

            return Optional.of(stringBuilder.toString());
        }
        if (object instanceof Float || object instanceof Double) {
            return Optional.of(String.format("%.5f", object));
        }

        return Optional.of(object.toString());
    }
}
