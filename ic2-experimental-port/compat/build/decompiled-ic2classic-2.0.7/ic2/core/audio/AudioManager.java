/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.audio;

import ic2.core.audio.AudioEffect;
import ic2.core.audio.IAudioSource;
import ic2.core.audio.providers.SimplePosition;
import ic2.core.audio.tracker.IAudioTracker;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.AudioPacket;
import ic2.core.utils.collection.CollectionUtils;
import java.util.EnumMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AudioManager {
    public void init() {
    }

    public void playSound(Object obj, ResourceLocation location) {
        this.playSound(obj, location, SoundType.STATIC);
    }

    public void playSound(Object obj, ResourceLocation location, SoundType type) {
        this.playSound(obj, location, type, 1.0f, 1.0f);
    }

    public void playSound(Object obj, ResourceLocation location, SoundType type, float volume, float pitch) {
        if (location == null) {
            return;
        }
        if (obj instanceof Entity) {
            Entity entity = (Entity)obj;
            PacketManager.INSTANCE.sendToAllEntityWatchers(entity, new AudioPacket.EntityAudioPacket(entity, location, type, volume, pitch));
        } else if (obj instanceof BlockEntity) {
            BlockEntity tile = (BlockEntity)obj;
            PacketManager.INSTANCE.sendToAllChunkWatchers(tile.m_58904_().m_46745_(tile.m_58899_()), new AudioPacket.TileAudioPacket(tile, location, type, volume, pitch));
        } else if (obj instanceof SimplePosition) {
            SimplePosition position = (SimplePosition)obj;
            PacketManager.INSTANCE.sendToAllChunkWatchers(position.getWorld().m_46745_(new BlockPos(position.getPosition())), new AudioPacket.SimpleAudioPacket(position.getPosition(), location, type, volume, pitch));
        }
    }

    public IAudioSource createSource(Object obj, ResourceLocation location) {
        return null;
    }

    public IAudioSource createSource(Object obj, ResourceLocation location, SoundType type, float volume, boolean loop, boolean priority) {
        return null;
    }

    public void removeSource(Object obj) {
    }

    public float getDefaultVolume() {
        return 1.0f;
    }

    public float getMasterVolume() {
        return 0.0f;
    }

    public float getVolumeForType(SoundType type) {
        return 0.0f;
    }

    public void addTracker(IAudioTracker tracker) {
    }

    public void addModifier(BlockPos pos, EnumMap<SoundType, AudioEffect> modifiers) {
    }

    public void removeModifier(BlockPos pos) {
    }

    public void resetAll() {
    }

    public static enum SoundType implements CollectionUtils.IIndexEnum
    {
        STATIC(0),
        ITEM(1),
        BACKPACK(2);

        static SoundType[] TYPES;
        int index;

        private SoundType(int index) {
            this.index = index;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        public static SoundType byIndex(int index) {
            return TYPES[index % TYPES.length];
        }

        static {
            TYPES = (SoundType[])CollectionUtils.createSortedArray((CollectionUtils.IIndexEnum[])SoundType.values());
        }
    }
}

