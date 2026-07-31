/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.world.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class HoeItem
extends DiggerItem {
    protected static final Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> f_41332_ = Maps.newHashMap((Map)ImmutableMap.of((Object)Blocks.f_50440_, (Object)Pair.of(HoeItem::m_150856_, HoeItem.m_150858_(Blocks.f_50093_.m_49966_())), (Object)Blocks.f_152481_, (Object)Pair.of(HoeItem::m_150856_, HoeItem.m_150858_(Blocks.f_50093_.m_49966_())), (Object)Blocks.f_50493_, (Object)Pair.of(HoeItem::m_150856_, HoeItem.m_150858_(Blocks.f_50093_.m_49966_())), (Object)Blocks.f_50546_, (Object)Pair.of(HoeItem::m_150856_, HoeItem.m_150858_(Blocks.f_50493_.m_49966_())), (Object)Blocks.f_152549_, (Object)Pair.of(p_238242_ -> true, HoeItem.m_150849_(Blocks.f_50493_.m_49966_(), Items.f_151017_))));

    protected HoeItem(Tier p_41336_, int p_41337_, float p_41338_, Item.Properties p_41339_) {
        super(p_41337_, p_41338_, p_41336_, BlockTags.f_144281_, p_41339_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_41341_) {
        BlockPos $$2;
        Level $$1 = p_41341_.m_43725_();
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> $$3 = f_41332_.get($$1.m_8055_($$2 = p_41341_.m_8083_()).m_60734_());
        if ($$3 == null) {
            return InteractionResult.PASS;
        }
        Predicate $$4 = (Predicate)$$3.getFirst();
        Consumer $$5 = (Consumer)$$3.getSecond();
        if ($$4.test(p_41341_)) {
            Player $$6 = p_41341_.m_43723_();
            $$1.m_5594_($$6, $$2, SoundEvents.f_11955_, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!$$1.f_46443_) {
                $$5.accept(p_41341_);
                if ($$6 != null) {
                    p_41341_.m_43722_().m_41622_(1, $$6, p_150845_ -> p_150845_.m_21190_(p_41341_.m_43724_()));
                }
            }
            return InteractionResult.m_19078_($$1.f_46443_);
        }
        return InteractionResult.PASS;
    }

    public static Consumer<UseOnContext> m_150858_(BlockState p_150859_) {
        return p_238241_ -> {
            p_238241_.m_43725_().m_7731_(p_238241_.m_8083_(), p_150859_, 11);
            p_238241_.m_43725_().m_220407_(GameEvent.f_157792_, p_238241_.m_8083_(), GameEvent.Context.m_223719_(p_238241_.m_43723_(), p_150859_));
        };
    }

    public static Consumer<UseOnContext> m_150849_(BlockState p_150850_, ItemLike p_150851_) {
        return p_238246_ -> {
            p_238246_.m_43725_().m_7731_(p_238246_.m_8083_(), p_150850_, 11);
            p_238246_.m_43725_().m_220407_(GameEvent.f_157792_, p_238246_.m_8083_(), GameEvent.Context.m_223719_(p_238246_.m_43723_(), p_150850_));
            Block.m_152435_(p_238246_.m_43725_(), p_238246_.m_8083_(), p_238246_.m_43719_(), new ItemStack(p_150851_));
        };
    }

    public static boolean m_150856_(UseOnContext p_150857_) {
        return p_150857_.m_43719_() != Direction.DOWN && p_150857_.m_43725_().m_8055_(p_150857_.m_8083_().m_7494_()).m_60795_();
    }
}

