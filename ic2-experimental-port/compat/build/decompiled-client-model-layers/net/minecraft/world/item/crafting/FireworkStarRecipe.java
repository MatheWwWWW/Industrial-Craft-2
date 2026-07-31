/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class FireworkStarRecipe
extends CustomRecipe {
    private static final Ingredient f_43876_ = Ingredient.m_43929_(Items.f_42613_, Items.f_42402_, Items.f_42587_, Items.f_42678_, Items.f_42679_, Items.f_42682_, Items.f_42680_, Items.f_42683_, Items.f_42681_);
    private static final Ingredient f_43877_ = Ingredient.m_43929_(Items.f_42415_);
    private static final Ingredient f_43878_ = Ingredient.m_43929_(Items.f_42525_);
    private static final Map<Item, FireworkRocketItem.Shape> f_43879_ = Util.m_137469_(Maps.newHashMap(), p_43898_ -> {
        p_43898_.put(Items.f_42613_, FireworkRocketItem.Shape.LARGE_BALL);
        p_43898_.put(Items.f_42402_, FireworkRocketItem.Shape.BURST);
        p_43898_.put(Items.f_42587_, FireworkRocketItem.Shape.STAR);
        p_43898_.put(Items.f_42678_, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.f_42679_, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.f_42682_, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.f_42680_, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.f_42683_, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.f_42681_, FireworkRocketItem.Shape.CREEPER);
    });
    private static final Ingredient f_43880_ = Ingredient.m_43929_(Items.f_42403_);

    public FireworkStarRecipe(ResourceLocation p_43883_) {
        super(p_43883_);
    }

    @Override
    public boolean m_5818_(CraftingContainer p_43895_, Level p_43896_) {
        boolean $$2 = false;
        boolean $$3 = false;
        boolean $$4 = false;
        boolean $$5 = false;
        boolean $$6 = false;
        for (int $$7 = 0; $$7 < p_43895_.m_6643_(); ++$$7) {
            ItemStack $$8 = p_43895_.m_8020_($$7);
            if ($$8.m_41619_()) continue;
            if (f_43876_.test($$8)) {
                if ($$4) {
                    return false;
                }
                $$4 = true;
                continue;
            }
            if (f_43878_.test($$8)) {
                if ($$6) {
                    return false;
                }
                $$6 = true;
                continue;
            }
            if (f_43877_.test($$8)) {
                if ($$5) {
                    return false;
                }
                $$5 = true;
                continue;
            }
            if (f_43880_.test($$8)) {
                if ($$2) {
                    return false;
                }
                $$2 = true;
                continue;
            }
            if ($$8.m_41720_() instanceof DyeItem) {
                $$3 = true;
                continue;
            }
            return false;
        }
        return $$2 && $$3;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_43893_) {
        ItemStack $$1 = new ItemStack(Items.f_42689_);
        CompoundTag $$2 = $$1.m_41698_("Explosion");
        FireworkRocketItem.Shape $$3 = FireworkRocketItem.Shape.SMALL_BALL;
        ArrayList $$4 = Lists.newArrayList();
        for (int $$5 = 0; $$5 < p_43893_.m_6643_(); ++$$5) {
            ItemStack $$6 = p_43893_.m_8020_($$5);
            if ($$6.m_41619_()) continue;
            if (f_43876_.test($$6)) {
                $$3 = f_43879_.get($$6.m_41720_());
                continue;
            }
            if (f_43878_.test($$6)) {
                $$2.m_128379_("Flicker", true);
                continue;
            }
            if (f_43877_.test($$6)) {
                $$2.m_128379_("Trail", true);
                continue;
            }
            if (!($$6.m_41720_() instanceof DyeItem)) continue;
            $$4.add(((DyeItem)$$6.m_41720_()).m_41089_().m_41070_());
        }
        $$2.m_128408_("Colors", $$4);
        $$2.m_128344_("Type", (byte)$$3.m_41236_());
        return $$1;
    }

    @Override
    public boolean m_8004_(int p_43885_, int p_43886_) {
        return p_43885_ * p_43886_ >= 2;
    }

    @Override
    public ItemStack m_8043_() {
        return new ItemStack(Items.f_42689_);
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44083_;
    }
}

