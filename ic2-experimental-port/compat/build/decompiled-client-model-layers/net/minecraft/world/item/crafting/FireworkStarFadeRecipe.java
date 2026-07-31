/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class FireworkStarFadeRecipe
extends CustomRecipe {
    private static final Ingredient f_43858_ = Ingredient.m_43929_(Items.f_42689_);

    public FireworkStarFadeRecipe(ResourceLocation p_43861_) {
        super(p_43861_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_43873_, Level p_43874_) {
        boolean $$2 = false;
        boolean $$3 = false;
        for (int $$4 = 0; $$4 < p_43873_.m_6643_(); ++$$4) {
            ItemStack $$5 = p_43873_.m_8020_($$4);
            if ($$5.m_41619_()) continue;
            if ($$5.m_41720_() instanceof DyeItem) {
                $$2 = true;
                continue;
            }
            if (f_43858_.test($$5)) {
                if ($$3) {
                    return false;
                }
                $$3 = true;
                continue;
            }
            return false;
        }
        return $$3 && $$2;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_43871_) {
        ArrayList $$1 = Lists.newArrayList();
        ItemStack $$2 = null;
        for (int $$3 = 0; $$3 < p_43871_.m_6643_(); ++$$3) {
            ItemStack $$4 = p_43871_.m_8020_($$3);
            Item $$5 = $$4.m_41720_();
            if ($$5 instanceof DyeItem) {
                $$1.add(((DyeItem)$$5).m_41089_().m_41070_());
                continue;
            }
            if (!f_43858_.test($$4)) continue;
            $$2 = $$4.m_41777_();
            $$2.m_41764_(1);
        }
        if ($$2 == null || $$1.isEmpty()) {
            return ItemStack.f_41583_;
        }
        $$2.m_41698_("Explosion").m_128408_("FadeColors", $$1);
        return $$2;
    }

    @Override
    public boolean m_8004_(int p_43863_, int p_43864_) {
        return p_43863_ * p_43864_ >= 2;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44084_;
    }
}

