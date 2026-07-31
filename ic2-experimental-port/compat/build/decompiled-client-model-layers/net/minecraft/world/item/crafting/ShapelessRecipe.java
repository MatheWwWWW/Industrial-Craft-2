/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.world.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class ShapelessRecipe
implements CraftingRecipe {
    private final ResourceLocation f_44241_;
    final String f_44242_;
    final ItemStack f_44243_;
    final NonNullList<Ingredient> f_44244_;

    public ShapelessRecipe(ResourceLocation p_44246_, String p_44247_, ItemStack p_44248_, NonNullList<Ingredient> p_44249_) {
        this.f_44241_ = p_44246_;
        this.f_44242_ = p_44247_;
        this.f_44243_ = p_44248_;
        this.f_44244_ = p_44249_;
    }

    @Override
    public ResourceLocation m_6423_() {
        return this.f_44241_;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44077_;
    }

    @Override
    public String m_6076_() {
        return this.f_44242_;
    }

    @Override
    public ItemStack m_8043_() {
        return this.f_44243_;
    }

    @Override
    public NonNullList<Ingredient> m_7527_() {
        return this.f_44244_;
    }

    @Override
    public boolean m_5818_(CraftingContainer p_44262_, Level p_44263_) {
        StackedContents $$2 = new StackedContents();
        int $$3 = 0;
        for (int $$4 = 0; $$4 < p_44262_.m_6643_(); ++$$4) {
            ItemStack $$5 = p_44262_.m_8020_($$4);
            if ($$5.m_41619_()) continue;
            ++$$3;
            $$2.m_36468_($$5, 1);
        }
        return $$3 == this.f_44244_.size() && $$2.m_36475_(this, null);
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_44260_) {
        return this.f_44243_.m_41777_();
    }

    @Override
    public boolean m_8004_(int p_44252_, int p_44253_) {
        return p_44252_ * p_44253_ >= this.f_44244_.size();
    }

    public static class Serializer
    implements RecipeSerializer<ShapelessRecipe> {
        @Override
        public ShapelessRecipe m_6729_(ResourceLocation p_44290_, JsonObject p_44291_) {
            String $$2 = GsonHelper.m_13851_(p_44291_, "group", "");
            NonNullList<Ingredient> $$3 = Serializer.m_44275_(GsonHelper.m_13933_(p_44291_, "ingredients"));
            if ($$3.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            }
            if ($$3.size() > 9) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            }
            ItemStack $$4 = ShapedRecipe.m_151274_(GsonHelper.m_13930_(p_44291_, "result"));
            return new ShapelessRecipe(p_44290_, $$2, $$4, $$3);
        }

        private static NonNullList<Ingredient> m_44275_(JsonArray p_44276_) {
            NonNullList<Ingredient> $$1 = NonNullList.m_122779_();
            for (int $$2 = 0; $$2 < p_44276_.size(); ++$$2) {
                Ingredient $$3 = Ingredient.m_43917_(p_44276_.get($$2));
                if ($$3.m_43947_()) continue;
                $$1.add($$3);
            }
            return $$1;
        }

        @Override
        public ShapelessRecipe m_8005_(ResourceLocation p_44293_, FriendlyByteBuf p_44294_) {
            String $$2 = p_44294_.m_130277_();
            int $$3 = p_44294_.m_130242_();
            NonNullList<Ingredient> $$4 = NonNullList.m_122780_($$3, Ingredient.f_43901_);
            for (int $$5 = 0; $$5 < $$4.size(); ++$$5) {
                $$4.set($$5, Ingredient.m_43940_(p_44294_));
            }
            ItemStack $$6 = p_44294_.m_130267_();
            return new ShapelessRecipe(p_44293_, $$2, $$6, $$4);
        }

        @Override
        public void m_6178_(FriendlyByteBuf p_44281_, ShapelessRecipe p_44282_) {
            p_44281_.m_130070_(p_44282_.f_44242_);
            p_44281_.m_130130_(p_44282_.f_44244_.size());
            for (Ingredient $$2 : p_44282_.f_44244_) {
                $$2.m_43923_(p_44281_);
            }
            p_44281_.m_130055_(p_44282_.f_44243_);
        }

        @Override
        public /* synthetic */ Recipe m_8005_(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return this.m_8005_(resourceLocation, friendlyByteBuf);
        }

        @Override
        public /* synthetic */ Recipe m_6729_(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return this.m_6729_(resourceLocation, jsonObject);
        }
    }
}

