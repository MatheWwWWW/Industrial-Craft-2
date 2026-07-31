/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.audio.Channel
 *  com.mojang.blaze3d.audio.Library
 *  com.mojang.blaze3d.audio.Library$Pool
 *  com.mojang.blaze3d.audio.OggAudioStream
 *  com.mojang.blaze3d.audio.SoundBuffer
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.sounds.SoundEngineExecutor
 *  org.lwjgl.openal.AL10
 */
package ic2.core.audio;

import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import com.mojang.blaze3d.audio.OggAudioStream;
import com.mojang.blaze3d.audio.SoundBuffer;
import ic2.core.IC2;
import ic2.core.audio.AudioManagerClient;
import ic2.core.audio.AudioPosition;
import ic2.core.audio.AudioSourceClient;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sounds.SoundEngineExecutor;
import org.lwjgl.openal.AL10;

@Environment(value=EnvType.CLIENT)
class AudioEngine
implements Closeable {
    private final Library soundEngine;
    private final SoundEngineExecutor worker;
    private final Map<String, SoundBuffer> soundCache = new HashMap<String, SoundBuffer>();
    private final Map<AudioSourceClient, Channel> sourceMap = new IdentityHashMap<AudioSourceClient, Channel>();

    public AudioEngine(Library library, SoundEngineExecutor soundEngineExecutor, int n) {
        this.soundEngine = library;
        this.worker = soundEngineExecutor;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private static SoundBuffer loadSound(URL uRL) {
        try (InputStream inputStream = uRL.openStream();){
            SoundBuffer soundBuffer;
            try (OggAudioStream oggAudioStream = new OggAudioStream(inputStream);){
                soundBuffer = new SoundBuffer(oggAudioStream.m_83764_(), oggAudioStream.m_6206_());
            }
            return soundBuffer;
        }
        catch (IOException iOException) {
            IC2.log.warn(LogCategory.Audio, iOException, "Error reading sound file: %s.", uRL);
            return null;
        }
    }

    public boolean newSource(AudioSourceClient audioSourceClient, String string, boolean bl, boolean bl2, AudioPosition audioPosition, float f) {
        URL uRL = AudioManagerClient.getSourceURL(string);
        if (uRL == null) {
            IC2.log.warn(LogCategory.Audio, "Invalid sound file: %s.", string);
            return false;
        }
        SoundBuffer soundBuffer = this.soundCache.get(string);
        if (soundBuffer == null) {
            soundBuffer = AudioEngine.loadSound(uRL);
            if (soundBuffer == null) {
                return false;
            }
            this.soundCache.put(string, soundBuffer);
        }
        Channel channel = this.soundEngine.m_83697_(Library.Pool.STATIC);
        channel.m_83656_(soundBuffer);
        channel.m_83654_(audioPosition.asVec3d());
        channel.m_83666_(f);
        channel.m_83663_(bl2);
        this.sourceMap.put(audioSourceClient, channel);
        return true;
    }

    public void setPosition(AudioSourceClient audioSourceClient, AudioPosition audioPosition) {
        Channel channel = this.sourceMap.get(audioSourceClient);
        if (channel != null) {
            this.worker.execute(() -> channel.m_83654_(audioPosition.asVec3d()));
        }
    }

    public boolean isPlaying(AudioSourceClient audioSourceClient) {
        Channel channel = this.sourceMap.get(audioSourceClient);
        return channel != null && channel.m_166126_();
    }

    public void play(AudioSourceClient audioSourceClient) {
        Channel channel = this.sourceMap.get(audioSourceClient);
        if (channel != null) {
            this.worker.execute(() -> ((Channel)channel).m_83672_());
        }
    }

    public void pause(AudioSourceClient audioSourceClient) {
        Channel channel = this.sourceMap.get(audioSourceClient);
        if (channel != null) {
            this.worker.execute(() -> ((Channel)channel).m_83677_());
        }
    }

    public void stop(AudioSourceClient audioSourceClient) {
        Channel channel = this.sourceMap.get(audioSourceClient);
        if (channel != null) {
            this.worker.execute(() -> ((Channel)channel).m_83679_());
        }
    }

    public void cull(AudioSourceClient audioSourceClient) {
        this.stop(audioSourceClient);
    }

    public void activate(AudioSourceClient audioSourceClient) {
    }

    public float getVolume(AudioSourceClient audioSourceClient) {
        Channel channel = this.sourceMap.get(audioSourceClient);
        if (channel != null) {
            try {
                Field field = ReflectionUtil.getField(Channel.class, "f_83642_", "field_18893", "pointer");
                field.setAccessible(true);
                int n = field.getInt(channel);
                return AL10.alGetSourcef((int)n, (int)4106);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                IC2.log.error(LogCategory.Audio, reflectiveOperationException, "Failed to get pointer field from %s", channel);
            }
        }
        return 0.0f;
    }

    public void setVolume(AudioSourceClient audioSourceClient, float f) {
        Channel channel = this.sourceMap.get(audioSourceClient);
        if (channel != null) {
            this.worker.execute(() -> channel.m_83666_(f));
        }
    }

    public void setPitch(AudioSourceClient audioSourceClient, float f) {
        Channel channel = this.sourceMap.get(audioSourceClient);
        if (channel != null) {
            this.worker.execute(() -> channel.m_83650_(f));
        }
    }

    public void removeSource(AudioSourceClient audioSourceClient) {
        Channel channel = this.sourceMap.remove(audioSourceClient);
        if (channel != null) {
            this.soundEngine.m_83695_(channel);
        }
    }

    @Override
    public void close() {
        for (Channel channel : this.sourceMap.values()) {
            channel.m_83665_();
        }
        this.sourceMap.clear();
        for (SoundBuffer soundBuffer : this.soundCache.values()) {
            soundBuffer.m_83801_();
        }
        this.soundCache.clear();
    }
}

