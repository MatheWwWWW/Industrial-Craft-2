/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.function.Predicate;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class BowItem
extends ProjectileWeaponItem
implements Vanishable {
    public static final int f_150704_ = 20;
    public static final int f_150705_ = 15;

    public BowItem(Item.Properties p_40660_) {
        super(p_40660_);
    }

    @Override
    public void m_5551_(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
        boolean $$9;
        int $$7;
        float $$8;
        if (!(p_40669_ instanceof Player)) {
            return;
        }
        Player $$4 = (Player)p_40669_;
        boolean $$5 = $$4.m_150110_().f_35937_ || EnchantmentHelper.m_44843_(Enchantments.f_44952_, p_40667_) > 0;
        ItemStack $$6 = $$4.m_6298_(p_40667_);
        if ($$6.m_41619_() && !$$5) {
            return;
        }
        if ($$6.m_41619_()) {
            $$6 = new ItemStack(Items.f_42412_);
        }
        if ((double)($$8 = BowItem.m_40661_($$7 = this.m_8105_(p_40667_) - p_40670_)) < 0.1) {
            return;
        }
        boolean bl = $$9 = $$5 && $$6.m_150930_(Items.f_42412_);
        if (!p_40668_.f_46443_) {
            int $$13;
            int $$12;
            ArrowItem $$10 = (ArrowItem)($$6.m_41720_() instanceof ArrowItem ? $$6.m_41720_() : Items.f_42412_);
            AbstractArrow $$11 = $$10.m_6394_(p_40668_, $$6, $$4);
            $$11.m_37251_($$4, $$4.m_146909_(), $$4.m_146908_(), 0.0f, $$8 * 3.0f, 1.0f);
            if ($$8 == 1.0f) {
                $$11.m_36762_(true);
            }
            if (($$12 = EnchantmentHelper.m_44843_(Enchantments.f_44988_, p_40667_)) > 0) {
                $$11.m_36781_($$11.m_36789_() + (double)$$12 * 0.5 + 0.5);
            }
            if (($$13 = EnchantmentHelper.m_44843_(Enchantments.f_44989_, p_40667_)) > 0) {
                $$11.m_36735_($$13);
            }
            if (EnchantmentHelper.m_44843_(Enchantments.f_44990_, p_40667_) > 0) {
                $$11.m_20254_(100);
            }
            p_40667_.m_41622_(1, $$4, p_40665_ -> p_40665_.m_21190_($$4.m_7655_()));
            if ($$9 || $$4.m_150110_().f_35937_ && ($$6.m_150930_(Items.f_42737_) || $$6.m_150930_(Items.f_42738_))) {
                $$11.f_36705_ = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            p_40668_.m_7967_($$11);
        }
        p_40668_.m_6263_(null, $$4.m_20185_(), $$4.m_20186_(), $$4.m_20189_(), SoundEvents.f_11687_, SoundSource.PLAYERS, 1.0f, 1.0f / (p_40668_.m_213780_().m_188501_() * 0.4f + 1.2f) + $$8 * 0.5f);
        if (!$$9 && !$$4.m_150110_().f_35937_) {
            $$6.m_41774_(1);
            if ($$6.m_41619_()) {
                $$4.m_150109_().m_36057_($$6);
            }
        }
        $$4.m_36246_(Stats.f_12982_.m_12902_(this));
    }

    public static float m_40661_(int p_40662_) {
        float $$1 = (float)p_40662_ / 20.0f;
        if (($$1 = ($$1 * $$1 + $$1 * 2.0f) / 3.0f) > 1.0f) {
            $$1 = 1.0f;
        }
        return $$1;
    }

    @Override
    public int m_8105_(ItemStack p_40680_) {
        return 72000;
    }

    @Override
    public UseAnim m_6164_(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
        boolean $$4;
        ItemStack $$3 = p_40673_.m_21120_(p_40674_);
        boolean bl = $$4 = !p_40673_.m_6298_($$3).m_41619_();
        if (p_40673_.m_150110_().f_35937_ || $$4) {
            p_40673_.m_6672_(p_40674_);
            return InteractionResultHolder.m_19096_($$3);
        }
        return InteractionResultHolder.m_19100_($$3);
    }

    @Override
    public Predicate<ItemStack> m_6437_() {
        return f_43005_;
    }

    @Override
    public int m_6615_() {
        return 15;
    }
}

