/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SwitchTubeTileEntity
extends TubeTileEntity
implements ITileActivityProvider {
    public SwitchTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("isActive");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.SWITCH_TUBE;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.self.setActiveDirections(this.isActive() ? DirectionList.EMPTY : DirectionList.ALL.remove(DirectionList.ofNumber(this.anchors)));
    }

    @Override
    public void onTubeUpdate() {
        this.handleRedstone();
        if (this.setActive(this.isRedstonePowered())) {
            this.self.setActiveDirections(this.isActive() ? DirectionList.EMPTY : DirectionList.ALL.remove(DirectionList.ofNumber(this.anchors)));
            this.notifyChanges(true, DirectionList.ALL);
        }
    }

    @Override
    public boolean canAddItem(TransportedItem item, Direction side) {
        return !this.isActive();
    }

    @Override
    protected void onCachesUpdated() {
        if (this.isActive()) {
            this.connectivity = 0;
            this.updateTileField("connectivity");
            return;
        }
        super.onCachesUpdated();
    }
}

