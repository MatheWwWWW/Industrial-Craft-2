/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockSnow
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.item.tfbp.TerraformerBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

class Chilling
extends TerraformerBase {
    Chilling() {
    }

    @Override
    boolean terraform(World world, BlockPos pos) {
        if ((pos = TileEntityTerra.getFirstBlockFrom(world, pos, 10)) == null) {
            return false;
        }
        IBlockState state = world.func_180495_p(pos);
        Block block = state.func_177230_c();
        if (block == Blocks.field_150355_j || block == Blocks.field_150358_i) {
            world.func_175656_a(pos, Blocks.field_150432_aD.func_176223_P());
            return true;
        }
        if (block == Blocks.field_150432_aD) {
            BlockPos below = pos.func_177977_b();
            Block blockBelow = world.func_180495_p(below).func_177230_c();
            if (blockBelow == Blocks.field_150355_j || blockBelow == Blocks.field_150358_i) {
                world.func_175656_a(below, Blocks.field_150432_aD.func_176223_P());
                return true;
            }
        } else if (block == Blocks.field_150431_aC) {
            if (Chilling.isSurroundedBySnow(world, pos)) {
                world.func_175656_a(pos, Blocks.field_150433_aE.func_176223_P());
                return true;
            }
            int size = (Integer)state.func_177229_b((IProperty)BlockSnow.field_176315_a);
            if (BlockSnow.field_176315_a.func_177700_c().contains(size + 1)) {
                world.func_175656_a(pos, state.func_177226_a((IProperty)BlockSnow.field_176315_a, (Comparable)Integer.valueOf(size + 1)));
                return true;
            }
        }
        if (Blocks.field_150431_aC.func_176196_c(world, pos = pos.func_177984_a()) || block == Blocks.field_150432_aD) {
            world.func_175656_a(pos, Blocks.field_150431_aC.func_176223_P());
            return true;
        }
        return false;
    }

    private static boolean isSurroundedBySnow(World world, BlockPos pos) {
        for (EnumFacing dir : EnumFacing.field_176754_o) {
            if (Chilling.isSnowHere(world, pos.func_177972_a(dir))) continue;
            return false;
        }
        return true;
    }

    private static boolean isSnowHere(World world, BlockPos pos) {
        int prevY = pos.func_177956_o();
        if ((pos = TileEntityTerra.getFirstBlockFrom(world, pos, 16)) == null || prevY > pos.func_177956_o()) {
            return false;
        }
        Block block = world.func_180495_p(pos).func_177230_c();
        if (block == Blocks.field_150433_aE || block == Blocks.field_150431_aC) {
            return true;
        }
        if (Blocks.field_150431_aC.func_176196_c(world, pos = pos.func_177984_a()) || block == Blocks.field_150432_aD) {
            world.func_175656_a(pos, Blocks.field_150431_aC.func_176223_P());
        }
        return false;
    }
}

