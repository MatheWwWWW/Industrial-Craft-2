/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.item.tfbp;

import ic2.core.block.machine.tileentity.TileEntityTerra;
import ic2.core.item.tfbp.Cultivation;
import ic2.core.item.tfbp.TerraformerBase;
import ic2.core.ref.BlockName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

class Desertification
extends TerraformerBase {
    Desertification() {
    }

    @Override
    boolean terraform(World world, BlockPos pos) {
        if ((pos = TileEntityTerra.getFirstBlockFrom(world, pos, 10)) == null) {
            return false;
        }
        IBlockState sand = Blocks.field_150354_m.func_176223_P();
        if (TileEntityTerra.switchGround(world, pos, Blocks.field_150346_d, sand, false) || TileEntityTerra.switchGround(world, pos, (Block)Blocks.field_150349_c, sand, false) || TileEntityTerra.switchGround(world, pos, Blocks.field_150458_ak, sand, false)) {
            TileEntityTerra.switchGround(world, pos, Blocks.field_150346_d, sand, false);
            return true;
        }
        Block block = world.func_180495_p(pos).func_177230_c();
        if (block == Blocks.field_150355_j || block == Blocks.field_150358_i || block == Blocks.field_150431_aC || block == Blocks.field_150362_t || block == Blocks.field_150361_u || block == BlockName.leaves.getInstance() || Desertification.isPlant(block)) {
            world.func_175698_g(pos);
            if (Desertification.isPlant(world.func_180495_p(pos.func_177984_a()).func_177230_c())) {
                world.func_175698_g(pos.func_177984_a());
            }
            return true;
        }
        if (block == Blocks.field_150432_aD || block == Blocks.field_150433_aE) {
            world.func_175656_a(pos, Blocks.field_150358_i.func_176223_P());
            return true;
        }
        if ((block == Blocks.field_150344_f || block == Blocks.field_150364_r || block == BlockName.rubber_wood.getInstance()) && world.field_73012_v.nextInt(15) == 0) {
            world.func_175656_a(pos, Blocks.field_150480_ab.func_176223_P());
            return true;
        }
        return false;
    }

    private static boolean isPlant(Block block) {
        for (IBlockState state : Cultivation.plants) {
            if (state.func_177230_c() != block) continue;
            return true;
        }
        return false;
    }
}

