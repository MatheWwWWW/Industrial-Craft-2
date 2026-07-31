/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.api.tile;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchable {
    public EnumFacing getFacing(World var1, BlockPos var2);

    default public boolean canSetFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
        return true;
    }

    public boolean setFacing(World var1, BlockPos var2, EnumFacing var3, EntityPlayer var4);

    public boolean wrenchCanRemove(World var1, BlockPos var2, EntityPlayer var3);

    public List<ItemStack> getWrenchDrops(World var1, BlockPos var2, IBlockState var3, TileEntity var4, EntityPlayer var5, int var6);
}

