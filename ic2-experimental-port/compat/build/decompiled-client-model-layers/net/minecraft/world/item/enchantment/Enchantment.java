/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item.enchantment;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class Enchantment {
    private final EquipmentSlot[] f_44671_;
    private final Rarity f_44674_;
    public final EnchantmentCategory f_44672_;
    @Nullable
    protected String f_44673_;

    @Nullable
    public static Enchantment m_44697_(int p_44698_) {
        return (Enchantment)Registry.f_122825_.m_7942_(p_44698_);
    }

    protected Enchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        this.f_44674_ = p_44676_;
        this.f_44672_ = p_44677_;
        this.f_44671_ = p_44678_;
    }

    public Map<EquipmentSlot, ItemStack> m_44684_(LivingEntity p_44685_) {
        EnumMap $$1 = Maps.newEnumMap(EquipmentSlot.class);
        for (EquipmentSlot $$2 : this.f_44671_) {
            ItemStack $$3 = p_44685_.m_6844_($$2);
            if ($$3.m_41619_()) continue;
            $$1.put($$2, $$3);
        }
        return $$1;
    }

    public Rarity m_44699_() {
        return this.f_44674_;
    }

    public int m_44702_() {
        return 1;
    }

    public int m_6586_() {
        return 1;
    }

    public int m_6183_(int p_44679_) {
        return 1 + p_44679_ * 10;
    }

    public int m_6175_(int p_44691_) {
        return this.m_6183_(p_44691_) + 5;
    }

    public int m_7205_(int p_44680_, DamageSource p_44681_) {
        return 0;
    }

    public float m_7335_(int p_44682_, MobType p_44683_) {
        return 0.0f;
    }

    public final boolean m_44695_(Enchantment p_44696_) {
        return this.m_5975_(p_44696_) && p_44696_.m_5975_(this);
    }

    protected boolean m_5975_(Enchantment p_44690_) {
        return this != p_44690_;
    }

    protected String m_44703_() {
        if (this.f_44673_ == null) {
            this.f_44673_ = Util.m_137492_("enchantment", Registry.f_122825_.m_7981_(this));
        }
        return this.f_44673_;
    }

    public String m_44704_() {
        return this.m_44703_();
    }

    public Component m_44700_(int p_44701_) {
        MutableComponent $$1 = Component.m_237115_(this.m_44704_());
        if (this.m_6589_()) {
            $$1.m_130940_(ChatFormatting.RED);
        } else {
            $$1.m_130940_(ChatFormatting.GRAY);
        }
        if (p_44701_ != 1 || this.m_6586_() != 1) {
            $$1.m_130946_(" ").m_7220_(Component.m_237115_("enchantment.level." + p_44701_));
        }
        return $$1;
    }

    public boolean m_6081_(ItemStack p_44689_) {
        return this.f_44672_.m_7454_(p_44689_.m_41720_());
    }

    public void m_7677_(LivingEntity p_44686_, Entity p_44687_, int p_44688_) {
    }

    public void m_7675_(LivingEntity p_44692_, Entity p_44693_, int p_44694_) {
    }

    public boolean m_6591_() {
        return false;
    }

    public boolean m_6589_() {
        return false;
    }

    public boolean m_6594_() {
        return true;
    }

    public boolean m_6592_() {
        return true;
    }

    public static final class Rarity
    extends Enum<Rarity> {
        public static final /* enum */ Rarity COMMON = new Rarity(10);
        public static final /* enum */ Rarity UNCOMMON = new Rarity(5);
        public static final /* enum */ Rarity RARE = new Rarity(2);
        public static final /* enum */ Rarity VERY_RARE = new Rarity(1);
        private final int f_44709_;
        private static final /* synthetic */ Rarity[] $VALUES;

        public static Rarity[] values() {
            return (Rarity[])$VALUES.clone();
        }

        public static Rarity valueOf(String p_44718_) {
            return Enum.valueOf(Rarity.class, p_44718_);
        }

        private Rarity(int p_44715_) {
            this.f_44709_ = p_44715_;
        }

        public int m_44716_() {
            return this.f_44709_;
        }

        private static /* synthetic */ Rarity[] m_151292_() {
            return new Rarity[]{COMMON, UNCOMMON, RARE, VERY_RARE};
        }

        static {
            $VALUES = Rarity.m_151292_();
        }
    }
}

