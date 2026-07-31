/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.ImmutableBiMap
 */
package net.minecraft.world.item;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class HoneycombItem
extends Item {
    public static final Supplier<BiMap<Block, Block>> f_150863_ = Suppliers.memoize(() -> ImmutableBiMap.builder().put((Object)Blocks.f_152504_, (Object)Blocks.f_152571_).put((Object)Blocks.f_152503_, (Object)Blocks.f_152573_).put((Object)Blocks.f_152502_, (Object)Blocks.f_152572_).put((Object)Blocks.f_152501_, (Object)Blocks.f_152574_).put((Object)Blocks.f_152510_, (Object)Blocks.f_152578_).put((Object)Blocks.f_152509_, (Object)Blocks.f_152577_).put((Object)Blocks.f_152508_, (Object)Blocks.f_152576_).put((Object)Blocks.f_152507_, (Object)Blocks.f_152575_).put((Object)Blocks.f_152570_, (Object)Blocks.f_152586_).put((Object)Blocks.f_152569_, (Object)Blocks.f_152585_).put((Object)Blocks.f_152568_, (Object)Blocks.f_152584_).put((Object)Blocks.f_152567_, (Object)Blocks.f_152583_).put((Object)Blocks.f_152566_, (Object)Blocks.f_152582_).put((Object)Blocks.f_152565_, (Object)Blocks.f_152581_).put((Object)Blocks.f_152564_, (Object)Blocks.f_152580_).put((Object)Blocks.f_152563_, (Object)Blocks.f_152579_).build());
    public static final Supplier<BiMap<Block, Block>> f_150864_ = Suppliers.memoize(() -> f_150863_.get().inverse());

    public HoneycombItem(Item.Properties p_150867_) {
        super(p_150867_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_150869_) {
        Level $$1 = p_150869_.m_43725_();
        BlockPos $$2 = p_150869_.m_8083_();
        BlockState $$3 = $$1.m_8055_($$2);
        return HoneycombItem.m_150878_($$3).map(p_238251_ -> {
            Player $$4 = p_150869_.m_43723_();
            ItemStack $$5 = p_150869_.m_43722_();
            if ($$4 instanceof ServerPlayer) {
                CriteriaTriggers.f_10562_.m_220040_((ServerPlayer)$$4, $$2, $$5);
            }
            $$5.m_41774_(1);
            $$1.m_7731_($$2, (BlockState)p_238251_, 11);
            $$1.m_220407_(GameEvent.f_157792_, $$2, GameEvent.Context.m_223719_($$4, p_238251_));
            $$1.m_5898_($$4, 3003, $$2, 0);
            return InteractionResult.m_19078_(p_238250_.f_46443_);
        }).orElse(InteractionResult.PASS);
    }

    public static Optional<BlockState> m_150878_(BlockState p_150879_) {
        return Optional.ofNullable((Block)f_150863_.get().get((Object)p_150879_.m_60734_())).map(p_150877_ -> p_150877_.m_152465_(p_150879_));
    }
}

