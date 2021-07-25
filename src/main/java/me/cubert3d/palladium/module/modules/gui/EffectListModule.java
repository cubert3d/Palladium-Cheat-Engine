package me.cubert3d.palladium.module.modules.gui;

import me.cubert3d.palladium.gui.text.ColorText;
import me.cubert3d.palladium.gui.text.Colors;
import me.cubert3d.palladium.gui.text.TextProvider;
import me.cubert3d.palladium.gui.window.TextProviderWindow;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

@ClassInfo(
        authors = "cubert3d",
        date = "4/17/2021",
        type = ClassType.MODULE
)

public final class EffectListModule extends AbstractHudModule {

    private final EffectListProvider effectList;

    public EffectListModule() {
        super("Effects", "Displays the player's current effects and their remaining duration.");
        this.effectList = new EffectListProvider();
    }

    @Override
    protected final void onEnable() {
        super.onEnable();
        getTextManager().setTopRightList(effectList);
    }

    @Override
    protected final void onDisable() {
        super.onDisable();
        getTextManager().clearTopRightList();
    }

    @Override
    protected final TextProviderWindow createWindow() {
        TextProviderWindow newWindow = TextProviderWindow.newDisplayWindow("effect_list", effectList, this);
        newWindow.setX(25);
        newWindow.setY(25);
        newWindow.setWidth(150);
        newWindow.setHeight(91);
        newWindow.setColor(Colors.BACKGROUND_LAVENDER);
        return newWindow;
    }

    private static class EffectListProvider extends TextProvider {

        public final int maxDuration = 356400;
        private int numberEffects = 0;

        private EffectListProvider() {
            super();
        }

        @Override
        public ColorText getTitle() {
            return new ColorText("Status Effects (" + numberEffects + ")");
        }

        @Override
        public ArrayList<ColorText> getBody() {
            ArrayList<ColorText> text = new ArrayList<>();
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            if (player != null) {
                int counter = 0;
                for (Map.Entry<StatusEffect, StatusEffectInstance> entry : player.getActiveStatusEffects().entrySet()) {

                    StatusEffect effect = entry.getKey();
                    StatusEffectInstance effectInstance = entry.getValue();

                    String string;
                    String effectName = effect.getName().getString();
                    String level = convertArabicToRoman(effectInstance.getAmplifier() + 1);
                    String timeLeft = getTimeLeft(effectInstance.getDuration() / 20);
                    int color = getColorFromType(effect.getType());

                    string = String.format("%s %s (%s)", effectName, level, timeLeft);

                    text.add(new ColorText(string, Colors.WHITE, color));
                    counter++;
                }
                numberEffects = counter;
            }

            return text;
        }

        private @NotNull String getTimeLeft(int duration) {
            if (duration <= maxDuration) {
                final int SECOND = 1;
                final int MINUTE = 60;
                final int HOUR = 3600;

                int secondsLeft = (duration / SECOND) % MINUTE;
                int minutesLeft = (duration / MINUTE) % MINUTE;
                int hoursLeft = duration / HOUR;

                if (hoursLeft > 0) {
                    return String.format("%02d:%02d:%02d", hoursLeft, minutesLeft, secondsLeft);
                }
                else {
                    return String.format("%02d:%02d", minutesLeft, secondsLeft);
                }
            }
            else {
                return "99+ Hours";
            }
        }

        private int getColorFromType(@NotNull StatusEffectType type) {
            switch (type) {
                case BENEFICIAL: return Colors.BACKGROUND_GREEN;
                case HARMFUL: return Colors.BACKGROUND_RED;
                default:
                case NEUTRAL: return Colors.BACKGROUND_LAVENDER;
            }
        }

        private @NotNull String convertArabicToRoman(int number) {
            switch (number) {
                case 1:
                    return "I";
                case 2:
                    return "II";
                case 3:
                    return "III";
                case 4:
                    return "IV";
                case 5:
                    return "V";
                case 6:
                    return "VI";
                case 7:
                    return "VII";
                case 8:
                    return "VIII";
                case 9:
                    return "IX";
                case 10:
                    return "X";
                default:
                    return Integer.valueOf(number).toString();
            }
        }
    }
}
