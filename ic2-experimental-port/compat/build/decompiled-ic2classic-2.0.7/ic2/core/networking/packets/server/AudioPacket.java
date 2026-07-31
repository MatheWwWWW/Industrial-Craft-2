/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.networking.packets.server;

import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.audio.providers.SimplePosition;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public abstract class AudioPacket
extends IC2Packet {
    ResourceLocation location;
    AudioManager.SoundType type;
    float volume;
    float pitch;

    public AudioPacket() {
    }

    public AudioPacket(ResourceLocation location, AudioManager.SoundType type, float volume, float pitch) {
        this.location = location;
        this.type = type;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130085_(this.location);
        buffer.writeByte(this.type.getIndex());
        buffer.writeFloat(this.volume);
        buffer.writeFloat(this.pitch);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.location = buffer.m_130281_();
        this.type = AudioManager.SoundType.byIndex(buffer.readByte());
        this.volume = buffer.readFloat();
        this.pitch = buffer.readFloat();
    }

    public static class TileAudioPacket
    extends AudioPacket {
        BlockPos pos;

        public TileAudioPacket() {
        }

        public TileAudioPacket(BlockEntity tile, ResourceLocation location, AudioManager.SoundType type, float volume, float pitch) {
            super(location, type, volume, pitch);
            this.pos = tile.m_58899_();
        }

        @Override
        public void write(FriendlyByteBuf buffer) {
            buffer.writeLong(this.pos.m_121878_());
            super.write(buffer);
        }

        @Override
        public void read(FriendlyByteBuf buffer) {
            this.pos = BlockPos.m_122022_((long)buffer.readLong());
            super.read(buffer);
        }

        @Override
        public void handlePacket(Player source) {
            BlockEntity tile = source.f_19853_.m_7702_(this.pos);
            if (tile == null) {
                return;
            }
            IC2.AUDIO.playSound(tile, this.location, this.type, this.volume, this.pitch);
        }
    }

    public static class EntityAudioPacket
    extends AudioPacket {
        int id;

        public EntityAudioPacket() {
        }

        public EntityAudioPacket(Entity entity, ResourceLocation location, AudioManager.SoundType type, float volume, float pitch) {
            super(location, type, volume, pitch);
            this.id = entity.m_19879_();
        }

        @Override
        public void write(FriendlyByteBuf buffer) {
            buffer.writeInt(this.id);
            super.write(buffer);
        }

        @Override
        public void read(FriendlyByteBuf buffer) {
            this.id = buffer.readInt();
            super.read(buffer);
        }

        @Override
        public void handlePacket(Player source) {
            Entity entity = source.f_19853_.m_6815_(this.id);
            if (entity == null) {
                return;
            }
            IC2.AUDIO.playSound(entity, this.location, this.type, this.volume, this.pitch);
        }
    }

    public static class SimpleAudioPacket
    extends AudioPacket {
        Vec3 position;

        public SimpleAudioPacket() {
        }

        public SimpleAudioPacket(Vec3 position, ResourceLocation location, AudioManager.SoundType type, float volume, float pitch) {
            super(location, type, volume, pitch);
            this.position = position;
        }

        @Override
        public void write(FriendlyByteBuf buffer) {
            super.write(buffer);
            buffer.writeDouble(this.position.m_7096_());
            buffer.writeDouble(this.position.m_7098_());
            buffer.writeDouble(this.position.m_7094_());
        }

        @Override
        public void read(FriendlyByteBuf buffer) {
            super.read(buffer);
            this.position = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        }

        @Override
        public void handlePacket(Player source) {
            if (source == null || source.f_19853_ == null) {
                return;
            }
            IC2.AUDIO.playSound(new SimplePosition(source.f_19853_, this.position), this.location, this.type, this.volume, this.pitch);
        }
    }
}

