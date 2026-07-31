/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.api.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface IRepairable
extends ItemLike {
    public boolean repairDamage(ItemStack var1, int var2);
}

