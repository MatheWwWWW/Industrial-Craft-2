/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.IFluidBlock
 */
package ic2.core.util;

import ic2.core.util.Util;
import java.util.HashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class PumpUtil {
    private static int moveUp(World world, BlockPos.MutableBlockPos pos) {
        pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o() + 1, pos.func_177952_p());
        int newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p());
        newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n() - 2, pos.func_177956_o(), pos.func_177952_p());
        newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
        newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() - 2);
        newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o() - 1, pos.func_177952_p() + 1);
        return -1;
    }

    private static int moveSideways(World world, BlockPos.MutableBlockPos pos, int decay) {
        pos.func_181079_c(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p());
        int newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0 && newDecay < decay) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
        newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0 && newDecay < decay) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() - 2);
        newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0 && newDecay < decay) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
        newDecay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        if (newDecay >= 0 && newDecay < decay) {
            return newDecay;
        }
        pos.func_181079_c(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p());
        return -1;
    }

    public static BlockPos searchFluidSource(World world, BlockPos startPos) {
        int newDecay;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        pos.func_181079_c(startPos.func_177958_n(), startPos.func_177956_o(), startPos.func_177952_p());
        int decay = PumpUtil.getFlowDecay(world, (BlockPos)pos);
        for (int i = 0; i < 64 && ((newDecay = PumpUtil.moveUp(world, pos)) >= 0 || (newDecay = PumpUtil.moveSideways(world, pos, decay)) >= 0); ++i) {
            decay = newDecay;
        }
        HashSet<BlockPos> visited = new HashSet<BlockPos>(64);
        for (int i = 0; i < 64; ++i) {
            int newDecay2;
            visited.add(new BlockPos((Vec3i)pos));
            pos.func_181079_c(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p());
            if (!visited.contains(pos) && (newDecay2 = PumpUtil.getFlowDecay(world, (BlockPos)pos)) >= 0) {
                if (newDecay2 != 0) continue;
                return pos;
            }
            pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
            if (!visited.contains(pos) && (newDecay2 = PumpUtil.getFlowDecay(world, (BlockPos)pos)) >= 0) {
                if (newDecay2 != 0) continue;
                return pos;
            }
            pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() - 2);
            if (!visited.contains(pos) && (newDecay2 = PumpUtil.getFlowDecay(world, (BlockPos)pos)) >= 0) {
                if (newDecay2 != 0) continue;
                return pos;
            }
            pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
            if (!visited.contains(pos) && (newDecay2 = PumpUtil.getFlowDecay(world, (BlockPos)pos)) >= 0) {
                if (newDecay2 != 0) continue;
                return pos;
            }
            pos.func_181079_c(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p());
            break;
        }
        BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
        for (int ix = -2; ix <= 2; ++ix) {
            for (int iz = -2; iz <= 2; ++iz) {
                cPos.func_181079_c(pos.func_177958_n() + ix, pos.func_177956_o(), pos.func_177952_p() + iz);
                IBlockState state = world.func_180495_p((BlockPos)cPos);
                decay = PumpUtil.getFlowDecay(state, world, (BlockPos)cPos);
                if (decay < 0) continue;
                if (decay == 0) {
                    return cPos;
                }
                if (decay >= 1 && decay < 7 && state.func_177230_c() instanceof BlockLiquid) {
                    world.func_175656_a((BlockPos)cPos, state.func_177226_a((IProperty)BlockLiquid.field_176367_b, (Comparable)Integer.valueOf(decay + 1)));
                    continue;
                }
                world.func_175698_g((BlockPos)cPos);
            }
        }
        return null;
    }

    protected static int getFlowDecay(World world, BlockPos pos) {
        IBlockState state = world.func_180495_p(pos);
        return PumpUtil.getFlowDecay(state, world, pos);
    }

    protected static int getFlowDecay(IBlockState state, World world, BlockPos pos) {
        Block block = state.func_177230_c();
        if (block instanceof IFluidBlock) {
            IFluidBlock fb = (IFluidBlock)block;
            if (fb.canDrain(world, pos)) {
                return 0;
            }
            float level = Math.abs(fb.getFilledPercentage(world, pos));
            return 7 - Util.limit(Math.round(6.0f * level), 0, 6);
        }
        if (block instanceof BlockLiquid) {
            return (Integer)state.func_177229_b((IProperty)BlockLiquid.field_176367_b);
        }
        return -1;
    }

    protected static boolean isExistInArray(int x, int y, int z, int[][] xyz, int end_i) {
        for (int i = 0; i <= end_i; ++i) {
            if (xyz[i][0] != x || xyz[i][1] != y || xyz[i][2] != z) continue;
            return true;
        }
        return false;
    }
}

