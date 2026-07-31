/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.audio;

import ic2.core.IC2;
import ic2.core.audio.AudioEngine;
import ic2.core.audio.AudioManagerClient;
import ic2.core.audio.AudioPosition;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.util.Util;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@Environment(value=EnvType.CLIENT)
class AudioSourceClient
extends AudioSource
implements Comparable<AudioSourceClient> {
    protected final AudioEngine soundSystem;
    private boolean valid = false;
    private boolean culled = false;
    private final String initialSoundFile;
    private final boolean loop;
    private final boolean prioritized;
    private final Reference<Object> obj;
    private AudioPosition position;
    private final PositionSpec positionSpec;
    private float configuredVolume;
    private float realVolume;
    private boolean isPlaying = false;

    AudioSourceClient(AudioEngine audioEngine, String string, Object object, PositionSpec positionSpec, String string2, boolean bl, boolean bl2, float f) {
        this.soundSystem = audioEngine;
        this.initialSoundFile = string2;
        this.loop = bl;
        this.prioritized = bl2;
        this.obj = new WeakReference<Object>(object);
        this.position = AudioPosition.getFrom(object, positionSpec);
        this.positionSpec = positionSpec;
        this.configuredVolume = f;
    }

    void setup() {
        if (this.valid) {
            throw new IllegalStateException("already initialized");
        }
        this.valid = this.soundSystem.newSource(this, this.initialSoundFile, this.prioritized, this.loop, this.position, ((AudioManagerClient)IC2.audioManager).fadingDistance * Math.max(this.configuredVolume, 1.0f));
        this.setVolume(this.configuredVolume);
    }

    @Override
    public int compareTo(AudioSourceClient audioSourceClient) {
        if (this.culled) {
            return (int)((this.realVolume * 0.9f - audioSourceClient.realVolume) * 128.0f);
        }
        return (int)((this.realVolume - audioSourceClient.realVolume) * 128.0f);
    }

    @Override
    public void remove() {
        if (!this.check()) {
            return;
        }
        this.stop();
        this.soundSystem.removeSource(this);
        this.setInvalid();
    }

    boolean isValid() {
        return this.valid;
    }

    void setInvalid() {
        this.valid = false;
    }

    @Override
    public void play() {
        if (!this.check()) {
            return;
        }
        if (this.isPlaying) {
            return;
        }
        this.isPlaying = true;
        if (this.culled) {
            return;
        }
        this.soundSystem.play(this);
    }

    @Override
    public void pause() {
        if (!this.check()) {
            return;
        }
        if (!this.isPlaying || this.culled) {
            return;
        }
        this.isPlaying = false;
        this.soundSystem.pause(this);
    }

    @Override
    public void stop() {
        if (!this.check() || !this.isPlaying) {
            return;
        }
        this.isPlaying = false;
        if (this.culled) {
            return;
        }
        this.soundSystem.stop(this);
    }

    @Override
    public void cull() {
        if (!this.check() || this.culled) {
            return;
        }
        this.soundSystem.cull(this);
        this.culled = true;
    }

    @Override
    public void activate() {
        if (!this.check() || !this.culled) {
            return;
        }
        this.soundSystem.activate(this);
        this.culled = false;
        if (this.isPlaying) {
            this.isPlaying = false;
            this.play();
        }
    }

    @Override
    public float getVolume() {
        if (!this.check()) {
            return 0.0f;
        }
        return this.soundSystem.getVolume(this);
    }

    @Override
    public float getRealVolume() {
        return this.realVolume;
    }

    @Override
    public void setVolume(float f) {
        if (!this.check()) {
            return;
        }
        this.configuredVolume = f;
        this.soundSystem.setVolume(this, 0.001f);
    }

    @Override
    public void setPitch(float f) {
        if (!this.check()) {
            return;
        }
        this.soundSystem.setPitch(this, f);
    }

    @Override
    public void updatePosition() {
        if (!this.check()) {
            return;
        }
        this.position = AudioPosition.getFrom(this.obj.get(), this.positionSpec);
        if (this.position == null) {
            return;
        }
        this.soundSystem.setPosition(this, this.position);
    }

    @Override
    public void updateVolume(Player player) {
        float f;
        float f2;
        float f3;
        float f4;
        if (!this.check() || !this.isPlaying) {
            this.realVolume = 0.0f;
            return;
        }
        float f5 = ((AudioManagerClient)IC2.audioManager).fadingDistance * Math.max(this.configuredVolume, 1.0f);
        float f6 = 1.0f;
        float f7 = 1.0f;
        Level level = player.m_20193_();
        float f8 = (float)player.m_20185_();
        float f9 = (float)player.m_20186_();
        float f10 = (float)player.m_20189_();
        if (this.position != null && this.position.getWorld() == level) {
            f4 = this.position.x - f8;
            f3 = this.position.y - f9;
            f2 = this.position.z - f10;
            f = (float)Math.sqrt(f4 * f4 + f3 * f3 + f2 * f2);
        } else {
            f = Float.POSITIVE_INFINITY;
        }
        if (f > f5) {
            this.realVolume = 0.0f;
            this.cull();
            return;
        }
        if (f < f7) {
            f = f7;
        }
        f4 = 1.0f - f6 * (f - f7) / (f5 - f7);
        f3 = f4 * this.configuredVolume * IC2.audioManager.getMasterVolume();
        f2 = (this.position.x - f8) / f;
        float f11 = (this.position.y - f9) / f;
        float f12 = (this.position.z - f10) / f;
        if ((double)f3 > 0.1) {
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int n = 0;
            while ((float)n < f) {
                BlockState blockState;
                mutableBlockPos.m_122178_(Util.roundToNegInf(f8), Util.roundToNegInf(f9), Util.roundToNegInf(f10));
                if (!this.position.isSameBlock((BlockPos)mutableBlockPos) && !(blockState = level.m_8055_((BlockPos)mutableBlockPos)).m_60795_()) {
                    f3 = blockState.m_60796_((BlockGetter)level, (BlockPos)mutableBlockPos) ? (f3 *= 0.6f) : (f3 *= 0.8f);
                }
                ++n;
                f8 += f2;
                f9 += f11;
                f10 += f12;
            }
        }
        if ((double)Math.abs(this.realVolume / f3 - 1.0f) > 0.06) {
            this.soundSystem.setVolume(this, IC2.audioManager.getMasterVolume() * Math.min(f3, 1.0f));
        }
        this.realVolume = f3;
    }

    protected boolean check() {
        if (this.valid && IC2.audioManager.valid()) {
            return true;
        }
        this.valid = false;
        return false;
    }
}

