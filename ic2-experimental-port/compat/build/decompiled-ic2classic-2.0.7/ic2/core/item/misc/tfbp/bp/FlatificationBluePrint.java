/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.misc.tfbp.bp;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FlatificationBluePrint
implements ITerraformerBP {
    public static final ITerraformerBP INSTANCE = new FlatificationBluePrint();

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
        return 4000;
    }

    @Override
    public int getRadius(ItemStack stack) {
        return 40;
    }

    @Override
    public void onInsert(ItemStack stack, Player player, Level world, BlockPos pos) {
    }

    @Override
    public boolean terraform(ItemStack stack, Level world, BlockPos position, ITerraformer terraformer) {
        BlockPos target = terraformer.getFirstBlockFrom(world, position.m_6630_(20));
        if (target.m_123342_() == -1) {
            return false;
        }
        BlockState state = world.m_8055_(target);
        if (state.m_60734_() == Blocks.f_50125_) {
            target = target.m_7495_();
            state = world.m_8055_(target);
        }
        if (target.m_123342_() == position.m_123342_()) {
            return false;
        }
        if (target.m_123342_() < position.m_123342_()) {
            world.m_46597_(target.m_7494_(), Blocks.f_50493_.m_49966_());
            return true;
        }
        if (state.m_204336_(IC2Tags.TERRAFORMER_FLAT)) {
            world.m_46597_(target, Blocks.f_50016_.m_49966_());
            return true;
        }
        return false;
    }
}

