/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.rendering.camouflage;

import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.INetworkDataBuffer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public interface IQuadCache
extends INetworkDataBuffer {
    public CompoundTag write(CompoundTag var1);

    public void read(CompoundTag var1);

    public BlockState getDisplayBlock();

    public int[] getColors();

    public RetextureEvent.Rotation[] getRotations();

    public Direction getSide();
}

