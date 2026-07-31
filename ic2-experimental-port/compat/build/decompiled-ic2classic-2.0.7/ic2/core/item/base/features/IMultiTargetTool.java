/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.base.features;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IMultiTargetTool {
    public boolean isMultiMining(ItemStack var1);

    public boolean canMultiMine(ItemStack var1);

    public Iterator<BlockPos> getHitPositions(ItemStack var1, Player var2, BlockPos var3, Direction var4);
}

