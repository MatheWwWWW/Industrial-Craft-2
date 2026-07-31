/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class PumpkinBlock
extends StemGrownBlock {
    protected PumpkinBlock(BlockBehaviour.Properties p_55284_) {
        super(p_55284_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_55289_, Level p_55290_, BlockPos p_55291_, Player p_55292_, InteractionHand p_55293_, BlockHitResult p_55294_) {
        ItemStack $$6 = p_55292_.m_21120_(p_55293_);
        if ($$6.m_150930_(Items.f_42574_)) {
            if (!p_55290_.f_46443_) {
                Direction $$7 = p_55294_.m_82434_();
                Direction $$8 = $$7.m_122434_() == Direction.Axis.Y ? p_55292_.m_6350_().m_122424_() : $$7;
                p_55290_.m_5594_(null, p_55291_, SoundEvents.f_12296_, SoundSource.BLOCKS, 1.0f, 1.0f);
                p_55290_.m_7731_(p_55291_, (BlockState)Blocks.f_50143_.m_49966_().m_61124_(CarvedPumpkinBlock.f_51367_, $$8), 11);
                ItemEntity $$9 = new ItemEntity(p_55290_, (double)p_55291_.m_123341_() + 0.5 + (double)$$8.m_122429_() * 0.65, (double)p_55291_.m_123342_() + 0.1, (double)p_55291_.m_123343_() + 0.5 + (double)$$8.m_122431_() * 0.65, new ItemStack(Items.f_42577_, 4));
                $$9.m_20334_(0.05 * (double)$$8.m_122429_() + p_55290_.f_46441_.m_188500_() * 0.02, 0.05, 0.05 * (double)$$8.m_122431_() + p_55290_.f_46441_.m_188500_() * 0.02);
                p_55290_.m_7967_($$9);
                $$6.m_41622_(1, p_55292_, p_55287_ -> p_55287_.m_21190_(p_55293_));
                p_55290_.m_142346_(p_55292_, GameEvent.f_157781_, p_55291_);
                p_55292_.m_36246_(Stats.f_12982_.m_12902_(Items.f_42574_));
            }
            return InteractionResult.m_19078_(p_55290_.f_46443_);
        }
        return super.m_6227_(p_55289_, p_55290_, p_55291_, p_55292_, p_55293_, p_55294_);
    }

    @Override
    public StemBlock m_7161_() {
        return (StemBlock)Blocks.f_50189_;
    }

    @Override
    public AttachedStemBlock m_7810_() {
        return (AttachedStemBlock)Blocks.f_50187_;
    }
}

