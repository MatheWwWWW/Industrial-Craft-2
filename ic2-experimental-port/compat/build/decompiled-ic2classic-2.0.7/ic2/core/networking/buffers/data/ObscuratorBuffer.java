/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.networking.buffers.data;

import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import java.util.Arrays;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ObscuratorBuffer
implements INetworkDataBuffer {
    BlockState state;
    Direction dir;
    int[] colors;
    boolean mainHand;

    public ObscuratorBuffer() {
    }

    public ObscuratorBuffer(BlockState state, Direction dir, int[] colors, boolean mainHand) {
        this.state = state;
        this.dir = dir;
        this.colors = colors;
        this.mainHand = mainHand;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeBoolean(this.mainHand);
        buffer.writeNBTData(NbtUtils.m_129202_((BlockState)this.state));
        buffer.writeByte((byte)this.dir.m_122411_());
        buffer.writeByte((byte)this.colors.length);
        for (int color : this.colors) {
            buffer.writeInt(color);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.mainHand = buffer.readBoolean();
        this.state = NbtUtils.m_129241_((CompoundTag)buffer.readNBTData());
        this.dir = Direction.m_122376_((int)buffer.readByte());
        this.colors = new int[buffer.readByte()];
        for (int i = 0; i < this.colors.length; ++i) {
            this.colors[i] = buffer.readInt();
        }
    }

    public ItemStack getItem(Player player) {
        return player.m_21120_(this.mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }

    public RetextureEvent.TextureContainer createContainer() {
        return new RetextureEvent.TextureContainer(this.state, this.dir, this.createRotations(), this.colors);
    }

    public int[] getColors() {
        return this.colors;
    }

    public Direction getDir() {
        return this.dir;
    }

    public BlockState getState() {
        return this.state;
    }

    public RetextureEvent.Rotation[] createRotations() {
        RetextureEvent.Rotation[] rotation = new RetextureEvent.Rotation[this.colors.length];
        Arrays.fill((Object[])rotation, (Object)RetextureEvent.Rotation.ROTATION_0);
        return rotation;
    }
}

