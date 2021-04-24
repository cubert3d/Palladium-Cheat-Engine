package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.TextHudRenderer;
import me.cubert3d.palladium.gui.text.TextList;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/17/2021"
)

public final class EffectListModule extends Module {

    public static final int MAX_DURATION = 356400;

    private static final TextList effectList;

    public EffectListModule() {
        super("Effects", "Displays the player's current effects and their remaining duration.",
                ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onEnable() {
        TextHudRenderer.getTextManager().setTopRightList(effectList);
    }

    @Override
    protected void onDisable() {
        TextHudRenderer.getTextManager().clearTopRightList();
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

    static {
        effectList = new TextList(
                () -> {
                    int count = Common.getPlayer().getStatusEffects().size();
                    return new ColorText("Status Effects (" + count + ")");
                },
                () -> {
                    ArrayList<ColorText> text = new ArrayList<>();

                    for (Map.Entry<StatusEffect, StatusEffectInstance> entry : Common.getPlayer().getActiveStatusEffects().entrySet()) {

                        String effectName = entry.getKey().getName().getString();
                        String timeLeft = getTimeLeft(entry.getValue().getDuration() / 20);
                        int color = getColorFromType(entry.getKey().getType());

                        text.add(new ColorText(effectName + " (" + timeLeft + ")", Colors.WHITE, color));
                    }

                    return text;
                }
        );
    }
}
