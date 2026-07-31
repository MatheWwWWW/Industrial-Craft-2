/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.transport.cover;

import ic2.core.block.transport.cover.CoverProperty;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ICoverHolder {
    public Set<CoverProperty> getCoverProperties();

    public boolean canPlaceCover(Level var1, BlockPos var2, Direction var3, ItemStack var4);

    public void placeCover(Level var1, BlockPos var2, Direction var3, ItemStack var4);

    public boolean canRemoveCover(Level var1, BlockPos var2, Direction var3);

    public void removeCover(Level var1, BlockPos var2, Direction var3);
}

