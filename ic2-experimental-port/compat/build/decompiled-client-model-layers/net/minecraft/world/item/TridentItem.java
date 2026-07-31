/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 */
package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TridentItem
extends Item
implements Vanishable {
    public static final int f_151230_ = 10;
    public static final float f_151231_ = 8.0f;
    public static final float f_151232_ = 2.5f;
    private final Multimap<Attribute, AttributeModifier> f_43379_;

    public TridentItem(Item.Properties p_43381_) {
        super(p_43381_);
        ImmutableMultimap.Builder $$1 = ImmutableMultimap.builder();
        $$1.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Tool modifier", 8.0, AttributeModifier.Operation.ADDITION));
        $$1.put((Object)Attributes.f_22283_, (Object)new AttributeModifier(f_41375_, "Tool modifier", (double)-2.9f, AttributeModifier.Operation.ADDITION));
        this.f_43379_ = $$1.build();
    }

    @Override
    public boolean m_6777_(BlockState p_43409_, Level p_43410_, BlockPos p_43411_, Player p_43412_) {
        return !p_43412_.m_7500_();
    }

    @Override
    public UseAnim m_6164_(ItemStack p_43417_) {
        return UseAnim.SPEAR;
    }

    @Override
    public int m_8105_(ItemStack p_43419_) {
        return 72000;
    }

    @Override
    public void m_5551_(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if (!(p_43396_ instanceof Player)) {
            return;
        }
        Player $$4 = (Player)p_43396_;
        int $$5 = this.m_8105_(p_43394_) - p_43397_;
        if ($$5 < 10) {
            return;
        }
        int $$6 = EnchantmentHelper.m_44932_(p_43394_);
        if ($$6 > 0 && !$$4.m_20070_()) {
            return;
        }
        if (!p_43395_.f_46443_) {
            p_43394_.m_41622_(1, $$4, p_43388_ -> p_43388_.m_21190_(p_43396_.m_7655_()));
            if ($$6 == 0) {
                ThrownTrident $$7 = new ThrownTrident(p_43395_, (LivingEntity)$$4, p_43394_);
                $$7.m_37251_($$4, $$4.m_146909_(), $$4.m_146908_(), 0.0f, 2.5f + (float)$$6 * 0.5f, 1.0f);
                if ($$4.m_150110_().f_35937_) {
                    $$7.f_36705_ = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
                p_43395_.m_7967_($$7);
                p_43395_.m_6269_(null, $$7, SoundEvents.f_12520_, SoundSource.PLAYERS, 1.0f, 1.0f);
                if (!$$4.m_150110_().f_35937_) {
                    $$4.m_150109_().m_36057_(p_43394_);
                }
            }
        }
        $$4.m_36246_(Stats.f_12982_.m_12902_(this));
        if ($$6 > 0) {
            SoundEvent $$18;
            float $$8 = $$4.m_146908_();
            float $$9 = $$4.m_146909_();
            float $$10 = -Mth.m_14031_($$8 * ((float)Math.PI / 180)) * Mth.m_14089_($$9 * ((float)Math.PI / 180));
            float $$11 = -Mth.m_14031_($$9 * ((float)Math.PI / 180));
            float $$12 = Mth.m_14089_($$8 * ((float)Math.PI / 180)) * Mth.m_14089_($$9 * ((float)Math.PI / 180));
            float $$13 = Mth.m_14116_($$10 * $$10 + $$11 * $$11 + $$12 * $$12);
            float $$14 = 3.0f * ((1.0f + (float)$$6) / 4.0f);
            $$4.m_5997_($$10 *= $$14 / $$13, $$11 *= $$14 / $$13, $$12 *= $$14 / $$13);
            $$4.m_204079_(20);
            if ($$4.m_20096_()) {
                float $$15 = 1.1999999f;
                $$4.m_6478_(MoverType.SELF, new Vec3(0.0, 1.1999999284744263, 0.0));
            }
            if ($$6 >= 3) {
                SoundEvent $$16 = SoundEvents.f_12519_;
            } else if ($$6 == 2) {
                SoundEvent $$17 = SoundEvents.f_12518_;
            } else {
                $$18 = SoundEvents.f_12517_;
            }
            p_43395_.m_6269_(null, $$4, $$18, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack $$3 = p_43406_.m_21120_(p_43407_);
        if ($$3.m_41773_() >= $$3.m_41776_() - 1) {
            return InteractionResultHolder.m_19100_($$3);
        }
        if (EnchantmentHelper.m_44932_($$3) > 0 && !p_43406_.m_20070_()) {
            return InteractionResultHolder.m_19100_($$3);
        }
        p_43406_.m_6672_(p_43407_);
        return InteractionResultHolder.m_19096_($$3);
    }

    @Override
    public boolean m_7579_(ItemStack p_43390_, LivingEntity p_43391_, LivingEntity p_43392_) {
        p_43390_.m_41622_(1, p_43392_, p_43414_ -> p_43414_.m_21166_(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean m_6813_(ItemStack p_43399_, Level p_43400_, BlockState p_43401_, BlockPos p_43402_, LivingEntity p_43403_) {
        if ((double)p_43401_.m_60800_(p_43400_, p_43402_) != 0.0) {
            p_43399_.m_41622_(2, p_43403_, p_43385_ -> p_43385_.m_21166_(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot p_43383_) {
        if (p_43383_ == EquipmentSlot.MAINHAND) {
            return this.f_43379_;
        }
        return super.m_7167_(p_43383_);
    }

    @Override
    public int m_6473_() {
        return 1;
    }
}

