/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.block.comp;

import ic2.core.IC2;
import ic2.core.block.comp.Components;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.network.GrowingBuffer;
import java.io.DataInput;
import java.io.IOException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;

public abstract class TileEntityComponent {
    protected final Ic2TileEntity parent;

    public TileEntityComponent(Ic2TileEntity ic2TileEntity) {
        this.parent = ic2TileEntity;
    }

    public Ic2TileEntity getParent() {
        return this.parent;
    }

    public void readFromNbt(CompoundTag compoundTag) {
    }

    public CompoundTag writeToNbt() {
        return null;
    }

    public void onLoaded() {
    }

    public void onUnloaded() {
    }

    public void onNeighborChange(Block block, BlockPos blockPos) {
    }

    public void onContainerUpdate(ServerPlayer serverPlayer) {
    }

    public void onNetworkUpdate(DataInput dataInput) throws IOException {
    }

    public boolean enableWorldTick() {
        return false;
    }

    public void onWorldTick() {
    }

    protected void setNetworkUpdate(ServerPlayer serverPlayer, GrowingBuffer growingBuffer) {
        IC2.network.get(true).sendComponentUpdate(this.parent, Components.getId(this.getClass()), serverPlayer, growingBuffer);
    }
}

