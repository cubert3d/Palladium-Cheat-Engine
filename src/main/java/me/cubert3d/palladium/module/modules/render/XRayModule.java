package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.Common;
import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.module.setting.list.BlockListSetting;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/10/2021",
        status = "in-progress"
)

public final class XRayModule extends Module {

    private static final Block[] ores = new Block[]{
            Blocks.COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.GOLD_ORE,
            Blocks.LAPIS_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.NETHER_GOLD_ORE,
            Blocks.ANCIENT_DEBRIS
    };

    private static final Block[] containers = new Block[]{
            Blocks.CHEST,
            Blocks.TRAPPED_CHEST,
            Blocks.FURNACE,
            Blocks.BLAST_FURNACE,
            Blocks.SMOKER,
            Blocks.HOPPER,
            Blocks.BARREL,
            Blocks.BREWING_STAND,
            Blocks.DISPENSER,
            Blocks.DROPPER,
            Blocks.SHULKER_BOX,
            Blocks.WHITE_SHULKER_BOX,
            Blocks.ORANGE_SHULKER_BOX,
            Blocks.MAGENTA_SHULKER_BOX,
            Blocks.LIGHT_BLUE_SHULKER_BOX,
            Blocks.YELLOW_SHULKER_BOX,
            Blocks.LIME_SHULKER_BOX,
            Blocks.PINK_SHULKER_BOX,
            Blocks.GRAY_SHULKER_BOX,
            Blocks.LIGHT_GRAY_SHULKER_BOX,
            Blocks.CYAN_SHULKER_BOX,
            Blocks.PURPLE_SHULKER_BOX,
            Blocks.BLUE_SHULKER_BOX,
            Blocks.BROWN_SHULKER_BOX,
            Blocks.GREEN_SHULKER_BOX,
            Blocks.RED_SHULKER_BOX,
            Blocks.BLACK_SHULKER_BOX
    };

    private static final Block[] special = new Block[]{
            Blocks.SPAWNER,
            Blocks.NETHER_PORTAL,
            Blocks.END_PORTAL,
            Blocks.END_PORTAL_FRAME,
            Blocks.END_GATEWAY,
            Blocks.ENDER_CHEST
    };

    private static final Block[] hazards = new Block[]{
            Blocks.LAVA,
            Blocks.TNT,
            Blocks.TRIPWIRE,
            Blocks.TRIPWIRE_HOOK,
            Blocks.DETECTOR_RAIL,
            Blocks.ACTIVATOR_RAIL,
            Blocks.OAK_PRESSURE_PLATE,
            Blocks.SPRUCE_PRESSURE_PLATE,
            Blocks.BIRCH_PRESSURE_PLATE,
            Blocks.JUNGLE_PRESSURE_PLATE,
            Blocks.DARK_OAK_PRESSURE_PLATE,
            Blocks.ACACIA_PRESSURE_PLATE,
            Blocks.CRIMSON_PRESSURE_PLATE,
            Blocks.WARPED_PRESSURE_PLATE,
            Blocks.STONE_PRESSURE_PLATE,
            Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE,
            Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE
    };

    private static final List<Block> defaultWhiteList = new ArrayList<>();
    private static final List<Block> whitelist = new ArrayList<>();

    public XRayModule() {
        super("XRay", "Lets you see ores in the ground", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
        this.addSetting(new BlockListSetting("Whitelist", defaultWhiteList));
    }

    @Override
    protected void onConstruct() {
        this.fillDefaultWhitelist();
    }

    @Override
    protected void onEnable() {
        Common.reloadRenderer();
    }

    @Override
    protected void onDisable() {
        Common.reloadRenderer();
    }

    @Override
    protected void onChangeSetting() {
        // shitty patchwork to use before i revise the old system
        this.getSettingByName("Whitelist").ifPresent(setting -> {
            whitelist.clear();
            whitelist.addAll(((BlockListSetting) setting).getList());
        });
        // The renderer doesn't need any reloading if X-Ray isn't even enabled.
        if (isEnabled())
            Common.reloadRenderer();
    }

    private void fillDefaultWhitelist() {
        defaultWhiteList.addAll(Arrays.asList(ores));
        defaultWhiteList.addAll(Arrays.asList(containers));
        defaultWhiteList.addAll(Arrays.asList(special));
        defaultWhiteList.addAll(Arrays.asList(hazards));
        whitelist.addAll(defaultWhiteList);
    }

    // Whether a block should be made invisible when X-Ray is enabled.
    public static boolean isSeeThrough(Block block) {
        for (Block other : whitelist) {
            if (block.is(other))
                return false;
        }
        return true;
    }

    public static boolean modifyDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing, boolean returns) {
        if (returns) {
            if (isSeeThrough(state.getBlock()))
                return false;
        }
        else {
            if (!isSeeThrough(state.getBlock())) {
                BlockPos adjacentPos = pos.offset(facing);
                BlockState adjacentState = view.getBlockState(adjacentPos);
                return adjacentState.getCullingFace(view, adjacentPos, facing.getOpposite()) != VoxelShapes.fullCube()
                        || adjacentState.getBlock() != state.getBlock();
            }
        }
        return returns;
    }
}
