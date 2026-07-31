/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ShieldDecorationRecipe
extends CustomRecipe {
    public ShieldDecorationRecipe(ResourceLocation p_44296_) {
        super(p_44296_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_44308_, Level p_44309_) {
        ItemStack $$2 = ItemStack.f_41583_;
        ItemStack $$3 = ItemStack.f_41583_;
        for (int $$4 = 0; $$4 < p_44308_.m_6643_(); ++$$4) {
            ItemStack $$5 = p_44308_.m_8020_($$4);
            if ($$5.m_41619_()) continue;
            if ($$5.m_41720_() instanceof BannerItem) {
                if (!$$3.m_41619_()) {
                    return false;
                }
                $$3 = $$5;
                continue;
            }
            if ($$5.m_150930_(Items.f_42740_)) {
                if (!$$2.m_41619_()) {
                    return false;
                }
                if (BlockItem.m_186336_($$5) != null) {
                    return false;
                }
                $$2 = $$5;
                continue;
            }
            return false;
        }
        return !$$2.m_41619_() && !$$3.m_41619_();
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_44306_) {
        ItemStack $$1 = ItemStack.f_41583_;
        ItemStack $$2 = ItemStack.f_41583_;
        for (int $$3 = 0; $$3 < p_44306_.m_6643_(); ++$$3) {
            ItemStack $$4 = p_44306_.m_8020_($$3);
            if ($$4.m_41619_()) continue;
            if ($$4.m_41720_() instanceof BannerItem) {
                $$1 = $$4;
                continue;
            }
            if (!$$4.m_150930_(Items.f_42740_)) continue;
            $$2 = $$4.m_41777_();
        }
        if ($$2.m_41619_()) {
            return $$2;
        }
        CompoundTag $$5 = BlockItem.m_186336_($$1);
        CompoundTag $$6 = $$5 == null ? new CompoundTag() : $$5.m_6426_();
        $$6.m_128405_("Base", ((BannerItem)$$1.m_41720_()).m_40545_().m_41060_());
        BlockItem.m_186338_($$2, BlockEntityType.f_58935_, $$6);
        return $$2;
    }

    @Override
    public boolean m_8004_(int p_44298_, int p_44299_) {
        return p_44298_ * p_44299_ >= 2;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44087_;
    }
}

