/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class BookCloningRecipe
extends CustomRecipe {
    public BookCloningRecipe(ResourceLocation p_43802_) {
        super(p_43802_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_43814_, Level p_43815_) {
        int $$2 = 0;
        ItemStack $$3 = ItemStack.f_41583_;
        for (int $$4 = 0; $$4 < p_43814_.m_6643_(); ++$$4) {
            ItemStack $$5 = p_43814_.m_8020_($$4);
            if ($$5.m_41619_()) continue;
            if ($$5.m_150930_(Items.f_42615_)) {
                if (!$$3.m_41619_()) {
                    return false;
                }
                $$3 = $$5;
                continue;
            }
            if ($$5.m_150930_(Items.f_42614_)) {
                ++$$2;
                continue;
            }
            return false;
        }
        return !$$3.m_41619_() && $$3.m_41782_() && $$2 > 0;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_43812_) {
        int $$1 = 0;
        ItemStack $$2 = ItemStack.f_41583_;
        for (int $$3 = 0; $$3 < p_43812_.m_6643_(); ++$$3) {
            ItemStack $$4 = p_43812_.m_8020_($$3);
            if ($$4.m_41619_()) continue;
            if ($$4.m_150930_(Items.f_42615_)) {
                if (!$$2.m_41619_()) {
                    return ItemStack.f_41583_;
                }
                $$2 = $$4;
                continue;
            }
            if ($$4.m_150930_(Items.f_42614_)) {
                ++$$1;
                continue;
            }
            return ItemStack.f_41583_;
        }
        if ($$2.m_41619_() || !$$2.m_41782_() || $$1 < 1 || WrittenBookItem.m_43473_($$2) >= 2) {
            return ItemStack.f_41583_;
        }
        ItemStack $$5 = new ItemStack(Items.f_42615_, $$1);
        CompoundTag $$6 = $$2.m_41783_().m_6426_();
        $$6.m_128405_("generation", WrittenBookItem.m_43473_($$2) + 1);
        $$5.m_41751_($$6);
        return $$5;
    }

    @Override
    public NonNullList<ItemStack> m_7457_(CraftingContainer p_43820_) {
        NonNullList<ItemStack> $$1 = NonNullList.m_122780_(p_43820_.m_6643_(), ItemStack.f_41583_);
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            ItemStack $$3 = p_43820_.m_8020_($$2);
            if ($$3.m_41720_().m_41470_()) {
                $$1.set($$2, new ItemStack($$3.m_41720_().m_41469_()));
                continue;
            }
            if (!($$3.m_41720_() instanceof WrittenBookItem)) continue;
            ItemStack $$4 = $$3.m_41777_();
            $$4.m_41764_(1);
            $$1.set($$2, $$4);
            break;
        }
        return $$1;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44079_;
    }

    @Override
    public boolean m_8004_(int p_43804_, int p_43805_) {
        return p_43804_ >= 3 && p_43805_ >= 3;
    }
}

