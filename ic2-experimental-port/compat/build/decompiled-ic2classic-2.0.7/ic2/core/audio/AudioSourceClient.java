/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.audio.Channel
 *  net.minecraft.client.sounds.ChannelAccess$ChannelHandle
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.audio;

import com.mojang.blaze3d.audio.Channel;
import ic2.core.audio.AudioManager;
import ic2.core.audio.AudioManagerClient;
import ic2.core.audio.IAudioPosition;
import ic2.core.audio.IAudioSource;
import ic2.core.utils.math.MathUtils;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class AudioSourceClient
implements IAudioSource {
    private static final float FADING_DISTANCE = 16.0f;
    public static final int VALID = 1;
    public static final int AUDIO_UPDATE = 2;
    public static final int LOOPING = 4;
    public static final int PRIORITY = 8;
    public static final int PLAYING = 16;
    public static final int PAUSED = 32;
    public static final int ENABLED = 64;
    public static final int ACCEPTING = 128;
    AudioManagerClient manager;
    ResourceLocation location;
    IAudioPosition pos;
    ChannelAccess.ChannelHandle audio;
    Vec3 currentPosition;
    AudioManager.SoundType type;
    float modifier = 1.0f;
    float volume;
    float realVolume;
    float pitch;
    int flags = 65;

    public AudioSourceClient(AudioManagerClient manager, ResourceLocation location, IAudioPosition pos, AudioManager.SoundType type, float volume, float pitch, boolean looping, boolean priority) {
        this.manager = manager;
        this.location = location;
        this.pos = pos;
        this.currentPosition = pos.getPosition();
        this.type = type;
        this.volume = volume;
        this.pitch = pitch;
        if (looping) {
            this.setFlag(4);
        }
        if (priority) {
            this.setFlag(8);
        }
    }

    public AudioSourceClient(AudioManagerClient manager) {
        this.manager = manager;
    }

    protected void setFlag(int flag) {
        this.flags |= flag;
    }

    protected boolean hasFlag(int flag) {
        return (this.flags & flag) == flag;
    }

    protected boolean hasNotFlag(int flag) {
        return (this.flags & flag) != flag;
    }

    protected void clearFlag(int flag) {
        this.flags &= ~flag;
    }

    @Override
    public boolean isValid() {
        return this.hasFlag(1);
    }

    @Override
    public boolean isEnabled() {
        return this.hasFlag(65);
    }

    @Override
    public boolean isPlaying() {
        return this.hasFlag(16) && this.hasNotFlag(32);
    }

    @Override
    public boolean isPaused() {
        return this.hasFlag(32);
    }

    @Override
    public boolean isPriority() {
        return this.hasFlag(8);
    }

    @Override
    public float getVolume() {
        return this.volume;
    }

    @Override
    public float getRealVolume() {
        return this.realVolume;
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    @Override
    public void setVolume(float volume) {
        if (this.hasNotFlag(1)) {
            return;
        }
        this.volume = volume;
        this.setFlag(2);
    }

    @Override
    public void setPitch(float pitch) {
        if (this.hasNotFlag(1)) {
            return;
        }
        this.pitch = pitch;
        this.setFlag(2);
    }

    @Override
    public void play() {
        if (this.hasNotFlag(1)) {
            return;
        }
        this.clearFlag(32);
        this.setFlag(16);
        if (this.audio != null && this.hasFlag(64)) {
            this.audio.m_120154_(Channel::m_83672_);
        }
    }

    @Override
    public void pause() {
        if (this.hasNotFlag(17)) {
            return;
        }
        this.setFlag(32);
        if (this.audio != null && this.hasFlag(64)) {
            this.audio.m_120154_(Channel::m_83677_);
        }
    }

    @Override
    public void stop() {
        if (this.hasNotFlag(1)) {
            return;
        }
        this.clearFlag(48);
        if (this.audio != null && this.hasFlag(64)) {
            this.audio.m_120154_(Channel::m_83679_);
        }
    }

    @Override
    public void remove() {
        this.stop();
        this.clearFlag(1);
    }

    @Override
    public void disable() {
        if (this.hasNotFlag(65)) {
            return;
        }
        if (this.hasFlag(16)) {
            this.stop();
            this.setFlag(16);
        }
        this.clearFlag(64);
    }

    @Override
    public void enable() {
        if (this.hasNotFlag(1) || this.hasFlag(64)) {
            return;
        }
        this.setFlag(64);
        this.ensureValid();
    }

    public void ensureValid() {
        if (this.hasNotFlag(65)) {
            return;
        }
        if (this.audio != null && this.audio.m_120151_()) {
            this.audio = null;
            if (this.hasNotFlag(4)) {
                this.clearFlag(48);
            }
        }
        if (this.audio == null && this.hasNotFlag(128)) {
            this.setFlag(128);
            this.manager.createEntry().thenAccept(T -> {
                this.audio = T;
                this.clearFlag(128);
                this.audio.m_120154_(i -> {
                    i.m_83656_(this.manager.createBuffer(this.location));
                    this.syncWithSource((Channel)i, true);
                    if (this.hasFlag(16) && this.hasNotFlag(32)) {
                        i.m_83672_();
                    }
                });
            });
        }
    }

    @Override
    public IAudioPosition getPosition() {
        return this.pos;
    }

    @Override
    public void updatePosition() {
        if (this.hasNotFlag(1)) {
            return;
        }
        this.currentPosition = this.pos.getPosition();
        if (this.audio != null) {
            this.audio.m_120154_(i -> i.m_83654_(this.currentPosition));
        }
    }

    @Override
    public void updateVolume(Player player) {
        if (this.hasNotFlag(1)) {
            this.realVolume = 0.0f;
            return;
        }
        if (this.manager.didModifiersChange()) {
            this.modifier = this.manager.getModifier(this.type, this.currentPosition);
        }
        float multiplier = this.volume;
        multiplier *= this.manager.getVolumeForType(this.type);
        multiplier *= this.manager.getMasterVolume();
        multiplier *= this.modifier;
        double maxDistance = (multiplier /= this.volume) > 1.0f ? (double)(16.0f * multiplier * Math.max(this.volume, 0.2f)) : (double)(16.0f * Math.max(this.volume, 0.2f));
        double distance = 1.0;
        Vec3 playerPos = player.m_20182_();
        if (this.pos.isInSameWorld(player.f_19853_)) {
            distance = Math.max(1.0, this.pos.getPosition().m_82554_(playerPos));
        }
        if (distance > maxDistance) {
            this.realVolume = 0.0f;
            this.disable();
            return;
        }
        this.setFlag(64);
        double newRealVolume = (double)this.volume * (1.0 - distance / maxDistance);
        newRealVolume *= (double)multiplier;
        if (newRealVolume > (double)0.1f) {
            Vec3 perStep = MathUtils.divide(this.pos.getPosition().m_82546_(playerPos), distance);
            int i2 = 0;
            while ((double)i2 < distance) {
                BlockPos pos = new BlockPos(playerPos);
                BlockState state = player.f_19853_.m_8055_(pos);
                if (state.m_60734_() != Blocks.f_50016_) {
                    newRealVolume *= state.m_60804_((BlockGetter)player.f_19853_, pos) ? 0.6 : 0.8;
                }
                playerPos = playerPos.m_82549_(perStep);
                ++i2;
            }
        }
        this.realVolume = (float)newRealVolume;
        if (this.realVolume <= 1.0E-4f) {
            this.realVolume = 0.0f;
            this.disable();
            return;
        }
        if (this.audio != null) {
            if (this.hasFlag(2)) {
                this.clearFlag(2);
                this.audio.m_120154_(i -> this.syncWithSource((Channel)i, false));
            } else {
                this.audio.m_120154_(i -> i.m_83666_(this.realVolume));
            }
        }
    }

    public void syncWithSource(Channel source, boolean init) {
        if (init) {
            source.m_83663_(this.hasFlag(4));
            source.m_83681_();
            source.m_83670_(false);
            source.m_83654_(this.currentPosition);
        }
        source.m_83666_(this.realVolume);
        source.m_83650_(this.pitch);
    }
}

