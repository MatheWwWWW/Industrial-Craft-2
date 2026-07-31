/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.transport.item.tubes;

import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TransportTubeTileEntity
extends TubeTileEntity {
    public TransportTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.tubes);
    }

    @Override
    public boolean useDefaultCache() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TRANSPORT_TUBE;
    }
}

