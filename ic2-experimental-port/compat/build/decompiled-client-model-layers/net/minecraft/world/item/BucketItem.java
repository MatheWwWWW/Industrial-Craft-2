/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BucketItem
extends Item
implements DispensibleContainerItem {
    private final Fluid f_40687_;

    public BucketItem(Fluid p_40689_, Item.Properties p_40690_) {
        super(p_40690_);
        this.f_40687_ = p_40689_;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_40703_, Player p_40704_, InteractionHand p_40705_) {
        ItemStack $$3 = p_40704_.m_21120_(p_40705_);
        BlockHitResult $$4 = BucketItem.m_41435_(p_40703_, p_40704_, this.f_40687_ == Fluids.f_76191_ ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        if ($$4.m_6662_() == HitResult.Type.MISS) {
            return InteractionResultHolder.m_19098_($$3);
        }
        if ($$4.m_6662_() == HitResult.Type.BLOCK) {
            BlockPos $$13;
            BlockPos $$5 = $$4.m_82425_();
            Direction $$6 = $$4.m_82434_();
            BlockPos $$7 = $$5.m_121945_($$6);
            if (!p_40703_.m_7966_(p_40704_, $$5) || !p_40704_.m_36204_($$7, $$6, $$3)) {
                return InteractionResultHolder.m_19100_($$3);
            }
            if (this.f_40687_ == Fluids.f_76191_) {
                BucketPickup $$9;
                ItemStack $$10;
                BlockState $$8 = p_40703_.m_8055_($$5);
                if ($$8.m_60734_() instanceof BucketPickup && !($$10 = ($$9 = (BucketPickup)((Object)$$8.m_60734_())).m_142598_(p_40703_, $$5, $$8)).m_41619_()) {
                    p_40704_.m_36246_(Stats.f_12982_.m_12902_(this));
                    $$9.m_142298_().ifPresent(p_150709_ -> p_40704_.m_5496_((SoundEvent)p_150709_, 1.0f, 1.0f));
                    p_40703_.m_142346_(p_40704_, GameEvent.f_157816_, $$5);
                    ItemStack $$11 = ItemUtils.m_41813_($$3, p_40704_, $$10);
                    if (!p_40703_.f_46443_) {
                        CriteriaTriggers.f_10576_.m_38772_((ServerPlayer)p_40704_, $$10);
                    }
                    return InteractionResultHolder.m_19092_($$11, p_40703_.m_5776_());
                }
                return InteractionResultHolder.m_19100_($$3);
            }
            BlockState $$12 = p_40703_.m_8055_($$5);
            BlockPos blockPos = $$13 = $$12.m_60734_() instanceof LiquidBlockContainer && this.f_40687_ == Fluids.f_76193_ ? $$5 : $$7;
            if (this.m_142073_(p_40704_, p_40703_, $$13, $$4)) {
                this.m_142131_(p_40704_, p_40703_, $$3, $$13);
                if (p_40704_ instanceof ServerPlayer) {
                    CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)p_40704_, $$13, $$3);
                }
                p_40704_.m_36246_(Stats.f_12982_.m_12902_(this));
                return InteractionResultHolder.m_19092_(BucketItem.m_40699_($$3, p_40704_), p_40703_.m_5776_());
            }
            return InteractionResultHolder.m_19100_($$3);
        }
        return InteractionResultHolder.m_19098_($$3);
    }

    public static ItemStack m_40699_(ItemStack p_40700_, Player p_40701_) {
        if (!p_40701_.m_150110_().f_35937_) {
            return new ItemStack(Items.f_42446_);
        }
        return p_40700_;
    }

    @Override
    public void m_142131_(@Nullable Player p_150711_, Level p_150712_, ItemStack p_150713_, BlockPos p_150714_) {
    }

    @Override
    public boolean m_142073_(@Nullable Player p_150716_, Level p_150717_, BlockPos p_150718_, @Nullable BlockHitResult p_150719_) {
        boolean $$8;
        if (!(this.f_40687_ instanceof FlowingFluid)) {
            return false;
        }
        BlockState $$4 = p_150717_.m_8055_(p_150718_);
        Block $$5 = $$4.m_60734_();
        Material $$6 = $$4.m_60767_();
        boolean $$7 = $$4.m_60722_(this.f_40687_);
        boolean bl = $$8 = $$4.m_60795_() || $$7 || $$5 instanceof LiquidBlockContainer && ((LiquidBlockContainer)((Object)$$5)).m_6044_(p_150717_, p_150718_, $$4, this.f_40687_);
        if (!$$8) {
            return p_150719_ != null && this.m_142073_(p_150716_, p_150717_, p_150719_.m_82425_().m_121945_(p_150719_.m_82434_()), null);
        }
        if (p_150717_.m_6042_().f_63857_() && this.f_40687_.m_205067_(FluidTags.f_13131_)) {
            int $$9 = p_150718_.m_123341_();
            int $$10 = p_150718_.m_123342_();
            int $$11 = p_150718_.m_123343_();
            p_150717_.m_5594_(p_150716_, p_150718_, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5f, 2.6f + (p_150717_.f_46441_.m_188501_() - p_150717_.f_46441_.m_188501_()) * 0.8f);
            for (int $$12 = 0; $$12 < 8; ++$$12) {
                p_150717_.m_7106_(ParticleTypes.f_123755_, (double)$$9 + Math.random(), (double)$$10 + Math.random(), (double)$$11 + Math.random(), 0.0, 0.0, 0.0);
            }
            return true;
        }
        if ($$5 instanceof LiquidBlockContainer && this.f_40687_ == Fluids.f_76193_) {
            ((LiquidBlockContainer)((Object)$$5)).m_7361_(p_150717_, p_150718_, $$4, ((FlowingFluid)this.f_40687_).m_76068_(false));
            this.m_7718_(p_150716_, p_150717_, p_150718_);
            return true;
        }
        if (!p_150717_.f_46443_ && $$7 && !$$6.m_76332_()) {
            p_150717_.m_46961_(p_150718_, true);
        }
        if (p_150717_.m_7731_(p_150718_, this.f_40687_.m_76145_().m_76188_(), 11) || $$4.m_60819_().m_76170_()) {
            this.m_7718_(p_150716_, p_150717_, p_150718_);
            return true;
        }
        return false;
    }

    protected void m_7718_(@Nullable Player p_40696_, LevelAccessor p_40697_, BlockPos p_40698_) {
        SoundEvent $$3 = this.f_40687_.m_205067_(FluidTags.f_13132_) ? SoundEvents.f_11780_ : SoundEvents.f_11778_;
        p_40697_.m_5594_(p_40696_, p_40698_, $$3, SoundSource.BLOCKS, 1.0f, 1.0f);
        p_40697_.m_142346_(p_40696_, GameEvent.f_157769_, p_40698_);
    }
}

