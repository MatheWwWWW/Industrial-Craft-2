/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.rendering.camouflage;

import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.rendering.camouflage.IQuadCache;
import java.util.Arrays;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.state.BlockState;

public class TextureQuadCache
implements IQuadCache {
    final Direction dir;
    BlockState state;
    Direction side;
    RetextureEvent.Rotation[] rotations;
    int[] colors;

    public TextureQuadCache(Direction dir) {
        this.dir = dir;
    }

    public void setData(RetextureEvent.TextureContainer container) {
        this.state = container.getState();
        this.side = container.getSide();
        this.rotations = container.getRotations();
        this.colors = container.getColors();
    }

    public boolean canSetData(RetextureEvent.TextureContainer container) {
        return this.state != container.getState() || this.side != container.getSide() || !Arrays.equals(this.colors, container.getColors()) || this.matchesNot(container.getRotations());
    }

    private boolean matchesNot(RetextureEvent.Rotation[] array) {
        if (this.rotations == null) {
            return true;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == this.rotations[i]) continue;
            return true;
        }
        return false;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeNBTData(NbtUtils.m_129202_((BlockState)this.state));
        buffer.writeByte((byte)this.side.m_122411_());
        buffer.writeByte((byte)this.colors.length);
        for (int i = 0; i < this.colors.length; ++i) {
            buffer.writeByte((byte)this.rotations[i].getIndex());
            buffer.writeInt(this.colors[i]);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.state = NbtUtils.m_129241_((CompoundTag)buffer.readNBTData());
        this.side = Direction.m_122376_((int)buffer.readByte());
        int size = buffer.readByte();
        this.rotations = new RetextureEvent.Rotation[size];
        this.colors = new int[size];
        for (int i = 0; i < size; ++i) {
            this.rotations[i] = RetextureEvent.Rotation.byIndex(buffer.readByte());
            this.colors[i] = buffer.readInt();
        }
    }

    @Override
    public CompoundTag write(CompoundTag nbt) {
        nbt.m_128365_("state", (Tag)NbtUtils.m_129202_((BlockState)this.state));
        nbt.m_128344_("side", (byte)this.side.m_122411_());
        int[] data = new int[this.colors.length * 2 + 1];
        data[data.length - 1] = this.colors.length;
        for (int i = 0; i < this.colors.length; ++i) {
            data[i * 2] = this.rotations[i].getIndex();
            data[i * 2 + 1] = this.colors[i];
        }
        nbt.m_128385_("data", data);
        return nbt;
    }

    @Override
    public void read(CompoundTag nbt) {
        this.state = NbtUtils.m_129241_((CompoundTag)nbt.m_128469_("state"));
        this.side = Direction.m_122376_((int)nbt.m_128451_("side"));
        int[] data = nbt.m_128465_("data");
        int size = data[data.length - 1];
        this.rotations = new RetextureEvent.Rotation[size];
        this.colors = new int[size];
        for (int i = 0; i < size; ++i) {
            this.rotations[i] = RetextureEvent.Rotation.byIndex(data[i * 2]);
            this.colors[i] = data[i * 2 + 1];
        }
    }

    @Override
    public BlockState getDisplayBlock() {
        return this.state;
    }

    @Override
    public int[] getColors() {
        return this.colors;
    }

    @Override
    public RetextureEvent.Rotation[] getRotations() {
        return this.rotations;
    }

    @Override
    public Direction getSide() {
        return this.side;
    }
}

