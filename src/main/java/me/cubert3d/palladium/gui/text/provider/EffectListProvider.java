package me.cubert3d.palladium.gui.text.provider;

import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

@ClassInfo(
        authors = "cubert3d",
        date = "4/23/2021",
        type = ClassType.PROVIDER
)

public final class EffectListProvider extends TextProvider {

    public static final int MAX_DURATION = 356400;

    private int numberEffects = 0;

    public EffectListProvider() {
        
    }

    @Override
    public @NotNull ColorText getHeader() {
        return new ColorText("Status Effects (" + numberEffects + ")");
    }

    @Override
    public @NotNull ArrayList<ColorText> getBody() {
        ArrayList<ColorText> text = new ArrayList<>();

        int counter = 0;
        for (Map.Entry<StatusEffect, StatusEffectInstance> entry : Common.getPlayer().getActiveStatusEffects().entrySet()) {

            String effectName = entry.getKey().getName().getString();
            String timeLeft = getTimeLeft(entry.getValue().getDuration() / 20);
            int color = getColorFromType(entry.getKey().getType());

            text.add(new ColorText(effectName + " (" + timeLeft + ")", Colors.WHITE, color));
            counter++;
        }
        numberEffects = counter;

        return text;
    }

    @Contract(pure = true)
    private static @NotNull String getTimeLeft(int duration) {

        if (duration > MAX_DURATION)
            return "99+ Hours";

        final int SECOND = 1;
        final int MINUTE = 60;
        final int HOUR = 3600;

        int secondsLeft = (duration / SECOND) % MINUTE;
        int minutesLeft = (duration / MINUTE) % MINUTE;
        int hoursLeft = duration / HOUR;

        if (hoursLeft > 0)
            return String.format("%02d:%02d:%02d", hoursLeft, minutesLeft, secondsLeft);
        else
            return String.format("%02d:%02d", minutesLeft, secondsLeft);
    }

    private static int getColorFromType(@NotNull StatusEffectType type) {
        switch (type) {
            case BENEFICIAL: return Colors.BACKGROUND_GREEN;
            case HARMFUL: return Colors.BACKGROUND_RED;
            default:
            case NEUTRAL: return Colors.BACKGROUND_LAVENDER;
        }
    }
}
