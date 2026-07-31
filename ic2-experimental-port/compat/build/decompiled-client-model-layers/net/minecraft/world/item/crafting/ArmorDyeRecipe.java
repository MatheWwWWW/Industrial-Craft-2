/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ArmorDyeRecipe
extends CustomRecipe {
    public ArmorDyeRecipe(ResourceLocation p_43757_) {
        super(p_43757_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_43769_, Level p_43770_) {
        ItemStack $$2 = ItemStack.f_41583_;
        ArrayList $$3 = Lists.newArrayList();
        for (int $$4 = 0; $$4 < p_43769_.m_6643_(); ++$$4) {
            ItemStack $$5 = p_43769_.m_8020_($$4);
            if ($$5.m_41619_()) continue;
            if ($$5.m_41720_() instanceof DyeableLeatherItem) {
                if (!$$2.m_41619_()) {
                    return false;
                }
                $$2 = $$5;
                continue;
            }
            if ($$5.m_41720_() instanceof DyeItem) {
                $$3.add($$5);
                continue;
            }
            return false;
        }
        return !$$2.m_41619_() && !$$3.isEmpty();
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_43767_) {
        ArrayList $$1 = Lists.newArrayList();
        ItemStack $$2 = ItemStack.f_41583_;
        for (int $$3 = 0; $$3 < p_43767_.m_6643_(); ++$$3) {
            ItemStack $$4 = p_43767_.m_8020_($$3);
            if ($$4.m_41619_()) continue;
            Item $$5 = $$4.m_41720_();
            if ($$5 instanceof DyeableLeatherItem) {
                if (!$$2.m_41619_()) {
                    return ItemStack.f_41583_;
                }
                $$2 = $$4.m_41777_();
                continue;
            }
            if ($$5 instanceof DyeItem) {
                $$1.add((DyeItem)$$5);
                continue;
            }
            return ItemStack.f_41583_;
        }
        if ($$2.m_41619_() || $$1.isEmpty()) {
            return ItemStack.f_41583_;
        }
        return DyeableLeatherItem.m_41118_($$2, $$1);
    }

    @Override
    public boolean m_8004_(int p_43759_, int p_43760_) {
        return p_43759_ * p_43760_ >= 2;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44078_;
    }
}

