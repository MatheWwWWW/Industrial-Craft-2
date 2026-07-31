/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class TippedArrowRecipe
extends CustomRecipe {
    public TippedArrowRecipe(ResourceLocation p_44503_) {
        super(p_44503_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_44515_, Level p_44516_) {
        if (p_44515_.m_39347_() != 3 || p_44515_.m_39346_() != 3) {
            return false;
        }
        for (int $$2 = 0; $$2 < p_44515_.m_39347_(); ++$$2) {
            for (int $$3 = 0; $$3 < p_44515_.m_39346_(); ++$$3) {
                ItemStack $$4 = p_44515_.m_8020_($$2 + $$3 * p_44515_.m_39347_());
                if ($$4.m_41619_()) {
                    return false;
                }
                if (!($$2 == 1 && $$3 == 1 ? !$$4.m_150930_(Items.f_42739_) : !$$4.m_150930_(Items.f_42412_))) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_44513_) {
        ItemStack $$1 = p_44513_.m_8020_(1 + p_44513_.m_39347_());
        if (!$$1.m_150930_(Items.f_42739_)) {
            return ItemStack.f_41583_;
        }
        ItemStack $$2 = new ItemStack(Items.f_42738_, 8);
        PotionUtils.m_43549_($$2, PotionUtils.m_43579_($$1));
        PotionUtils.m_43552_($$2, PotionUtils.m_43571_($$1));
        return $$2;
    }

    @Override
    public boolean m_8004_(int p_44505_, int p_44506_) {
        return p_44505_ >= 2 && p_44506_ >= 2;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44085_;
    }
}

