/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.item.alchemy;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class PotionBrewing {
    public static final int f_151252_ = 20;
    private static final List<Mix<Potion>> f_43494_ = Lists.newArrayList();
    private static final List<Mix<Item>> f_43495_ = Lists.newArrayList();
    private static final List<Ingredient> f_43496_ = Lists.newArrayList();
    private static final Predicate<ItemStack> f_43497_ = p_43528_ -> {
        for (Ingredient $$1 : f_43496_) {
            if (!$$1.test((ItemStack)p_43528_)) continue;
            return true;
        }
        return false;
    };

    public static boolean m_43506_(ItemStack p_43507_) {
        return PotionBrewing.m_43517_(p_43507_) || PotionBrewing.m_43522_(p_43507_);
    }

    protected static boolean m_43517_(ItemStack p_43518_) {
        int $$2 = f_43495_.size();
        for (int $$1 = 0; $$1 < $$2; ++$$1) {
            if (!PotionBrewing.f_43495_.get((int)$$1).f_43533_.test(p_43518_)) continue;
            return true;
        }
        return false;
    }

    protected static boolean m_43522_(ItemStack p_43523_) {
        int $$2 = f_43494_.size();
        for (int $$1 = 0; $$1 < $$2; ++$$1) {
            if (!PotionBrewing.f_43494_.get((int)$$1).f_43533_.test(p_43523_)) continue;
            return true;
        }
        return false;
    }

    public static boolean m_43511_(Potion p_43512_) {
        int $$2 = f_43494_.size();
        for (int $$1 = 0; $$1 < $$2; ++$$1) {
            if (PotionBrewing.f_43494_.get((int)$$1).f_43534_ != p_43512_) continue;
            return true;
        }
        return false;
    }

    public static boolean m_43508_(ItemStack p_43509_, ItemStack p_43510_) {
        if (!f_43497_.test(p_43509_)) {
            return false;
        }
        return PotionBrewing.m_43519_(p_43509_, p_43510_) || PotionBrewing.m_43524_(p_43509_, p_43510_);
    }

    protected static boolean m_43519_(ItemStack p_43520_, ItemStack p_43521_) {
        Item $$2 = p_43520_.m_41720_();
        int $$4 = f_43495_.size();
        for (int $$3 = 0; $$3 < $$4; ++$$3) {
            Mix<Item> $$5 = f_43495_.get($$3);
            if ($$5.f_43532_ != $$2 || !$$5.f_43533_.test(p_43521_)) continue;
            return true;
        }
        return false;
    }

    protected static boolean m_43524_(ItemStack p_43525_, ItemStack p_43526_) {
        Potion $$2 = PotionUtils.m_43579_(p_43525_);
        int $$4 = f_43494_.size();
        for (int $$3 = 0; $$3 < $$4; ++$$3) {
            Mix<Potion> $$5 = f_43494_.get($$3);
            if ($$5.f_43532_ != $$2 || !$$5.f_43533_.test(p_43526_)) continue;
            return true;
        }
        return false;
    }

    public static ItemStack m_43529_(ItemStack p_43530_, ItemStack p_43531_) {
        if (!p_43531_.m_41619_()) {
            Potion $$2 = PotionUtils.m_43579_(p_43531_);
            Item $$3 = p_43531_.m_41720_();
            int $$5 = f_43495_.size();
            for (int $$4 = 0; $$4 < $$5; ++$$4) {
                Mix<Item> $$6 = f_43495_.get($$4);
                if ($$6.f_43532_ != $$3 || !$$6.f_43533_.test(p_43530_)) continue;
                return PotionUtils.m_43549_(new ItemStack((ItemLike)$$6.f_43534_), $$2);
            }
            int $$8 = f_43494_.size();
            for (int $$7 = 0; $$7 < $$8; ++$$7) {
                Mix<Potion> $$9 = f_43494_.get($$7);
                if ($$9.f_43532_ != $$2 || !$$9.f_43533_.test(p_43530_)) continue;
                return PotionUtils.m_43549_(new ItemStack($$3), (Potion)$$9.f_43534_);
            }
        }
        return p_43531_;
    }

    public static void m_43499_() {
        PotionBrewing.m_43500_(Items.f_42589_);
        PotionBrewing.m_43500_(Items.f_42736_);
        PotionBrewing.m_43500_(Items.f_42739_);
        PotionBrewing.m_43502_(Items.f_42589_, Items.f_42403_, Items.f_42736_);
        PotionBrewing.m_43502_(Items.f_42736_, Items.f_42735_, Items.f_42739_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42546_, Potions.f_43600_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42586_, Potions.f_43600_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42648_, Potions.f_43600_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42593_, Potions.f_43600_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42591_, Potions.f_43600_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42501_, Potions.f_43600_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42542_, Potions.f_43600_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42525_, Potions.f_43601_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42451_, Potions.f_43600_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42588_, Potions.f_43602_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42677_, Potions.f_43603_);
        PotionBrewing.m_43513_(Potions.f_43603_, Items.f_42451_, Potions.f_43604_);
        PotionBrewing.m_43513_(Potions.f_43603_, Items.f_42592_, Potions.f_43605_);
        PotionBrewing.m_43513_(Potions.f_43604_, Items.f_42592_, Potions.f_43606_);
        PotionBrewing.m_43513_(Potions.f_43605_, Items.f_42451_, Potions.f_43606_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42542_, Potions.f_43610_);
        PotionBrewing.m_43513_(Potions.f_43610_, Items.f_42451_, Potions.f_43611_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42648_, Potions.f_43607_);
        PotionBrewing.m_43513_(Potions.f_43607_, Items.f_42451_, Potions.f_43608_);
        PotionBrewing.m_43513_(Potions.f_43607_, Items.f_42525_, Potions.f_43609_);
        PotionBrewing.m_43513_(Potions.f_43607_, Items.f_42592_, Potions.f_43615_);
        PotionBrewing.m_43513_(Potions.f_43608_, Items.f_42592_, Potions.f_43616_);
        PotionBrewing.m_43513_(Potions.f_43615_, Items.f_42451_, Potions.f_43616_);
        PotionBrewing.m_43513_(Potions.f_43615_, Items.f_42525_, Potions.f_43617_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42354_, Potions.f_43618_);
        PotionBrewing.m_43513_(Potions.f_43618_, Items.f_42451_, Potions.f_43619_);
        PotionBrewing.m_43513_(Potions.f_43618_, Items.f_42525_, Potions.f_43620_);
        PotionBrewing.m_43513_(Potions.f_43612_, Items.f_42592_, Potions.f_43615_);
        PotionBrewing.m_43513_(Potions.f_43613_, Items.f_42592_, Potions.f_43616_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42501_, Potions.f_43612_);
        PotionBrewing.m_43513_(Potions.f_43612_, Items.f_42451_, Potions.f_43613_);
        PotionBrewing.m_43513_(Potions.f_43612_, Items.f_42525_, Potions.f_43614_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42529_, Potions.f_43621_);
        PotionBrewing.m_43513_(Potions.f_43621_, Items.f_42451_, Potions.f_43622_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42546_, Potions.f_43623_);
        PotionBrewing.m_43513_(Potions.f_43623_, Items.f_42525_, Potions.f_43581_);
        PotionBrewing.m_43513_(Potions.f_43623_, Items.f_42592_, Potions.f_43582_);
        PotionBrewing.m_43513_(Potions.f_43581_, Items.f_42592_, Potions.f_43583_);
        PotionBrewing.m_43513_(Potions.f_43582_, Items.f_42525_, Potions.f_43583_);
        PotionBrewing.m_43513_(Potions.f_43584_, Items.f_42592_, Potions.f_43582_);
        PotionBrewing.m_43513_(Potions.f_43585_, Items.f_42592_, Potions.f_43582_);
        PotionBrewing.m_43513_(Potions.f_43586_, Items.f_42592_, Potions.f_43583_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42591_, Potions.f_43584_);
        PotionBrewing.m_43513_(Potions.f_43584_, Items.f_42451_, Potions.f_43585_);
        PotionBrewing.m_43513_(Potions.f_43584_, Items.f_42525_, Potions.f_43586_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42586_, Potions.f_43587_);
        PotionBrewing.m_43513_(Potions.f_43587_, Items.f_42451_, Potions.f_43588_);
        PotionBrewing.m_43513_(Potions.f_43587_, Items.f_42525_, Potions.f_43589_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42593_, Potions.f_43590_);
        PotionBrewing.m_43513_(Potions.f_43590_, Items.f_42451_, Potions.f_43591_);
        PotionBrewing.m_43513_(Potions.f_43590_, Items.f_42525_, Potions.f_43592_);
        PotionBrewing.m_43513_(Potions.f_43599_, Items.f_42592_, Potions.f_43593_);
        PotionBrewing.m_43513_(Potions.f_43593_, Items.f_42451_, Potions.f_43594_);
        PotionBrewing.m_43513_(Potions.f_43602_, Items.f_42714_, Potions.f_43596_);
        PotionBrewing.m_43513_(Potions.f_43596_, Items.f_42451_, Potions.f_43597_);
    }

    private static void m_43502_(Item p_43503_, Item p_43504_, Item p_43505_) {
        if (!(p_43503_ instanceof PotionItem)) {
            throw new IllegalArgumentException("Expected a potion, got: " + Registry.f_122827_.m_7981_(p_43503_));
        }
        if (!(p_43505_ instanceof PotionItem)) {
            throw new IllegalArgumentException("Expected a potion, got: " + Registry.f_122827_.m_7981_(p_43505_));
        }
        f_43495_.add(new Mix<Item>(p_43503_, Ingredient.m_43929_(p_43504_), p_43505_));
    }

    private static void m_43500_(Item p_43501_) {
        if (!(p_43501_ instanceof PotionItem)) {
            throw new IllegalArgumentException("Expected a potion, got: " + Registry.f_122827_.m_7981_(p_43501_));
        }
        f_43496_.add(Ingredient.m_43929_(p_43501_));
    }

    private static void m_43513_(Potion p_43514_, Item p_43515_, Potion p_43516_) {
        f_43494_.add(new Mix<Potion>(p_43514_, Ingredient.m_43929_(p_43515_), p_43516_));
    }

    static class Mix<T> {
        final T f_43532_;
        final Ingredient f_43533_;
        final T f_43534_;

        public Mix(T p_43536_, Ingredient p_43537_, T p_43538_) {
            this.f_43532_ = p_43536_;
            this.f_43533_ = p_43537_;
            this.f_43534_ = p_43538_;
        }
    }
}

