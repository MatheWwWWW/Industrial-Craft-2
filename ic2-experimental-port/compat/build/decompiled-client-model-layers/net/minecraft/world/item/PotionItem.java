/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class PotionItem
extends Item {
    private static final int f_151180_ = 32;

    public PotionItem(Item.Properties p_42979_) {
        super(p_42979_);
    }

    @Override
    public ItemStack m_7968_() {
        return PotionUtils.m_43549_(super.m_7968_(), Potions.f_43599_);
    }

    @Override
    public ItemStack m_5922_(ItemStack p_42984_, Level p_42985_, LivingEntity p_42986_) {
        Player $$3;
        Player player = $$3 = p_42986_ instanceof Player ? (Player)p_42986_ : null;
        if ($$3 instanceof ServerPlayer) {
            CriteriaTriggers.f_10592_.m_23682_((ServerPlayer)$$3, p_42984_);
        }
        if (!p_42985_.f_46443_) {
            List<MobEffectInstance> $$4 = PotionUtils.m_43547_(p_42984_);
            for (MobEffectInstance $$5 : $$4) {
                if ($$5.m_19544_().m_8093_()) {
                    $$5.m_19544_().m_19461_($$3, $$3, p_42986_, $$5.m_19564_(), 1.0);
                    continue;
                }
                p_42986_.m_7292_(new MobEffectInstance($$5));
            }
        }
        if ($$3 != null) {
            $$3.m_36246_(Stats.f_12982_.m_12902_(this));
            if (!$$3.m_150110_().f_35937_) {
                p_42984_.m_41774_(1);
            }
        }
        if ($$3 == null || !$$3.m_150110_().f_35937_) {
            if (p_42984_.m_41619_()) {
                return new ItemStack(Items.f_42590_);
            }
            if ($$3 != null) {
                $$3.m_150109_().m_36054_(new ItemStack(Items.f_42590_));
            }
        }
        p_42986_.m_146850_(GameEvent.f_223704_);
        return p_42984_;
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_220235_) {
        Level $$1 = p_220235_.m_43725_();
        BlockPos $$2 = p_220235_.m_8083_();
        Player $$3 = p_220235_.m_43723_();
        ItemStack $$4 = p_220235_.m_43722_();
        BlockState $$5 = $$1.m_8055_($$2);
        if (p_220235_.m_43719_() != Direction.DOWN && $$5.m_204336_(BlockTags.f_215828_) && PotionUtils.m_43579_($$4) == Potions.f_43599_) {
            $$1.m_5594_(null, $$2, SoundEvents.f_11917_, SoundSource.PLAYERS, 1.0f, 1.0f);
            $$3.m_21008_(p_220235_.m_43724_(), ItemUtils.m_41813_($$4, $$3, new ItemStack(Items.f_42590_)));
            $$3.m_36246_(Stats.f_12982_.m_12902_($$4.m_41720_()));
            if (!$$1.f_46443_) {
                ServerLevel $$6 = (ServerLevel)$$1;
                for (int $$7 = 0; $$7 < 5; ++$$7) {
                    $$6.m_8767_(ParticleTypes.f_123769_, (double)$$2.m_123341_() + $$1.f_46441_.m_188500_(), $$2.m_123342_() + 1, (double)$$2.m_123343_() + $$1.f_46441_.m_188500_(), 1, 0.0, 0.0, 0.0, 1.0);
                }
            }
            $$1.m_5594_(null, $$2, SoundEvents.f_11769_, SoundSource.BLOCKS, 1.0f, 1.0f);
            $$1.m_142346_(null, GameEvent.f_157769_, $$2);
            $$1.m_46597_($$2, Blocks.f_220864_.m_49966_());
            return InteractionResult.m_19078_($$1.f_46443_);
        }
        return InteractionResult.PASS;
    }

    @Override
    public int m_8105_(ItemStack p_43001_) {
        return 32;
    }

    @Override
    public UseAnim m_6164_(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
        return ItemUtils.m_150959_(p_42993_, p_42994_, p_42995_);
    }

    @Override
    public String m_5671_(ItemStack p_43003_) {
        return PotionUtils.m_43579_(p_43003_).m_43492_(this.m_5524_() + ".effect.");
    }

    @Override
    public void m_7373_(ItemStack p_42988_, @Nullable Level p_42989_, List<Component> p_42990_, TooltipFlag p_42991_) {
        PotionUtils.m_43555_(p_42988_, p_42990_, 1.0f);
    }

    @Override
    public boolean m_5812_(ItemStack p_42999_) {
        return super.m_5812_(p_42999_) || !PotionUtils.m_43547_(p_42999_).isEmpty();
    }

    @Override
    public void m_6787_(CreativeModeTab p_42981_, NonNullList<ItemStack> p_42982_) {
        if (this.m_220152_(p_42981_)) {
            for (Potion $$2 : Registry.f_122828_) {
                if ($$2 == Potions.f_43598_) continue;
                p_42982_.add(PotionUtils.m_43549_(new ItemStack(this), $$2));
            }
        }
    }
}

