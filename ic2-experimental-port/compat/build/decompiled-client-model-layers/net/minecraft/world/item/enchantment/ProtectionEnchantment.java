/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class ProtectionEnchantment
extends Enchantment {
    public final Type f_45124_;

    public ProtectionEnchantment(Enchantment.Rarity p_45126_, Type p_45127_, EquipmentSlot ... p_45128_) {
        super(p_45126_, p_45127_ == Type.FALL ? EnchantmentCategory.ARMOR_FEET : EnchantmentCategory.ARMOR, p_45128_);
        this.f_45124_ = p_45127_;
    }

    @Override
    public int m_6183_(int p_45131_) {
        return this.f_45124_.m_45161_() + (p_45131_ - 1) * this.f_45124_.m_45162_();
    }

    @Override
    public int m_6175_(int p_45144_) {
        return this.m_6183_(p_45144_) + this.f_45124_.m_45162_();
    }

    @Override
    public int m_6586_() {
        return 4;
    }

    @Override
    public int m_7205_(int p_45133_, DamageSource p_45134_) {
        if (p_45134_.m_19378_()) {
            return 0;
        }
        if (this.f_45124_ == Type.ALL) {
            return p_45133_;
        }
        if (this.f_45124_ == Type.FIRE && p_45134_.m_19384_()) {
            return p_45133_ * 2;
        }
        if (this.f_45124_ == Type.FALL && p_45134_.m_146707_()) {
            return p_45133_ * 3;
        }
        if (this.f_45124_ == Type.EXPLOSION && p_45134_.m_19372_()) {
            return p_45133_ * 2;
        }
        if (this.f_45124_ == Type.PROJECTILE && p_45134_.m_19360_()) {
            return p_45133_ * 2;
        }
        return 0;
    }

    @Override
    public boolean m_5975_(Enchantment p_45142_) {
        if (p_45142_ instanceof ProtectionEnchantment) {
            ProtectionEnchantment $$1 = (ProtectionEnchantment)p_45142_;
            if (this.f_45124_ == $$1.f_45124_) {
                return false;
            }
            return this.f_45124_ == Type.FALL || $$1.f_45124_ == Type.FALL;
        }
        return super.m_5975_(p_45142_);
    }

    public static int m_45138_(LivingEntity p_45139_, int p_45140_) {
        int $$2 = EnchantmentHelper.m_44836_(Enchantments.f_44966_, p_45139_);
        if ($$2 > 0) {
            p_45140_ -= Mth.m_14143_((float)p_45140_ * ((float)$$2 * 0.15f));
        }
        return p_45140_;
    }

    public static double m_45135_(LivingEntity p_45136_, double p_45137_) {
        int $$2 = EnchantmentHelper.m_44836_(Enchantments.f_44968_, p_45136_);
        if ($$2 > 0) {
            p_45137_ -= (double)Mth.m_14107_(p_45137_ * (double)((float)$$2 * 0.15f));
        }
        return p_45137_;
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type ALL = new Type(1, 11);
        public static final /* enum */ Type FIRE = new Type(10, 8);
        public static final /* enum */ Type FALL = new Type(5, 6);
        public static final /* enum */ Type EXPLOSION = new Type(5, 8);
        public static final /* enum */ Type PROJECTILE = new Type(3, 6);
        private final int f_45151_;
        private final int f_45152_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_45164_) {
            return Enum.valueOf(Type.class, p_45164_);
        }

        private Type(int p_151299_, int p_151300_) {
            this.f_45151_ = p_151299_;
            this.f_45152_ = p_151300_;
        }

        public int m_45161_() {
            return this.f_45151_;
        }

        public int m_45162_() {
            return this.f_45152_;
        }

        private static /* synthetic */ Type[] m_151301_() {
            return new Type[]{ALL, FIRE, FALL, EXPLOSION, PROJECTILE};
        }

        static {
            $VALUES = Type.m_151301_();
        }
    }
}

