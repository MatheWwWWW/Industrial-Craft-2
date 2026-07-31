/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item;

import ic2.core.IHasGui;
import ic2.core.item.IHandHeldInventory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IHandHeldSubInventory
extends IHandHeldInventory {
    public IHasGui getSubInventory(Player var1, InteractionHand var2, ItemStack var3, int var4);
}

