/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.ingridients.recipes;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.ingridients.recipes.SimpleRecipeOutput;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class SawMillOutput
extends SimpleRecipeOutput {
    public static final String RECIPE_FLAG = "saw_upgrade";

    public SawMillOutput(JsonObject obj) {
        super(obj);
    }

    public SawMillOutput(FriendlyByteBuf buffer) {
        super(buffer);
    }

    public SawMillOutput(List<ItemStack> outputs) {
        super(outputs);
    }

    public SawMillOutput(List<ItemStack> outputs, float xp) {
        super(outputs, xp);
    }

    public SawMillOutput(List<ItemStack> outputs, CompoundTag nbt, float xp) {
        super(outputs, nbt, xp);
    }

    @Override
    public List<ItemStack> onRecipeProcessed(RandomSource rand, CompoundTag persistentData, CompoundTag recipeFlags) {
        List<ItemStack> list = IRecipeOutput.copyItems(this.outputs);
        int effect = recipeFlags.m_128451_(RECIPE_FLAG);
        if (effect != 0) {
            int m = list.size();
            for (int i = 0; i < m; ++i) {
                list.get(i).m_41769_(effect);
            }
        }
        return list;
    }
}

