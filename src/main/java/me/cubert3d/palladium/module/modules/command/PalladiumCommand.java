package me.cubert3d.palladium.module.modules.command;

import me.cubert3d.palladium.Palladium;
import me.cubert3d.palladium.module.modules.CommandModule;
import me.cubert3d.palladium.module.setting.single.StringSetting;
import me.cubert3d.palladium.util.annotation.ClassInfo;
import me.cubert3d.palladium.util.annotation.ClassType;

@ClassInfo(
        authors = "cubert3d",
        date = "7/15/2021",
        type = ClassType.MODULE
)

public final class PalladiumCommand extends CommandModule {

    private static final StringSetting prefixSetting = new StringSetting("Prefix", "The prefix for using the command line interface.", ".");

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
