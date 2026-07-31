/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHitSoundOverride {
    @SideOnly(value=Side.CLIENT)
    public String getHitSoundForBlock(EntityPlayerSP var1, World var2, BlockPos var3, ItemStack var4);

    @SideOnly(value=Side.CLIENT)
    public String getBreakSoundForBlock(EntityPlayerSP var1, World var2, BlockPos var3, ItemStack var4);
}

