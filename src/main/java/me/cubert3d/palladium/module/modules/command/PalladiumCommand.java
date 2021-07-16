package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.module.setting.single.StringSetting;

public final class PalladiumCommand extends CommandModule {

    private static final StringSetting prefixSetting = new StringSetting("Prefix", ".");

    public PalladiumCommand() {
        super("Palladium", "Shows client information, and contains general settings.");
        this.addSetting(prefixSetting);
    }

    @Override
    protected final void execute(String[] args) {
        printToChatHud("Running " + Palladium.NAME + " " + Palladium.VERSION);
    }

    public static String getPrefix() {
        return prefixSetting.getValue();
    }
}
