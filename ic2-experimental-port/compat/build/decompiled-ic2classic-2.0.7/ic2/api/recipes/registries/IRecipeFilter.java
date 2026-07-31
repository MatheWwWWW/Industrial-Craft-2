/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.api.recipes.registries;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface IRecipeFilter {
    public static final int SIMPLE = 1;
    public static final int FILTER = 2;
    public static final int BOTH = 3;

    public void add(ItemLike ... var1);

    public void add(IInput ... var1);

    public void remove(ItemStack var1, int var2);

    public boolean contains(ItemStack var1);

    public List<ItemStack> getFilteredItems();

    public void clear();

    public JsonObject serialize();

    public void read(JsonObject var1);
}

