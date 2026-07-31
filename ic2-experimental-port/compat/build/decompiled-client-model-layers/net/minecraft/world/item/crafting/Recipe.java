/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public interface Recipe<C extends Container> {
    public boolean m_5818_(C var1, Level var2);

    public ItemStack m_5874_(C var1);

    public boolean m_8004_(int var1, int var2);

    public ItemStack m_8043_();

    default public NonNullList<ItemStack> m_7457_(C p_44004_) {
        NonNullList<ItemStack> $$1 = NonNullList.m_122780_(p_44004_.m_6643_(), ItemStack.f_41583_);
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            Item $$3 = p_44004_.m_8020_($$2).m_41720_();
            if (!$$3.m_41470_()) continue;
            $$1.set($$2, new ItemStack($$3.m_41469_()));
        }
        return $$1;
    }

    default public NonNullList<Ingredient> m_7527_() {
        return NonNullList.m_122779_();
    }

    default public boolean m_5598_() {
        return false;
    }

    default public String m_6076_() {
        return "";
    }

    default public ItemStack m_8042_() {
        return new ItemStack(Blocks.f_50091_);
    }

    public ResourceLocation m_6423_();

    public RecipeSerializer<?> m_7707_();

    public RecipeType<?> m_6671_();

    default public boolean m_142505_() {
        NonNullList<Ingredient> $$0 = this.m_7527_();
        return $$0.isEmpty() || $$0.stream().anyMatch(p_151268_ -> p_151268_.m_43908_().length == 0);
    }
}

