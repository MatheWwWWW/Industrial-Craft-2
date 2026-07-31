/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class FireChargeItem
extends Item {
    public FireChargeItem(Item.Properties p_41202_) {
        super(p_41202_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_41204_) {
        Level $$1 = p_41204_.m_43725_();
        BlockPos $$2 = p_41204_.m_8083_();
        BlockState $$3 = $$1.m_8055_($$2);
        boolean $$4 = false;
        if (CampfireBlock.m_51321_($$3) || CandleBlock.m_152845_($$3) || CandleCakeBlock.m_152910_($$3)) {
            this.m_41205_($$1, $$2);
            $$1.m_46597_($$2, (BlockState)$$3.m_61124_(BlockStateProperties.f_61443_, true));
            $$1.m_142346_(p_41204_.m_43723_(), GameEvent.f_157792_, $$2);
            $$4 = true;
        } else if (BaseFireBlock.m_49255_($$1, $$2 = $$2.m_121945_(p_41204_.m_43719_()), p_41204_.m_8125_())) {
            this.m_41205_($$1, $$2);
            $$1.m_46597_($$2, BaseFireBlock.m_49245_($$1, $$2));
            $$1.m_142346_(p_41204_.m_43723_(), GameEvent.f_157797_, $$2);
            $$4 = true;
        }
        if ($$4) {
            p_41204_.m_43722_().m_41774_(1);
            return InteractionResult.m_19078_($$1.f_46443_);
        }
        return InteractionResult.FAIL;
    }

    private void m_41205_(Level p_41206_, BlockPos p_41207_) {
        RandomSource $$2 = p_41206_.m_213780_();
        p_41206_.m_5594_(null, p_41207_, SoundEvents.f_11874_, SoundSource.BLOCKS, 1.0f, ($$2.m_188501_() - $$2.m_188501_()) * 0.2f + 1.0f);
    }
}

