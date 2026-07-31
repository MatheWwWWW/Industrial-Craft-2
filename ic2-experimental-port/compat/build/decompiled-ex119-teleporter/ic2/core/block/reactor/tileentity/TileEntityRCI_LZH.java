/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.reactor.tileentity;

import ic2.core.block.reactor.tileentity.TileEntityAbstractRCI;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityRCI_LZH
extends TileEntityAbstractRCI {
    public TileEntityRCI_LZH(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.RCI_LZH, blockPos, blockState, new ItemStack((ItemLike)Ic2Items.LZH_CONDENSATOR), new ItemStack((ItemLike)Blocks.f_50060_));
    }
}

