/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class RepairItemRecipe
extends CustomRecipe {
    public RepairItemRecipe(ResourceLocation p_44126_) {
        super(p_44126_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_44138_, Level p_44139_) {
        ArrayList $$2 = Lists.newArrayList();
        for (int $$3 = 0; $$3 < p_44138_.m_6643_(); ++$$3) {
            ItemStack $$5;
            ItemStack $$4 = p_44138_.m_8020_($$3);
            if ($$4.m_41619_()) continue;
            $$2.add($$4);
            if ($$2.size() <= 1 || $$4.m_150930_(($$5 = (ItemStack)$$2.get(0)).m_41720_()) && $$5.m_41613_() == 1 && $$4.m_41613_() == 1 && $$5.m_41720_().m_41465_()) continue;
            return false;
        }
        return $$2.size() == 2;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_44136_) {
        ItemStack $$6;
        ItemStack $$5;
        ArrayList $$1 = Lists.newArrayList();
        for (int $$2 = 0; $$2 < p_44136_.m_6643_(); ++$$2) {
            ItemStack $$4;
            ItemStack $$3 = p_44136_.m_8020_($$2);
            if ($$3.m_41619_()) continue;
            $$1.add($$3);
            if ($$1.size() <= 1 || $$3.m_150930_(($$4 = (ItemStack)$$1.get(0)).m_41720_()) && $$4.m_41613_() == 1 && $$3.m_41613_() == 1 && $$4.m_41720_().m_41465_()) continue;
            return ItemStack.f_41583_;
        }
        if ($$1.size() == 2 && ($$5 = (ItemStack)$$1.get(0)).m_150930_(($$6 = (ItemStack)$$1.get(1)).m_41720_()) && $$5.m_41613_() == 1 && $$6.m_41613_() == 1 && $$5.m_41720_().m_41465_()) {
            Item $$7 = $$5.m_41720_();
            int $$8 = $$7.m_41462_() - $$5.m_41773_();
            int $$9 = $$7.m_41462_() - $$6.m_41773_();
            int $$10 = $$8 + $$9 + $$7.m_41462_() * 5 / 100;
            int $$11 = $$7.m_41462_() - $$10;
            if ($$11 < 0) {
                $$11 = 0;
            }
            ItemStack $$12 = new ItemStack($$5.m_41720_());
            $$12.m_41721_($$11);
            HashMap $$13 = Maps.newHashMap();
            Map<Enchantment, Integer> $$14 = EnchantmentHelper.m_44831_($$5);
            Map<Enchantment, Integer> $$15 = EnchantmentHelper.m_44831_($$6);
            Registry.f_122825_.m_123024_().filter(Enchantment::m_6589_).forEach(p_44144_ -> {
                int $$4 = Math.max($$14.getOrDefault(p_44144_, 0), $$15.getOrDefault(p_44144_, 0));
                if ($$4 > 0) {
                    $$13.put(p_44144_, $$4);
                }
            });
            if (!$$13.isEmpty()) {
                EnchantmentHelper.m_44865_($$13, $$12);
            }
            return $$12;
        }
        return ItemStack.f_41583_;
    }

    @Override
    public boolean m_8004_(int p_44128_, int p_44129_) {
        return p_44128_ * p_44129_ >= 2;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44090_;
    }
}

