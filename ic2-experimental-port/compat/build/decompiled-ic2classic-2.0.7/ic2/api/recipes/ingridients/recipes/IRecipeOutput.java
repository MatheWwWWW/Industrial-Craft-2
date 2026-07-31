/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nonnull
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.ingridients.recipes;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public interface IRecipeOutput {
    public static final CompoundTag EMPTY_COMPOUND = new CompoundTag();

    public List<ItemStack> onRecipeProcessed(RandomSource var1, CompoundTag var2, CompoundTag var3);

    default public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags, IRecipeOverride overrides) {
        return this.onRecipeProcessed(rand, persistentData, recipeFlags);
    }

    public List<ItemStack> getAllOutputs();

    @Nonnull
    public CompoundTag getMetadata();

    public float getExperience();

    public void serialize(FriendlyByteBuf var1);

    public JsonObject serialize();

    public static List<ItemStack> copyItems(List<ItemStack> copy) {
        ObjectArrayList list = new ObjectArrayList();
        for (ItemStack stack : copy) {
            list.add(stack.m_41777_());
        }
        return list;
    }

    public static interface IRecipeOverride {
        public float getChance(float var1);
    }
}

