/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableFloat
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.world.item.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class EnchantmentHelper {
    private static final String f_182430_ = "id";
    private static final String f_182431_ = "lvl";
    private static final float f_220286_ = 0.15f;

    public static CompoundTag m_182443_(@Nullable ResourceLocation p_182444_, int p_182445_) {
        CompoundTag $$2 = new CompoundTag();
        $$2.m_128359_(f_182430_, String.valueOf(p_182444_));
        $$2.m_128376_(f_182431_, (short)p_182445_);
        return $$2;
    }

    public static void m_182440_(CompoundTag p_182441_, int p_182442_) {
        p_182441_.m_128376_(f_182431_, (short)p_182442_);
    }

    public static int m_182438_(CompoundTag p_182439_) {
        return Mth.m_14045_(p_182439_.m_128451_(f_182431_), 0, 255);
    }

    @Nullable
    public static ResourceLocation m_182446_(CompoundTag p_182447_) {
        return ResourceLocation.m_135820_(p_182447_.m_128461_(f_182430_));
    }

    @Nullable
    public static ResourceLocation m_182432_(Enchantment p_182433_) {
        return Registry.f_122825_.m_7981_(p_182433_);
    }

    public static int m_44843_(Enchantment p_44844_, ItemStack p_44845_) {
        if (p_44845_.m_41619_()) {
            return 0;
        }
        ResourceLocation $$2 = EnchantmentHelper.m_182432_(p_44844_);
        ListTag $$3 = p_44845_.m_41785_();
        for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
            CompoundTag $$5 = $$3.m_128728_($$4);
            ResourceLocation $$6 = EnchantmentHelper.m_182446_($$5);
            if ($$6 == null || !$$6.equals($$2)) continue;
            return EnchantmentHelper.m_182438_($$5);
        }
        return 0;
    }

    public static Map<Enchantment, Integer> m_44831_(ItemStack p_44832_) {
        ListTag $$1 = p_44832_.m_150930_(Items.f_42690_) ? EnchantedBookItem.m_41163_(p_44832_) : p_44832_.m_41785_();
        return EnchantmentHelper.m_44882_($$1);
    }

    public static Map<Enchantment, Integer> m_44882_(ListTag p_44883_) {
        LinkedHashMap $$1 = Maps.newLinkedHashMap();
        for (int $$2 = 0; $$2 < p_44883_.size(); ++$$2) {
            CompoundTag $$3 = p_44883_.m_128728_($$2);
            Registry.f_122825_.m_6612_(EnchantmentHelper.m_182446_($$3)).ifPresent(p_44871_ -> $$1.put(p_44871_, EnchantmentHelper.m_182438_($$3)));
        }
        return $$1;
    }

    public static void m_44865_(Map<Enchantment, Integer> p_44866_, ItemStack p_44867_) {
        ListTag $$2 = new ListTag();
        for (Map.Entry<Enchantment, Integer> $$3 : p_44866_.entrySet()) {
            Enchantment $$4 = $$3.getKey();
            if ($$4 == null) continue;
            int $$5 = $$3.getValue();
            $$2.add(EnchantmentHelper.m_182443_(EnchantmentHelper.m_182432_($$4), $$5));
            if (!p_44867_.m_150930_(Items.f_42690_)) continue;
            EnchantedBookItem.m_41153_(p_44867_, new EnchantmentInstance($$4, $$5));
        }
        if ($$2.isEmpty()) {
            p_44867_.m_41749_("Enchantments");
        } else if (!p_44867_.m_150930_(Items.f_42690_)) {
            p_44867_.m_41700_("Enchantments", $$2);
        }
    }

    private static void m_44850_(EnchantmentVisitor p_44851_, ItemStack p_44852_) {
        if (p_44852_.m_41619_()) {
            return;
        }
        ListTag $$2 = p_44852_.m_41785_();
        for (int $$3 = 0; $$3 < $$2.size(); ++$$3) {
            CompoundTag $$4 = $$2.m_128728_($$3);
            Registry.f_122825_.m_6612_(EnchantmentHelper.m_182446_($$4)).ifPresent(p_182437_ -> p_44851_.m_44944_((Enchantment)p_182437_, EnchantmentHelper.m_182438_($$4)));
        }
    }

    private static void m_44853_(EnchantmentVisitor p_44854_, Iterable<ItemStack> p_44855_) {
        for (ItemStack $$2 : p_44855_) {
            EnchantmentHelper.m_44850_(p_44854_, $$2);
        }
    }

    public static int m_44856_(Iterable<ItemStack> p_44857_, DamageSource p_44858_) {
        MutableInt $$2 = new MutableInt();
        EnchantmentHelper.m_44853_((p_44892_, p_44893_) -> $$2.add(p_44892_.m_7205_(p_44893_, p_44858_)), p_44857_);
        return $$2.intValue();
    }

    public static float m_44833_(ItemStack p_44834_, MobType p_44835_) {
        MutableFloat $$2 = new MutableFloat();
        EnchantmentHelper.m_44850_((p_44887_, p_44888_) -> $$2.add(p_44887_.m_7335_(p_44888_, p_44835_)), p_44834_);
        return $$2.floatValue();
    }

    public static float m_44821_(LivingEntity p_44822_) {
        int $$1 = EnchantmentHelper.m_44836_(Enchantments.f_44983_, p_44822_);
        if ($$1 > 0) {
            return SweepingEdgeEnchantment.m_45193_($$1);
        }
        return 0.0f;
    }

    public static void m_44823_(LivingEntity p_44824_, Entity p_44825_) {
        EnchantmentVisitor $$2 = (p_44902_, p_44903_) -> p_44902_.m_7675_(p_44824_, p_44825_, p_44903_);
        if (p_44824_ != null) {
            EnchantmentHelper.m_44853_($$2, p_44824_.m_20158_());
        }
        if (p_44825_ instanceof Player) {
            EnchantmentHelper.m_44850_($$2, p_44824_.m_21205_());
        }
    }

    public static void m_44896_(LivingEntity p_44897_, Entity p_44898_) {
        EnchantmentVisitor $$2 = (p_44829_, p_44830_) -> p_44829_.m_7677_(p_44897_, p_44898_, p_44830_);
        if (p_44897_ != null) {
            EnchantmentHelper.m_44853_($$2, p_44897_.m_20158_());
        }
        if (p_44897_ instanceof Player) {
            EnchantmentHelper.m_44850_($$2, p_44897_.m_21205_());
        }
    }

    public static int m_44836_(Enchantment p_44837_, LivingEntity p_44838_) {
        Collection<ItemStack> $$2 = p_44837_.m_44684_(p_44838_).values();
        if ($$2 == null) {
            return 0;
        }
        int $$3 = 0;
        for (ItemStack $$4 : $$2) {
            int $$5 = EnchantmentHelper.m_44843_(p_44837_, $$4);
            if ($$5 <= $$3) continue;
            $$3 = $$5;
        }
        return $$3;
    }

    public static float m_220302_(LivingEntity p_220303_) {
        return (float)EnchantmentHelper.m_44836_(Enchantments.f_220304_, p_220303_) * 0.15f;
    }

    public static int m_44894_(LivingEntity p_44895_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44980_, p_44895_);
    }

    public static int m_44914_(LivingEntity p_44915_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44981_, p_44915_);
    }

    public static int m_44918_(LivingEntity p_44919_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44970_, p_44919_);
    }

    public static int m_44922_(LivingEntity p_44923_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44973_, p_44923_);
    }

    public static int m_44926_(LivingEntity p_44927_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44984_, p_44927_);
    }

    public static int m_44904_(ItemStack p_44905_) {
        return EnchantmentHelper.m_44843_(Enchantments.f_44953_, p_44905_);
    }

    public static int m_44916_(ItemStack p_44917_) {
        return EnchantmentHelper.m_44843_(Enchantments.f_44954_, p_44917_);
    }

    public static int m_44930_(LivingEntity p_44931_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44982_, p_44931_);
    }

    public static boolean m_44934_(LivingEntity p_44935_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44971_, p_44935_) > 0;
    }

    public static boolean m_44938_(LivingEntity p_44939_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44974_, p_44939_) > 0;
    }

    public static boolean m_44942_(LivingEntity p_44943_) {
        return EnchantmentHelper.m_44836_(Enchantments.f_44976_, p_44943_) > 0;
    }

    public static boolean m_44920_(ItemStack p_44921_) {
        return EnchantmentHelper.m_44843_(Enchantments.f_44975_, p_44921_) > 0;
    }

    public static boolean m_44924_(ItemStack p_44925_) {
        return EnchantmentHelper.m_44843_(Enchantments.f_44963_, p_44925_) > 0;
    }

    public static int m_44928_(ItemStack p_44929_) {
        return EnchantmentHelper.m_44843_(Enchantments.f_44955_, p_44929_);
    }

    public static int m_44932_(ItemStack p_44933_) {
        return EnchantmentHelper.m_44843_(Enchantments.f_44957_, p_44933_);
    }

    public static boolean m_44936_(ItemStack p_44937_) {
        return EnchantmentHelper.m_44843_(Enchantments.f_44958_, p_44937_) > 0;
    }

    @Nullable
    public static Map.Entry<EquipmentSlot, ItemStack> m_44906_(Enchantment p_44907_, LivingEntity p_44908_) {
        return EnchantmentHelper.m_44839_(p_44907_, p_44908_, p_44941_ -> true);
    }

    @Nullable
    public static Map.Entry<EquipmentSlot, ItemStack> m_44839_(Enchantment p_44840_, LivingEntity p_44841_, Predicate<ItemStack> p_44842_) {
        Map<EquipmentSlot, ItemStack> $$3 = p_44840_.m_44684_(p_44841_);
        if ($$3.isEmpty()) {
            return null;
        }
        ArrayList $$4 = Lists.newArrayList();
        for (Map.Entry<EquipmentSlot, ItemStack> $$5 : $$3.entrySet()) {
            ItemStack $$6 = $$5.getValue();
            if ($$6.m_41619_() || EnchantmentHelper.m_44843_(p_44840_, $$6) <= 0 || !p_44842_.test($$6)) continue;
            $$4.add($$5);
        }
        return $$4.isEmpty() ? null : (Map.Entry)$$4.get(p_44841_.m_217043_().m_188503_($$4.size()));
    }

    public static int m_220287_(RandomSource p_220288_, int p_220289_, int p_220290_, ItemStack p_220291_) {
        Item $$4 = p_220291_.m_41720_();
        int $$5 = $$4.m_6473_();
        if ($$5 <= 0) {
            return 0;
        }
        if (p_220290_ > 15) {
            p_220290_ = 15;
        }
        int $$6 = p_220288_.m_188503_(8) + 1 + (p_220290_ >> 1) + p_220288_.m_188503_(p_220290_ + 1);
        if (p_220289_ == 0) {
            return Math.max($$6 / 3, 1);
        }
        if (p_220289_ == 1) {
            return $$6 * 2 / 3 + 1;
        }
        return Math.max($$6, p_220290_ * 2);
    }

    public static ItemStack m_220292_(RandomSource p_220293_, ItemStack p_220294_, int p_220295_, boolean p_220296_) {
        List<EnchantmentInstance> $$4 = EnchantmentHelper.m_220297_(p_220293_, p_220294_, p_220295_, p_220296_);
        boolean $$5 = p_220294_.m_150930_(Items.f_42517_);
        if ($$5) {
            p_220294_ = new ItemStack(Items.f_42690_);
        }
        for (EnchantmentInstance $$6 : $$4) {
            if ($$5) {
                EnchantedBookItem.m_41153_(p_220294_, $$6);
                continue;
            }
            p_220294_.m_41663_($$6.f_44947_, $$6.f_44948_);
        }
        return p_220294_;
    }

    public static List<EnchantmentInstance> m_220297_(RandomSource p_220298_, ItemStack p_220299_, int p_220300_, boolean p_220301_) {
        ArrayList $$4 = Lists.newArrayList();
        Item $$5 = p_220299_.m_41720_();
        int $$6 = $$5.m_6473_();
        if ($$6 <= 0) {
            return $$4;
        }
        p_220300_ += 1 + p_220298_.m_188503_($$6 / 4 + 1) + p_220298_.m_188503_($$6 / 4 + 1);
        float $$7 = (p_220298_.m_188501_() + p_220298_.m_188501_() - 1.0f) * 0.15f;
        List<EnchantmentInstance> $$8 = EnchantmentHelper.m_44817_(p_220300_ = Mth.m_14045_(Math.round((float)p_220300_ + (float)p_220300_ * $$7), 1, Integer.MAX_VALUE), p_220299_, p_220301_);
        if (!$$8.isEmpty()) {
            WeightedRandom.m_216822_(p_220298_, $$8).ifPresent($$4::add);
            while (p_220298_.m_188503_(50) <= p_220300_) {
                if (!$$4.isEmpty()) {
                    EnchantmentHelper.m_44862_($$8, (EnchantmentInstance)Util.m_137509_($$4));
                }
                if ($$8.isEmpty()) break;
                WeightedRandom.m_216822_(p_220298_, $$8).ifPresent($$4::add);
                p_220300_ /= 2;
            }
        }
        return $$4;
    }

    public static void m_44862_(List<EnchantmentInstance> p_44863_, EnchantmentInstance p_44864_) {
        Iterator<EnchantmentInstance> $$2 = p_44863_.iterator();
        while ($$2.hasNext()) {
            if (p_44864_.f_44947_.m_44695_($$2.next().f_44947_)) continue;
            $$2.remove();
        }
    }

    public static boolean m_44859_(Collection<Enchantment> p_44860_, Enchantment p_44861_) {
        for (Enchantment $$2 : p_44860_) {
            if ($$2.m_44695_(p_44861_)) continue;
            return false;
        }
        return true;
    }

    public static List<EnchantmentInstance> m_44817_(int p_44818_, ItemStack p_44819_, boolean p_44820_) {
        ArrayList $$3 = Lists.newArrayList();
        Item $$4 = p_44819_.m_41720_();
        boolean $$5 = p_44819_.m_150930_(Items.f_42517_);
        block0: for (Enchantment $$6 : Registry.f_122825_) {
            if ($$6.m_6591_() && !p_44820_ || !$$6.m_6592_() || !$$6.f_44672_.m_7454_($$4) && !$$5) continue;
            for (int $$7 = $$6.m_6586_(); $$7 > $$6.m_44702_() - 1; --$$7) {
                if (p_44818_ < $$6.m_6183_($$7) || p_44818_ > $$6.m_6175_($$7)) continue;
                $$3.add(new EnchantmentInstance($$6, $$7));
                continue block0;
            }
        }
        return $$3;
    }

    @FunctionalInterface
    static interface EnchantmentVisitor {
        public void m_44944_(Enchantment var1, int var2);
    }
}

