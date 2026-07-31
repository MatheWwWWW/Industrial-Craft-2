/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITerraformingBP {
    public double getConsume(ItemStack var1);

    public int getRange(ItemStack var1);

    public boolean canInsert(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4);

    public boolean terraform(ItemStack var1, World var2, BlockPos var3);
}

