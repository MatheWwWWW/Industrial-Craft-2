/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.api.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IAutoEatable {
    public boolean canAutoEat(ItemStack var1);

    public int getFoodValue(ItemStack var1);

    public ItemStack onEaten(ItemStack var1, Level var2, Player var3);
}

