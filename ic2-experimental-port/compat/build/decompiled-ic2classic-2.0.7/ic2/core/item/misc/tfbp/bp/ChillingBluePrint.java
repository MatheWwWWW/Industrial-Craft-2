/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.item.misc.tfbp.bp;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class ChillingBluePrint
implements ITerraformerBP {
    public static final ITerraformerBP INSTANCE = new ChillingBluePrint();

    @Override
    public boolean canInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isRandomized(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnergyUsage(ItemStack stack) {
        return 2000;
    }

    @Override
    public int getRadius(ItemStack stack) {
        return 50;
    }

    @Override
    public boolean terraform(ItemStack stack, Level world, BlockPos position, ITerraformer terraformer) {
        BlockState below;
        BlockPos target = terraformer.getFirstBlockFrom(world, position.m_6630_(10));
        if (target.m_123342_() < -1) {
            return false;
        }
        BlockState state = world.m_8055_(target);
        if (state.m_60819_().m_76152_() == Fluids.f_76193_ || state.m_60819_().m_76152_() == Fluids.f_76192_) {
            world.m_46597_(target, Blocks.f_50126_.m_49966_());
            return true;
        }
        if (state.m_60734_() == Blocks.f_50126_ && ((below = world.m_8055_(target.m_7495_())).m_60819_().m_76152_() == Fluids.f_76193_ || below.m_60819_().m_76152_() == Fluids.f_76192_)) {
            world.m_46597_(target.m_7495_(), Blocks.f_50126_.m_49966_());
            return true;
        }
        if (state.m_60734_() == Blocks.f_50125_ && this.isSurroundedBySnow(world, target, terraformer)) {
            world.m_46597_(target, Blocks.f_50127_.m_49966_());
            return true;
        }
        if (Blocks.f_50125_.m_7898_(world.m_8055_(target.m_7494_()), (LevelReader)world, target.m_7494_()) || state.m_60734_() == Blocks.f_50126_) {
            world.m_46597_(target.m_7494_(), Blocks.f_50125_.m_49966_());
            return true;
        }
        return false;
    }

    @Override
    public void onInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
    }

    public boolean isSurroundedBySnow(Level world, BlockPos pos, ITerraformer terraformer) {
        return this.isSnowHere(world, pos.m_122029_(), terraformer) && this.isSnowHere(world, pos.m_122024_(), terraformer) && this.isSnowHere(world, pos.m_122012_(), terraformer) && this.isSnowHere(world, pos.m_122019_(), terraformer);
    }

    public boolean isSnowHere(Level world, BlockPos pos, ITerraformer terraformer) {
        BlockPos target = terraformer.getFirstBlockFrom(world, pos.m_6630_(16));
        if (pos.m_123342_() > target.m_123342_()) {
            return false;
        }
        BlockState state = world.m_8055_(target);
        if (state.m_60734_() == Blocks.f_50125_ || state.m_60734_() == Blocks.f_50127_) {
            return true;
        }
        if (Blocks.f_50125_.m_7898_(world.m_8055_(target.m_7494_()), (LevelReader)world, target.m_7494_()) || state.m_60734_() == Blocks.f_50126_) {
            world.m_46597_(target.m_7494_(), Blocks.f_50125_.m_49966_());
            return true;
        }
        return false;
    }
}

