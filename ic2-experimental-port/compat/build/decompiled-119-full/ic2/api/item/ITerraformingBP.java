/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.api.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ITerraformingBP {
    public double getConsume(ItemStack var1);

    public int getRange(ItemStack var1);

    public boolean canInsert(ItemStack var1, Player var2, Level var3, BlockPos var4);

    public boolean terraform(ItemStack var1, Level var2, BlockPos var3);
}

