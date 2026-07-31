/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.platform.recipes.mods;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public interface IRecipeModifier {
    public void reset();

    public boolean isSlotValid(ItemStack var1);

    public boolean isOutputItem(ItemStack var1);

    public ItemStack applyChanges(ItemStack var1, boolean var2);

    public void serialize(FriendlyByteBuf var1);

    public JsonObject serialize();
}

