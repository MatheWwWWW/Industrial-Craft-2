/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.tile.PacketRange;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DroppingTubeTileEntity
extends TubeTileEntity {
    public static final double SPEED = 6.0;
    public static final double ACTUAL_SPEED = 0.44999999999999996;

    public DroppingTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("facing");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.DROPPING_TUBE;
    }

    @Override
    protected boolean isRotatable() {
        return true;
    }

    @Override
    public boolean canSetFacingInternal(Direction dir) {
        return dir != this.getFacing() && (this.connectivity & 1 << dir.m_122411_()) == 0;
    }

    @Override
    public boolean onCenterReached(TransportedItem item) {
        if (this.isRendering()) {
            return true;
        }
        this.onItemLost(item, true);
        this.sendToClient(1, item.getId(), PacketRange.CHUNK_TRACKED);
        Direction dir = this.getFacing();
        Vec3 pos = Vec3.m_82512_((Vec3i)this.m_58899_()).m_82549_(Vec3.m_82528_((Vec3i)dir.m_122436_()).m_82542_(0.75, 0.75, 0.75));
        ItemEntity entity = new ItemEntity(this.m_58904_(), pos.m_7096_(), pos.m_7098_(), pos.m_7094_(), item.getStack());
        entity.m_20256_(Vec3.m_82528_((Vec3i)dir.m_122436_()).m_82542_(0.44999999999999996, 0.44999999999999996, 0.44999999999999996));
        this.itemsToDelete.add((Object)item);
        this.f_58857_.m_7967_((Entity)entity);
        return true;
    }
}

