/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.common.IPlantable
 */
package ic2.core.item.misc.tfbp.bp;

import ic2.api.items.ITerraformerBP;
import ic2.api.tiles.ITerraformer;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.IPlantable;

public class DesertificationBluePrint
implements ITerraformerBP {
    public static final ITerraformerBP INSTANCE = new DesertificationBluePrint();

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
        return 2500;
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
        Holder block2;
        BlockPos target = terraformer.getFirstBlockFrom(world, position.m_6630_(10));
        if (target.m_123342_() == -1) {
            return false;
        }
        for (Holder block2 : Registry.f_122824_.m_206058_(IC2Tags.TERRAFORMER_GROUND)) {
            if (!terraformer.switchGround(world, target, ((Block)block2.m_203334_()).m_49966_(), Blocks.f_49992_.m_49966_(), false, false)) continue;
            return true;
        }
        BlockState state = world.m_8055_(target);
        block2 = state.m_60734_();
        if (state.m_60819_().m_76152_() == Fluids.f_76193_) {
            world.m_7731_(target, Blocks.f_50016_.m_49966_(), 2);
            return true;
        }
        if (state.m_60819_().m_76152_() == Fluids.f_76192_ || block2 == Blocks.f_50127_ || state.m_204336_(BlockTags.f_13035_) || block2 instanceof IPlantable) {
            world.m_7731_(target, Blocks.f_50016_.m_49966_(), 3);
            return true;
        }
        if (block2 == Blocks.f_50126_) {
            world.m_46597_(target, Fluids.f_76193_.m_76145_().m_76188_());
            return true;
        }
        if (state.m_204336_(BlockTags.f_13090_) || state.m_204336_(BlockTags.f_13105_)) {
            world.m_46597_(target, Blocks.f_50083_.m_49966_());
            return true;
        }
        return false;
    }
}

