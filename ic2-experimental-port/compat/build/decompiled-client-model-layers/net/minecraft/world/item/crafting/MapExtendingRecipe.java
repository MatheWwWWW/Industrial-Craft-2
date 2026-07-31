/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapExtendingRecipe
extends ShapedRecipe {
    public MapExtendingRecipe(ResourceLocation p_43984_) {
        super(p_43984_, "", 3, 3, NonNullList.m_122783_(Ingredient.f_43901_, Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42573_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_)), new ItemStack(Items.f_42676_));
    }

    @Override
    public boolean m_5818_(CraftingContainer p_43993_, Level p_43994_) {
        if (!super.m_5818_(p_43993_, p_43994_)) {
            return false;
        }
        ItemStack $$2 = ItemStack.f_41583_;
        for (int $$3 = 0; $$3 < p_43993_.m_6643_() && $$2.m_41619_(); ++$$3) {
            ItemStack $$4 = p_43993_.m_8020_($$3);
            if (!$$4.m_150930_(Items.f_42573_)) continue;
            $$2 = $$4;
        }
        if ($$2.m_41619_()) {
            return false;
        }
        MapItemSavedData $$5 = MapItem.m_42853_($$2, p_43994_);
        if ($$5 == null) {
            return false;
        }
        if ($$5.m_164810_()) {
            return false;
        }
        return $$5.f_77890_ < 4;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_43991_) {
        ItemStack $$1 = ItemStack.f_41583_;
        for (int $$2 = 0; $$2 < p_43991_.m_6643_() && $$1.m_41619_(); ++$$2) {
            ItemStack $$3 = p_43991_.m_8020_($$2);
            if (!$$3.m_150930_(Items.f_42573_)) continue;
            $$1 = $$3;
        }
        $$1 = $$1.m_41777_();
        $$1.m_41764_(1);
        $$1.m_41784_().m_128405_("map_scale_direction", 1);
        return $$1;
    }

    @Override
    public boolean m_5598_() {
        return true;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44081_;
    }
}

