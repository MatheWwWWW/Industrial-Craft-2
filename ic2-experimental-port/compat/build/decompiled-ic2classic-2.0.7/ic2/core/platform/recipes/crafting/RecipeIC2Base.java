/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeSerializer
 */
package ic2.core.platform.recipes.crafting;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.crafting.IC2RecipeSerializer;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public abstract class RecipeIC2Base
implements CraftingRecipe {
    protected ResourceLocation id;
    protected ItemStack output;
    protected IRecipeModifier mod;
    public boolean hidden;
    protected boolean consume;
    String group;

    public RecipeIC2Base(ResourceLocation id, ItemStack output, boolean hidden, boolean consume, IRecipeModifier mod) {
        this.id = id;
        this.output = output;
        this.mod = mod;
        this.hidden = hidden;
        this.consume = consume;
        this.group = output.m_41778_();
    }

    public RecipeIC2Base setCustomGroup(String group) {
        this.group = group;
        return this;
    }

    public String m_6076_() {
        return this.group;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public ItemStack m_8043_() {
        if (this.mod != null) {
            return this.mod.applyChanges(this.output, true);
        }
        return this.output.m_41777_();
    }

    public ResourceLocation m_6423_() {
        return this.id;
    }

    public RecipeSerializer<?> m_7707_() {
        return IC2RecipeSerializer.INSTANCE;
    }

    public boolean canBeDrained(ItemStack stack) {
        return !this.consume && stack.hasCraftingRemainingItem();
    }

    public abstract ResourceLocation getMetaSerializer();

    public abstract void serialize(FriendlyByteBuf var1);

    public abstract JsonObject serialize();

    public abstract NonNullList<Ingredient> m_7527_();
}

