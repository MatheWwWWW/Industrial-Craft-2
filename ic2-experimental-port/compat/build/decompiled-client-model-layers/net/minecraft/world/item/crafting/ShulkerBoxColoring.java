/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class ShulkerBoxColoring
extends CustomRecipe {
    public ShulkerBoxColoring(ResourceLocation p_44312_) {
        super(p_44312_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_44324_, Level p_44325_) {
        int $$2 = 0;
        int $$3 = 0;
        for (int $$4 = 0; $$4 < p_44324_.m_6643_(); ++$$4) {
            ItemStack $$5 = p_44324_.m_8020_($$4);
            if ($$5.m_41619_()) continue;
            if (Block.m_49814_($$5.m_41720_()) instanceof ShulkerBoxBlock) {
                ++$$2;
            } else if ($$5.m_41720_() instanceof DyeItem) {
                ++$$3;
            } else {
                return false;
            }
            if ($$3 <= 1 && $$2 <= 1) continue;
            return false;
        }
        return $$2 == 1 && $$3 == 1;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_44322_) {
        ItemStack $$1 = ItemStack.f_41583_;
        DyeItem $$2 = (DyeItem)Items.f_42535_;
        for (int $$3 = 0; $$3 < p_44322_.m_6643_(); ++$$3) {
            ItemStack $$4 = p_44322_.m_8020_($$3);
            if ($$4.m_41619_()) continue;
            Item $$5 = $$4.m_41720_();
            if (Block.m_49814_($$5) instanceof ShulkerBoxBlock) {
                $$1 = $$4;
                continue;
            }
            if (!($$5 instanceof DyeItem)) continue;
            $$2 = (DyeItem)$$5;
        }
        ItemStack $$6 = ShulkerBoxBlock.m_56250_($$2.m_41089_());
        if ($$1.m_41782_()) {
            $$6.m_41751_($$1.m_41783_().m_6426_());
        }
        return $$6;
    }

    @Override
    public boolean m_8004_(int p_44314_, int p_44315_) {
        return p_44314_ * p_44315_ >= 2;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44088_;
    }
}

