/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class FlintAndSteelItem
extends Item {
    public FlintAndSteelItem(Item.Properties p_41295_) {
        super(p_41295_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_41297_) {
        BlockPos $$3;
        Player $$1 = p_41297_.m_43723_();
        Level $$2 = p_41297_.m_43725_();
        BlockState $$4 = $$2.m_8055_($$3 = p_41297_.m_8083_());
        if (CampfireBlock.m_51321_($$4) || CandleBlock.m_152845_($$4) || CandleCakeBlock.m_152910_($$4)) {
            $$2.m_5594_($$1, $$3, SoundEvents.f_11942_, SoundSource.BLOCKS, 1.0f, $$2.m_213780_().m_188501_() * 0.4f + 0.8f);
            $$2.m_7731_($$3, (BlockState)$$4.m_61124_(BlockStateProperties.f_61443_, true), 11);
            $$2.m_142346_($$1, GameEvent.f_157792_, $$3);
            if ($$1 != null) {
                p_41297_.m_43722_().m_41622_(1, $$1, p_41303_ -> p_41303_.m_21190_(p_41297_.m_43724_()));
            }
            return InteractionResult.m_19078_($$2.m_5776_());
        }
        BlockPos $$5 = $$3.m_121945_(p_41297_.m_43719_());
        if (BaseFireBlock.m_49255_($$2, $$5, p_41297_.m_8125_())) {
            $$2.m_5594_($$1, $$5, SoundEvents.f_11942_, SoundSource.BLOCKS, 1.0f, $$2.m_213780_().m_188501_() * 0.4f + 0.8f);
            BlockState $$6 = BaseFireBlock.m_49245_($$2, $$5);
            $$2.m_7731_($$5, $$6, 11);
            $$2.m_142346_($$1, GameEvent.f_157797_, $$3);
            ItemStack $$7 = p_41297_.m_43722_();
            if ($$1 instanceof ServerPlayer) {
                CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)$$1, $$5, $$7);
                $$7.m_41622_(1, $$1, p_41300_ -> p_41300_.m_21190_(p_41297_.m_43724_()));
            }
            return InteractionResult.m_19078_($$2.m_5776_());
        }
        return InteractionResult.FAIL;
    }
}

