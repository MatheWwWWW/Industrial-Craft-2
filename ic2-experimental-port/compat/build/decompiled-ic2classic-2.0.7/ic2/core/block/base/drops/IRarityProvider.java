/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 */
package ic2.core.block.base.drops;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public interface IRarityProvider {
    public Rarity getRarity(ItemStack var1);
}

