/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.event.level.LevelEvent
 */
package ic2.api.events;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.LevelEvent;

public class RetextureEvent
extends LevelEvent {
    BlockPos pos;
    Direction side;
    Player applyingPlayer;
    TextureContainer container;
    boolean applied = false;

    public RetextureEvent(LevelAccessor world, BlockPos pos, Direction side, Player applyingPlayer, TextureContainer container) {
        super(world);
        this.pos = pos;
        this.side = side;
        this.applyingPlayer = applyingPlayer;
        this.container = container;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Direction getSide() {
        return this.side;
    }

    public BlockState getBlockState() {
        return this.getLevel().m_8055_(this.pos);
    }

    public BlockEntity getBlockEntity() {
        return this.getLevel().m_7702_(this.pos);
    }

    public Player getApplyingPlayer() {
        return this.applyingPlayer;
    }

    public TextureContainer getContainer() {
        return this.container;
    }

    public boolean isApplied() {
        return this.applied;
    }

    public void setApplied(boolean apply) {
        this.applied = apply;
    }

    public static class TextureContainer {
        BlockState state;
        Direction side;
        Rotation[] rotations;
        int[] colors;

        public TextureContainer(CompoundTag nbt) {
            this(NbtUtils.m_129241_((CompoundTag)nbt.m_128469_("block")), Direction.m_122376_((int)nbt.m_128451_("side")), TextureContainer.decode(nbt.m_128463_("rotations")), nbt.m_128465_("colors"));
        }

        public TextureContainer(BlockState state, Direction side, Rotation[] rotations, int[] colors) {
            this.state = state;
            this.side = side;
            this.rotations = rotations;
            this.colors = colors;
        }

        public BlockState getState() {
            return this.state;
        }

        public Direction getSide() {
            return this.side;
        }

        public int[] getColors() {
            return this.colors;
        }

        public Rotation[] getRotations() {
            return this.rotations;
        }

        public boolean equals(Object obj) {
            if (obj instanceof TextureContainer) {
                TextureContainer other = (TextureContainer)obj;
                return other.state.equals(this.state) && other.side == this.side && Arrays.equals(other.colors, this.colors);
            }
            return false;
        }

        public CompoundTag save() {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128365_("block", (Tag)NbtUtils.m_129202_((BlockState)this.state));
            nbt.m_128344_("side", (byte)this.side.m_122411_());
            nbt.m_128382_("rotations", TextureContainer.encode(this.rotations));
            nbt.m_128385_("colors", this.colors);
            return nbt.m_6426_();
        }

        private static Rotation[] decode(byte[] data) {
            Rotation[] rotation = new Rotation[data.length];
            for (int i = 0; i < data.length; ++i) {
                rotation[i] = Rotation.byIndex(data[i]);
            }
            return rotation;
        }

        private static byte[] encode(Rotation[] rotations) {
            byte[] data = new byte[rotations.length];
            for (int i = 0; i < data.length; ++i) {
                data[i] = (byte)rotations[i].getIndex();
            }
            return data;
        }
    }

    public static enum Rotation {
        ROTATION_0(0),
        ROTATION_90(1),
        ROTATION_180(2),
        ROTATION_270(3);

        public static final Rotation[] ROTATIONS;
        int rotation;

        private Rotation(int rotation) {
            this.rotation = rotation;
        }

        public static Rotation byIndex(int index) {
            return ROTATIONS[index % ROTATIONS.length];
        }

        public Rotation getNext() {
            return Rotation.byIndex(this.rotation + 1);
        }

        public int getIndex() {
            return this.rotation;
        }

        public int getRotation() {
            return this.rotation * 90;
        }

        static {
            Rotation[] values = Rotation.values();
            ROTATIONS = new Rotation[values.length];
            Rotation[] rotationArray = values;
            int n = rotationArray.length;
            for (int i = 0; i < n; ++i) {
                Rotation rotation;
                Rotation.ROTATIONS[rotation.getIndex()] = rotation = rotationArray[i];
            }
        }
    }
}

