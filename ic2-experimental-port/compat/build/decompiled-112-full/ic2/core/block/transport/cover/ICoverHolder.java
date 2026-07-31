/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.block.transport.cover;

import ic2.core.block.transport.cover.CoverProperty;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICoverHolder {
    public Set<CoverProperty> getCoverProperties();

    public boolean canPlaceCover(World var1, BlockPos var2, EnumFacing var3, ItemStack var4);

    public void placeCover(World var1, BlockPos var2, EnumFacing var3, ItemStack var4);

    public boolean canRemoveCover(World var1, BlockPos var2, EnumFacing var3);

    public void removeCover(World var1, BlockPos var2, EnumFacing var3);
}

