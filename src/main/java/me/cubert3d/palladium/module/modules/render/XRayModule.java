package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.AbstractModule;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public final class XRayModule extends AbstractModule {

    private static final Block[] ores = new Block[]{
            Blocks.COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.GOLD_ORE,
            Blocks.LAPIS_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.NETHER_GOLD_ORE
    };

    public XRayModule() {
        super("XRay", "Lets you see ores in the ground", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    // Whether a block should be made invisible when X-Ray is enabled.
    public static boolean isXRayPassable(Block block) {
        for (Block ore : ores) {
            if (block.is(ore))
                return false;
        }
        return true;
    }
}
