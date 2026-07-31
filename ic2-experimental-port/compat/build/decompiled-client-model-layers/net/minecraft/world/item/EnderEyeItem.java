/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EnderEyeItem
extends Item {
    public EnderEyeItem(Item.Properties p_41180_) {
        super(p_41180_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_41182_) {
        BlockPos $$2;
        Level $$1 = p_41182_.m_43725_();
        BlockState $$3 = $$1.m_8055_($$2 = p_41182_.m_8083_());
        if (!$$3.m_60713_(Blocks.f_50258_) || $$3.m_61143_(EndPortalFrameBlock.f_53043_).booleanValue()) {
            return InteractionResult.PASS;
        }
        if ($$1.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        BlockState $$4 = (BlockState)$$3.m_61124_(EndPortalFrameBlock.f_53043_, true);
        Block.m_49897_($$3, $$4, $$1, $$2);
        $$1.m_7731_($$2, $$4, 2);
        $$1.m_46717_($$2, Blocks.f_50258_);
        p_41182_.m_43722_().m_41774_(1);
        $$1.m_46796_(1503, $$2, 0);
        BlockPattern.BlockPatternMatch $$5 = EndPortalFrameBlock.m_53077_().m_61184_($$1, $$2);
        if ($$5 != null) {
            BlockPos $$6 = $$5.m_61228_().m_7918_(-3, 0, -3);
            for (int $$7 = 0; $$7 < 3; ++$$7) {
                for (int $$8 = 0; $$8 < 3; ++$$8) {
                    $$1.m_7731_($$6.m_7918_($$7, 0, $$8), Blocks.f_50257_.m_49966_(), 2);
                }
            }
            $$1.m_6798_(1038, $$6.m_7918_(1, 0, 1), 0);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41184_, Player p_41185_, InteractionHand p_41186_) {
        ServerLevel $$5;
        BlockPos $$6;
        ItemStack $$3 = p_41185_.m_21120_(p_41186_);
        BlockHitResult $$4 = EnderEyeItem.m_41435_(p_41184_, p_41185_, ClipContext.Fluid.NONE);
        if (((HitResult)$$4).m_6662_() == HitResult.Type.BLOCK && p_41184_.m_8055_($$4.m_82425_()).m_60713_(Blocks.f_50258_)) {
            return InteractionResultHolder.m_19098_($$3);
        }
        p_41185_.m_6672_(p_41186_);
        if (p_41184_ instanceof ServerLevel && ($$6 = ($$5 = (ServerLevel)p_41184_).m_215011_(StructureTags.f_215882_, p_41185_.m_20183_(), 100, false)) != null) {
            EyeOfEnder $$7 = new EyeOfEnder(p_41184_, p_41185_.m_20185_(), p_41185_.m_20227_(0.5), p_41185_.m_20189_());
            $$7.m_36972_($$3);
            $$7.m_36967_($$6);
            p_41184_.m_214171_(GameEvent.f_157778_, $$7.m_20182_(), GameEvent.Context.m_223717_(p_41185_));
            p_41184_.m_7967_($$7);
            if (p_41185_ instanceof ServerPlayer) {
                CriteriaTriggers.f_10579_.m_73935_((ServerPlayer)p_41185_, $$6);
            }
            p_41184_.m_6263_(null, p_41185_.m_20185_(), p_41185_.m_20186_(), p_41185_.m_20189_(), SoundEvents.f_11898_, SoundSource.NEUTRAL, 0.5f, 0.4f / (p_41184_.m_213780_().m_188501_() * 0.4f + 0.8f));
            p_41184_.m_5898_(null, 1003, p_41185_.m_20183_(), 0);
            if (!p_41185_.m_150110_().f_35937_) {
                $$3.m_41774_(1);
            }
            p_41185_.m_36246_(Stats.f_12982_.m_12902_(this));
            p_41185_.m_21011_(p_41186_, true);
            return InteractionResultHolder.m_19090_($$3);
        }
        return InteractionResultHolder.m_19096_($$3);
    }
}

