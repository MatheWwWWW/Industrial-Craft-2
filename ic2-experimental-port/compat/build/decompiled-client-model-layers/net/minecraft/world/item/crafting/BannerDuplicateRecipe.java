/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerBlockEntity;

public class BannerDuplicateRecipe
extends CustomRecipe {
    public BannerDuplicateRecipe(ResourceLocation p_43773_) {
        super(p_43773_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_43785_, Level p_43786_) {
        DyeColor $$2 = null;
        ItemStack $$3 = null;
        ItemStack $$4 = null;
        for (int $$5 = 0; $$5 < p_43785_.m_6643_(); ++$$5) {
            ItemStack $$6 = p_43785_.m_8020_($$5);
            if ($$6.m_41619_()) continue;
            Item $$7 = $$6.m_41720_();
            if (!($$7 instanceof BannerItem)) {
                return false;
            }
            BannerItem $$8 = (BannerItem)$$7;
            if ($$2 == null) {
                $$2 = $$8.m_40545_();
            } else if ($$2 != $$8.m_40545_()) {
                return false;
            }
            int $$9 = BannerBlockEntity.m_58504_($$6);
            if ($$9 > 6) {
                return false;
            }
            if ($$9 > 0) {
                if ($$3 == null) {
                    $$3 = $$6;
                    continue;
                }
                return false;
            }
            if ($$4 == null) {
                $$4 = $$6;
                continue;
            }
            return false;
        }
        return $$3 != null && $$4 != null;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_43783_) {
        for (int $$1 = 0; $$1 < p_43783_.m_6643_(); ++$$1) {
            int $$3;
            ItemStack $$2 = p_43783_.m_8020_($$1);
            if ($$2.m_41619_() || ($$3 = BannerBlockEntity.m_58504_($$2)) <= 0 || $$3 > 6) continue;
            ItemStack $$4 = $$2.m_41777_();
            $$4.m_41764_(1);
            return $$4;
        }
        return ItemStack.f_41583_;
    }

    @Override
    public NonNullList<ItemStack> m_7457_(CraftingContainer p_43791_) {
        NonNullList<ItemStack> $$1 = NonNullList.m_122780_(p_43791_.m_6643_(), ItemStack.f_41583_);
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            ItemStack $$3 = p_43791_.m_8020_($$2);
            if ($$3.m_41619_()) continue;
            if ($$3.m_41720_().m_41470_()) {
                $$1.set($$2, new ItemStack($$3.m_41720_().m_41469_()));
                continue;
            }
            if (!$$3.m_41782_() || BannerBlockEntity.m_58504_($$3) <= 0) continue;
            ItemStack $$4 = $$3.m_41777_();
            $$4.m_41764_(1);
            $$1.set($$2, $$4);
        }
        return $$1;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44086_;
    }

    @Override
    public boolean m_8004_(int p_43775_, int p_43776_) {
        return p_43775_ * p_43776_ >= 2;
    }
}

