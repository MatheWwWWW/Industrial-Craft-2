/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;

public abstract class CustomRecipe
implements CraftingRecipe {
    private final ResourceLocation f_43831_;

    public CustomRecipe(ResourceLocation p_43833_) {
        this.f_43831_ = p_43833_;
    }

    @Override
    public ResourceLocation m_6423_() {
        return this.f_43831_;
    }

    @Override
    public boolean m_5598_() {
        return true;
    }

    @Override
    public ItemStack m_8043_() {
        return ItemStack.f_41583_;
    }
}

