/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap$Builder
 */
package net.minecraft.world.item;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class AxeItem
extends DiggerItem {
    protected static final Map<Block, Block> f_150683_ = new ImmutableMap.Builder().put((Object)Blocks.f_50011_, (Object)Blocks.f_50044_).put((Object)Blocks.f_49999_, (Object)Blocks.f_50010_).put((Object)Blocks.f_50043_, (Object)Blocks.f_50049_).put((Object)Blocks.f_50004_, (Object)Blocks.f_50009_).put((Object)Blocks.f_50015_, (Object)Blocks.f_50048_).put((Object)Blocks.f_50003_, (Object)Blocks.f_50008_).put((Object)Blocks.f_50013_, (Object)Blocks.f_50046_).put((Object)Blocks.f_50001_, (Object)Blocks.f_50006_).put((Object)Blocks.f_50014_, (Object)Blocks.f_50047_).put((Object)Blocks.f_50002_, (Object)Blocks.f_50007_).put((Object)Blocks.f_50012_, (Object)Blocks.f_50045_).put((Object)Blocks.f_50000_, (Object)Blocks.f_50005_).put((Object)Blocks.f_50686_, (Object)Blocks.f_50687_).put((Object)Blocks.f_50688_, (Object)Blocks.f_50689_).put((Object)Blocks.f_50695_, (Object)Blocks.f_50696_).put((Object)Blocks.f_50697_, (Object)Blocks.f_50698_).put((Object)Blocks.f_220836_, (Object)Blocks.f_220837_).put((Object)Blocks.f_220832_, (Object)Blocks.f_220835_).build();

    protected AxeItem(Tier p_40521_, float p_40522_, float p_40523_, Item.Properties p_40524_) {
        super(p_40522_, p_40523_, p_40521_, BlockTags.f_144280_, p_40524_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_40529_) {
        Level $$1 = p_40529_.m_43725_();
        BlockPos $$2 = p_40529_.m_8083_();
        Player $$3 = p_40529_.m_43723_();
        BlockState $$4 = $$1.m_8055_($$2);
        Optional<BlockState> $$5 = this.m_150690_($$4);
        Optional<BlockState> $$6 = WeatheringCopper.m_154899_($$4);
        Optional<BlockState> $$7 = Optional.ofNullable((Block)HoneycombItem.f_150864_.get().get((Object)$$4.m_60734_())).map(p_150694_ -> p_150694_.m_152465_($$4));
        ItemStack $$8 = p_40529_.m_43722_();
        Optional<Object> $$9 = Optional.empty();
        if ($$5.isPresent()) {
            $$1.m_5594_($$3, $$2, SoundEvents.f_11688_, SoundSource.BLOCKS, 1.0f, 1.0f);
            $$9 = $$5;
        } else if ($$6.isPresent()) {
            $$1.m_5594_($$3, $$2, SoundEvents.f_144059_, SoundSource.BLOCKS, 1.0f, 1.0f);
            $$1.m_5898_($$3, 3005, $$2, 0);
            $$9 = $$6;
        } else if ($$7.isPresent()) {
            $$1.m_5594_($$3, $$2, SoundEvents.f_144060_, SoundSource.BLOCKS, 1.0f, 1.0f);
            $$1.m_5898_($$3, 3004, $$2, 0);
            $$9 = $$7;
        }
        if ($$9.isPresent()) {
            if ($$3 instanceof ServerPlayer) {
                CriteriaTriggers.f_10562_.m_220040_((ServerPlayer)$$3, $$2, $$8);
            }
            $$1.m_7731_($$2, (BlockState)$$9.get(), 11);
            $$1.m_220407_(GameEvent.f_157792_, $$2, GameEvent.Context.m_223719_($$3, (BlockState)$$9.get()));
            if ($$3 != null) {
                $$8.m_41622_(1, $$3, p_150686_ -> p_150686_.m_21190_(p_40529_.m_43724_()));
            }
            return InteractionResult.m_19078_($$1.f_46443_);
        }
        return InteractionResult.PASS;
    }

    private Optional<BlockState> m_150690_(BlockState p_150691_) {
        return Optional.ofNullable(f_150683_.get(p_150691_.m_60734_())).map(p_150689_ -> (BlockState)p_150689_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, p_150691_.m_61143_(RotatedPillarBlock.f_55923_)));
    }
}

