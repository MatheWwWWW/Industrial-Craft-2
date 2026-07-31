/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.world.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class SingleItemRecipe
implements Recipe<Container> {
    protected final Ingredient f_44409_;
    protected final ItemStack f_44410_;
    private final RecipeType<?> f_44413_;
    private final RecipeSerializer<?> f_44414_;
    protected final ResourceLocation f_44411_;
    protected final String f_44412_;

    public SingleItemRecipe(RecipeType<?> p_44416_, RecipeSerializer<?> p_44417_, ResourceLocation p_44418_, String p_44419_, Ingredient p_44420_, ItemStack p_44421_) {
        this.f_44413_ = p_44416_;
        this.f_44414_ = p_44417_;
        this.f_44411_ = p_44418_;
        this.f_44412_ = p_44419_;
        this.f_44409_ = p_44420_;
        this.f_44410_ = p_44421_;
    }

    @Override
    public RecipeType<?> m_6671_() {
        return this.f_44413_;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return this.f_44414_;
    }

    @Override
    public ResourceLocation m_6423_() {
        return this.f_44411_;
    }

    @Override
    public String m_6076_() {
        return this.f_44412_;
    }

    @Override
    public ItemStack m_8043_() {
        return this.f_44410_;
    }

    @Override
    public NonNullList<Ingredient> m_7527_() {
        NonNullList<Ingredient> $$0 = NonNullList.m_122779_();
        $$0.add(this.f_44409_);
        return $$0;
    }

    @Override
    public boolean m_8004_(int p_44424_, int p_44425_) {
        return true;
    }

    @Override
    public ItemStack m_5874_(Container p_44427_) {
        return this.f_44410_.m_41777_();
    }

    public static class Serializer<T extends SingleItemRecipe>
    implements RecipeSerializer<T> {
        final SingleItemMaker<T> f_44433_;

        protected Serializer(SingleItemMaker<T> p_44435_) {
            this.f_44433_ = p_44435_;
        }

        @Override
        public T m_6729_(ResourceLocation p_44449_, JsonObject p_44450_) {
            Ingredient $$4;
            String $$2 = GsonHelper.m_13851_(p_44450_, "group", "");
            if (GsonHelper.m_13885_(p_44450_, "ingredient")) {
                Ingredient $$3 = Ingredient.m_43917_((JsonElement)GsonHelper.m_13933_(p_44450_, "ingredient"));
            } else {
                $$4 = Ingredient.m_43917_((JsonElement)GsonHelper.m_13930_(p_44450_, "ingredient"));
            }
            String $$5 = GsonHelper.m_13906_(p_44450_, "result");
            int $$6 = GsonHelper.m_13927_(p_44450_, "count");
            ItemStack $$7 = new ItemStack(Registry.f_122827_.m_7745_(new ResourceLocation($$5)), $$6);
            return this.f_44433_.m_44454_(p_44449_, $$2, $$4, $$7);
        }

        @Override
        public T m_8005_(ResourceLocation p_44452_, FriendlyByteBuf p_44453_) {
            String $$2 = p_44453_.m_130277_();
            Ingredient $$3 = Ingredient.m_43940_(p_44453_);
            ItemStack $$4 = p_44453_.m_130267_();
            return this.f_44433_.m_44454_(p_44452_, $$2, $$3, $$4);
        }

        @Override
        public void m_6178_(FriendlyByteBuf p_44440_, T p_44441_) {
            p_44440_.m_130070_(((SingleItemRecipe)p_44441_).f_44412_);
            ((SingleItemRecipe)p_44441_).f_44409_.m_43923_(p_44440_);
            p_44440_.m_130055_(((SingleItemRecipe)p_44441_).f_44410_);
        }

        @Override
        public /* synthetic */ Recipe m_8005_(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return this.m_8005_(resourceLocation, friendlyByteBuf);
        }

        @Override
        public /* synthetic */ Recipe m_6729_(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return this.m_6729_(resourceLocation, jsonObject);
        }

        static interface SingleItemMaker<T extends SingleItemRecipe> {
            public T m_44454_(ResourceLocation var1, String var2, Ingredient var3, ItemStack var4);
        }
    }
}

