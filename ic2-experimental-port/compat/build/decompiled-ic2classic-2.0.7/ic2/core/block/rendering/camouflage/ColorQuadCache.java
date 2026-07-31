/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.rendering.camouflage;

import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.rendering.camouflage.IQuadCache;
import ic2.core.platform.registries.ColorMaps;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ColorQuadCache
implements IQuadCache {
    static final int[] COLOR = new int[]{-1};
    static final RetextureEvent.Rotation[] ROTATION = new RetextureEvent.Rotation[]{RetextureEvent.Rotation.ROTATION_0};
    final Direction dir;
    DyeColor color;
    BlockState state;

    public ColorQuadCache(Direction dir) {
        this.dir = dir;
    }

    public void setColor(DyeColor color) {
        this.color = color;
        if (color == null) {
            return;
        }
        this.state = ColorMaps.CFOAM_BLOCKS.getBlock(color, Blocks.f_50016_).m_49966_();
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte((byte)(this.color == null ? -1 : this.color.m_41060_()));
    }

    @Override
    public void read(IInputBuffer buffer) {
        byte value = buffer.readByte();
        this.setColor(value == -1 ? null : DyeColor.m_41053_((int)value));
    }

    @Override
    public CompoundTag write(CompoundTag nbt) {
        nbt.m_128344_("color", (byte)(this.color == null ? -1 : this.color.m_41060_()));
        return nbt;
    }

    @Override
    public void read(CompoundTag nbt) {
        int value = nbt.m_128451_("color");
        this.setColor(value == -1 ? null : DyeColor.m_41053_((int)value));
    }

    @Override
    public BlockState getDisplayBlock() {
        return this.state;
    }

    @Override
    public int[] getColors() {
        return COLOR;
    }

    @Override
    public RetextureEvent.Rotation[] getRotations() {
        return ROTATION;
    }

    @Override
    public Direction getSide() {
        return this.dir;
    }

    public DyeColor getColor() {
        return this.color;
    }
}

