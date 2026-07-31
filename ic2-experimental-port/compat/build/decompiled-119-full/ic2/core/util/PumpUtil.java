/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.util;

import ic2.core.fluid.FluidHandler;
import java.util.HashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class PumpUtil {
    private static int moveUp(Level level, BlockPos.MutableBlockPos mutableBlockPos) {
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_(), mutableBlockPos.m_123342_() + 1, mutableBlockPos.m_123343_());
        int n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n >= 0) {
            return n;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() + 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_());
        n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n >= 0) {
            return n;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() - 2, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_());
        n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n >= 0) {
            return n;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() + 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() + 1);
        n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n >= 0) {
            return n;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_(), mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() - 2);
        n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n >= 0) {
            return n;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_(), mutableBlockPos.m_123342_() - 1, mutableBlockPos.m_123343_() + 1);
        return -1;
    }

    private static int moveSideways(Level level, BlockPos.MutableBlockPos mutableBlockPos, int n) {
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() - 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_());
        int n2 = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n2 >= 0 && n2 < n) {
            return n2;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() + 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() + 1);
        n2 = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n2 >= 0 && n2 < n) {
            return n2;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_(), mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() - 2);
        n2 = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n2 >= 0 && n2 < n) {
            return n2;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() + 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() + 1);
        n2 = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        if (n2 >= 0 && n2 < n) {
            return n2;
        }
        mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() - 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_());
        return -1;
    }

    public static BlockPos searchFluidSource(Level level, BlockPos blockPos) {
        int n;
        int n2;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        mutableBlockPos.m_122178_(blockPos.m_123341_(), blockPos.m_123342_(), blockPos.m_123343_());
        int n3 = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos);
        for (int i = 0; i < 64 && ((n2 = PumpUtil.moveUp(level, mutableBlockPos)) >= 0 || (n2 = PumpUtil.moveSideways(level, mutableBlockPos, n3)) >= 0); ++i) {
            n3 = n2;
        }
        HashSet<BlockPos> hashSet = new HashSet<BlockPos>(64);
        for (n2 = 0; n2 < 64; ++n2) {
            hashSet.add(new BlockPos((Vec3i)mutableBlockPos));
            mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() - 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_());
            if (!hashSet.contains(mutableBlockPos) && (n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos)) >= 0) {
                if (n != 0) continue;
                return mutableBlockPos;
            }
            mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() + 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() + 1);
            if (!hashSet.contains(mutableBlockPos) && (n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos)) >= 0) {
                if (n != 0) continue;
                return mutableBlockPos;
            }
            mutableBlockPos.m_122178_(mutableBlockPos.m_123341_(), mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() - 2);
            if (!hashSet.contains(mutableBlockPos) && (n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos)) >= 0) {
                if (n != 0) continue;
                return mutableBlockPos;
            }
            mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() + 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() + 1);
            if (!hashSet.contains(mutableBlockPos) && (n = PumpUtil.getFlowDecay(level, (BlockPos)mutableBlockPos)) >= 0) {
                if (n != 0) continue;
                return mutableBlockPos;
            }
            mutableBlockPos.m_122178_(mutableBlockPos.m_123341_() - 1, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_());
            break;
        }
        BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
        for (n = -2; n <= 2; ++n) {
            for (int i = -2; i <= 2; ++i) {
                mutableBlockPos2.m_122178_(mutableBlockPos.m_123341_() + n, mutableBlockPos.m_123342_(), mutableBlockPos.m_123343_() + i);
                BlockState blockState = level.m_8055_((BlockPos)mutableBlockPos2);
                n3 = PumpUtil.getFlowDecay(blockState, level, (BlockPos)mutableBlockPos2);
                if (n3 < 0) continue;
                if (n3 == 0) {
                    return mutableBlockPos2;
                }
                if (n3 < 7 && blockState.m_60734_() instanceof LiquidBlock) continue;
                level.m_7471_((BlockPos)mutableBlockPos2, false);
            }
        }
        return null;
    }

    protected static int getFlowDecay(Level level, BlockPos blockPos) {
        BlockState blockState = level.m_8055_(blockPos);
        return PumpUtil.getFlowDecay(blockState, level, blockPos);
    }

    protected static int getFlowDecay(BlockState blockState, Level level, BlockPos blockPos) {
        Block block = blockState.m_60734_();
        int n = FluidHandler.getWorldFluidLevel(blockState, level, blockPos);
        if (n >= 0) {
            return n;
        }
        if (block instanceof LiquidBlock) {
            return (Integer)blockState.m_61143_((Property)LiquidBlock.f_54688_);
        }
        return -1;
    }

    protected static boolean isExistInArray(int n, int n2, int n3, int[][] nArray, int n4) {
        for (int i = 0; i <= n4; ++i) {
            if (nArray[i][0] != n || nArray[i][1] != n2 || nArray[i][2] != n3) continue;
            return true;
        }
        return false;
    }
}

