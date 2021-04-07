package me.cubert3d.palladium.module.modules.render;

import me.cubert3d.palladium.module.Module;
import me.cubert3d.palladium.module.ModuleDevStatus;
import me.cubert3d.palladium.module.ModuleType;
import me.cubert3d.palladium.util.RenderUtil;
import me.cubert3d.palladium.util.annotation.ClassDescription;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

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
            Blocks.ANCIENT_DEBRIS,
            Blocks.CHEST,
            Blocks.TRAPPED_CHEST,
            Blocks.LAVA
    };

    public XRayModule() {
        super("XRay", "Lets you see ores in the ground", ModuleType.TOGGLE, ModuleDevStatus.DEBUG_ONLY);
    }

    @Override
    protected void onEnable() {
        RenderUtil.reloadRenderer();
    }

    @Override
    protected void onDisable() {
        RenderUtil.reloadRenderer();
    }

    // Whether a block should be made invisible when X-Ray is enabled.
    public static boolean isSeeThrough(Block block) {
        for (Block ore : ores) {
            if (block.is(ore))
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
