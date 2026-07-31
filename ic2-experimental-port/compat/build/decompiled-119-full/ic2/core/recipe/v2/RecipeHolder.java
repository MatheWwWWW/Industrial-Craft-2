/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.Level
 */
package ic2.core.recipe.v2;

import ic2.api.recipe.MachineRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record RecipeHolder<I, O>(MachineRecipe<I, O> recipe, ResourceLocation id, RecipeSerializer<?> serializer, RecipeType<?> type) implements Recipe<Container>
{
    public boolean m_5818_(Container container, Level level) {
        throw new UnsupportedOperationException("Not supported for IC2 machine recipes.");
    }

    public ItemStack m_5874_(Container container) {
        throw new UnsupportedOperationException("Not supported for IC2 machine recipes.");
    }

    public boolean m_8004_(int n, int n2) {
        throw new UnsupportedOperationException("Not supported for IC2 machine recipes.");
    }

    public ItemStack m_8043_() {
        return ItemStack.f_41583_;
    }

    public ResourceLocation m_6423_() {
        return this.id;
    }

    public RecipeSerializer<?> m_7707_() {
        return this.serializer;
    }

    public RecipeType<?> m_6671_() {
        return this.type;
    }
}

