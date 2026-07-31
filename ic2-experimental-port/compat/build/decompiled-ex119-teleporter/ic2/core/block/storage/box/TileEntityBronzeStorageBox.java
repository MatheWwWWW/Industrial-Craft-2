/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.box;

import ic2.core.block.storage.box.TileEntityStorageBox;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityBronzeStorageBox
extends TileEntityStorageBox {
    public TileEntityBronzeStorageBox(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.BRONZE_STORAGE_BOX, blockPos, blockState, 45);
    }
}

