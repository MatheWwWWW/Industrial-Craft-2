/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ShovelItem
extends DiggerItem {
    protected static final Map<Block, BlockState> f_43110_ = Maps.newHashMap((Map)new ImmutableMap.Builder().put((Object)Blocks.f_50440_, (Object)Blocks.f_152481_.m_49966_()).put((Object)Blocks.f_50493_, (Object)Blocks.f_152481_.m_49966_()).put((Object)Blocks.f_50599_, (Object)Blocks.f_152481_.m_49966_()).put((Object)Blocks.f_50546_, (Object)Blocks.f_152481_.m_49966_()).put((Object)Blocks.f_50195_, (Object)Blocks.f_152481_.m_49966_()).put((Object)Blocks.f_152549_, (Object)Blocks.f_152481_.m_49966_()).build());

    public ShovelItem(Tier p_43114_, float p_43115_, float p_43116_, Item.Properties p_43117_) {
        super(p_43115_, p_43116_, p_43114_, BlockTags.f_144283_, p_43117_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_43119_) {
        Level $$1 = p_43119_.m_43725_();
        BlockPos $$2 = p_43119_.m_8083_();
        BlockState $$3 = $$1.m_8055_($$2);
        if (p_43119_.m_43719_() != Direction.DOWN) {
            Player $$4 = p_43119_.m_43723_();
            BlockState $$5 = f_43110_.get($$3.m_60734_());
            BlockState $$6 = null;
            if ($$5 != null && $$1.m_8055_($$2.m_7494_()).m_60795_()) {
                $$1.m_5594_($$4, $$2, SoundEvents.f_12406_, SoundSource.BLOCKS, 1.0f, 1.0f);
                $$6 = $$5;
            } else if ($$3.m_60734_() instanceof CampfireBlock && $$3.m_61143_(CampfireBlock.f_51227_).booleanValue()) {
                if (!$$1.m_5776_()) {
                    $$1.m_5898_(null, 1009, $$2, 0);
                }
                CampfireBlock.m_152749_(p_43119_.m_43723_(), $$1, $$2, $$3);
                $$6 = (BlockState)$$3.m_61124_(CampfireBlock.f_51227_, false);
            }
            if ($$6 != null) {
                if (!$$1.f_46443_) {
                    $$1.m_7731_($$2, $$6, 11);
                    $$1.m_220407_(GameEvent.f_157792_, $$2, GameEvent.Context.m_223719_($$4, $$6));
                    if ($$4 != null) {
                        p_43119_.m_43722_().m_41622_(1, $$4, p_43122_ -> p_43122_.m_21190_(p_43119_.m_43724_()));
                    }
                }
                return InteractionResult.m_19078_($$1.f_46443_);
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}

