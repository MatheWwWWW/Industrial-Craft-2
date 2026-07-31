/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.api.items;

import ic2.api.tiles.ITerraformer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ITerraformerBP {
    public boolean canInsert(ItemStack var1, Player var2, Level var3, BlockPos var4);

    public void onInsert(ItemStack var1, Player var2, Level var3, BlockPos var4);

    public boolean isRandomized(ItemStack var1);

    public int getEnergyUsage(ItemStack var1);

    public int getRadius(ItemStack var1);

    public boolean terraform(ItemStack var1, Level var2, BlockPos var3, ITerraformer var4);
}

