/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class FireworkRocketRecipe
extends CustomRecipe {
    private static final Ingredient f_43837_ = Ingredient.m_43929_(Items.f_42516_);
    private static final Ingredient f_43838_ = Ingredient.m_43929_(Items.f_42403_);
    private static final Ingredient f_43839_ = Ingredient.m_43929_(Items.f_42689_);

    public FireworkRocketRecipe(ResourceLocation p_43842_) {
        super(p_43842_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_43854_, Level p_43855_) {
        boolean $$2 = false;
        int $$3 = 0;
        for (int $$4 = 0; $$4 < p_43854_.m_6643_(); ++$$4) {
            ItemStack $$5 = p_43854_.m_8020_($$4);
            if ($$5.m_41619_()) continue;
            if (f_43837_.test($$5)) {
                if ($$2) {
                    return false;
                }
                $$2 = true;
                continue;
            }
            if (!(f_43838_.test($$5) ? ++$$3 > 3 : !f_43839_.test($$5))) continue;
            return false;
        }
        return $$2 && $$3 >= 1;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_43852_) {
        ItemStack $$1 = new ItemStack(Items.f_42688_, 3);
        CompoundTag $$2 = $$1.m_41698_("Fireworks");
        ListTag $$3 = new ListTag();
        int $$4 = 0;
        for (int $$5 = 0; $$5 < p_43852_.m_6643_(); ++$$5) {
            CompoundTag $$7;
            ItemStack $$6 = p_43852_.m_8020_($$5);
            if ($$6.m_41619_()) continue;
            if (f_43838_.test($$6)) {
                ++$$4;
                continue;
            }
            if (!f_43839_.test($$6) || ($$7 = $$6.m_41737_("Explosion")) == null) continue;
            $$3.add($$7);
        }
        $$2.m_128344_("Flight", (byte)$$4);
        if (!$$3.isEmpty()) {
            $$2.m_128365_("Explosions", $$3);
        }
        return $$1;
    }

    @Override
    public boolean m_8004_(int p_43844_, int p_43845_) {
        return p_43844_ * p_43845_ >= 2;
    }

    @Override
    public ItemStack m_8043_() {
        return new ItemStack(Items.f_42688_);
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44082_;
    }
}

