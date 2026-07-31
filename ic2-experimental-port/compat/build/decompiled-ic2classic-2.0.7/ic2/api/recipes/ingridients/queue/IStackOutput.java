/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.ingridients.queue;

import ic2.api.recipes.ingridients.queue.IInputter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IStackOutput {
    public boolean addToInventory(IInputter var1);

    public ItemStack getStack();

    public void save(CompoundTag var1);
}

