/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class MapCloningRecipe
extends CustomRecipe {
    public MapCloningRecipe(ResourceLocation p_43968_) {
        super(p_43968_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_43980_, Level p_43981_) {
        int $$2 = 0;
        ItemStack $$3 = ItemStack.f_41583_;
        for (int $$4 = 0; $$4 < p_43980_.m_6643_(); ++$$4) {
            ItemStack $$5 = p_43980_.m_8020_($$4);
            if ($$5.m_41619_()) continue;
            if ($$5.m_150930_(Items.f_42573_)) {
                if (!$$3.m_41619_()) {
                    return false;
                }
                $$3 = $$5;
                continue;
            }
            if ($$5.m_150930_(Items.f_42676_)) {
                ++$$2;
                continue;
            }
            return false;
        }
        return !$$3.m_41619_() && $$2 > 0;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_43978_) {
        int $$1 = 0;
        ItemStack $$2 = ItemStack.f_41583_;
        for (int $$3 = 0; $$3 < p_43978_.m_6643_(); ++$$3) {
            ItemStack $$4 = p_43978_.m_8020_($$3);
            if ($$4.m_41619_()) continue;
            if ($$4.m_150930_(Items.f_42573_)) {
                if (!$$2.m_41619_()) {
                    return ItemStack.f_41583_;
                }
                $$2 = $$4;
                continue;
            }
            if ($$4.m_150930_(Items.f_42676_)) {
                ++$$1;
                continue;
            }
            return ItemStack.f_41583_;
        }
        if ($$2.m_41619_() || $$1 < 1) {
            return ItemStack.f_41583_;
        }
        ItemStack $$5 = $$2.m_41777_();
        $$5.m_41764_($$1 + 1);
        return $$5;
    }

    @Override
    public boolean m_8004_(int p_43970_, int p_43971_) {
        return p_43970_ >= 3 && p_43971_ >= 3;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44080_;
    }
}

