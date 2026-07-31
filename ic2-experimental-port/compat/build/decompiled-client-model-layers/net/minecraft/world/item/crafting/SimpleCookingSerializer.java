/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.world.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleCookingSerializer<T extends AbstractCookingRecipe>
implements RecipeSerializer<T> {
    private final int f_44327_;
    private final CookieBaker<T> f_44328_;

    public SimpleCookingSerializer(CookieBaker<T> p_44330_, int p_44331_) {
        this.f_44327_ = p_44331_;
        this.f_44328_ = p_44330_;
    }

    @Override
    public T m_6729_(ResourceLocation p_44347_, JsonObject p_44348_) {
        String $$2 = GsonHelper.m_13851_(p_44348_, "group", "");
        JsonArray $$3 = GsonHelper.m_13885_(p_44348_, "ingredient") ? GsonHelper.m_13933_(p_44348_, "ingredient") : GsonHelper.m_13930_(p_44348_, "ingredient");
        Ingredient $$4 = Ingredient.m_43917_((JsonElement)$$3);
        String $$5 = GsonHelper.m_13906_(p_44348_, "result");
        ResourceLocation $$6 = new ResourceLocation($$5);
        ItemStack $$7 = new ItemStack(Registry.f_122827_.m_6612_($$6).orElseThrow(() -> new IllegalStateException("Item: " + $$5 + " does not exist")));
        float $$8 = GsonHelper.m_13820_(p_44348_, "experience", 0.0f);
        int $$9 = GsonHelper.m_13824_(p_44348_, "cookingtime", this.f_44327_);
        return this.f_44328_.m_44352_(p_44347_, $$2, $$4, $$7, $$8, $$9);
    }

    @Override
    public T m_8005_(ResourceLocation p_44350_, FriendlyByteBuf p_44351_) {
        String $$2 = p_44351_.m_130277_();
        Ingredient $$3 = Ingredient.m_43940_(p_44351_);
        ItemStack $$4 = p_44351_.m_130267_();
        float $$5 = p_44351_.readFloat();
        int $$6 = p_44351_.m_130242_();
        return this.f_44328_.m_44352_(p_44350_, $$2, $$3, $$4, $$5, $$6);
    }

    @Override
    public void m_6178_(FriendlyByteBuf p_44335_, T p_44336_) {
        p_44335_.m_130070_(((AbstractCookingRecipe)p_44336_).f_43728_);
        ((AbstractCookingRecipe)p_44336_).f_43729_.m_43923_(p_44335_);
        p_44335_.m_130055_(((AbstractCookingRecipe)p_44336_).f_43730_);
        p_44335_.writeFloat(((AbstractCookingRecipe)p_44336_).f_43731_);
        p_44335_.m_130130_(((AbstractCookingRecipe)p_44336_).f_43732_);
    }

    @Override
    public /* synthetic */ Recipe m_8005_(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        return this.m_8005_(resourceLocation, friendlyByteBuf);
    }

    @Override
    public /* synthetic */ Recipe m_6729_(ResourceLocation resourceLocation, JsonObject jsonObject) {
        return this.m_6729_(resourceLocation, jsonObject);
    }

    static interface CookieBaker<T extends AbstractCookingRecipe> {
        public T m_44352_(ResourceLocation var1, String var2, Ingredient var3, ItemStack var4, float var5, int var6);
    }
}

