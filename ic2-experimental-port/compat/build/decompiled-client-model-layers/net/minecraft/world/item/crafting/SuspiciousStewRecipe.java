/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;

public class SuspiciousStewRecipe
extends CustomRecipe {
    public SuspiciousStewRecipe(ResourceLocation p_44487_) {
        super(p_44487_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_44499_, Level p_44500_) {
        boolean $$2 = false;
        boolean $$3 = false;
        boolean $$4 = false;
        boolean $$5 = false;
        for (int $$6 = 0; $$6 < p_44499_.m_6643_(); ++$$6) {
            ItemStack $$7 = p_44499_.m_8020_($$6);
            if ($$7.m_41619_()) continue;
            if ($$7.m_150930_(Blocks.f_50072_.m_5456_()) && !$$4) {
                $$4 = true;
                continue;
            }
            if ($$7.m_150930_(Blocks.f_50073_.m_5456_()) && !$$3) {
                $$3 = true;
                continue;
            }
            if ($$7.m_204117_(ItemTags.f_13145_) && !$$2) {
                $$2 = true;
                continue;
            }
            if ($$7.m_150930_(Items.f_42399_) && !$$5) {
                $$5 = true;
                continue;
            }
            return false;
        }
        return $$2 && $$4 && $$3 && $$5;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_44497_) {
        ItemStack $$1 = ItemStack.f_41583_;
        for (int $$2 = 0; $$2 < p_44497_.m_6643_(); ++$$2) {
            ItemStack $$3 = p_44497_.m_8020_($$2);
            if ($$3.m_41619_() || !$$3.m_204117_(ItemTags.f_13145_)) continue;
            $$1 = $$3;
            break;
        }
        ItemStack $$4 = new ItemStack(Items.f_42718_, 1);
        if ($$1.m_41720_() instanceof BlockItem && ((BlockItem)$$1.m_41720_()).m_40614_() instanceof FlowerBlock) {
            FlowerBlock $$5 = (FlowerBlock)((BlockItem)$$1.m_41720_()).m_40614_();
            MobEffect $$6 = $$5.m_53521_();
            SuspiciousStewItem.m_43258_($$4, $$6, $$5.m_53522_());
        }
        return $$4;
    }

    @Override
    public boolean m_8004_(int p_44489_, int p_44490_) {
        return p_44489_ >= 2 && p_44490_ >= 2;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44089_;
    }
}

