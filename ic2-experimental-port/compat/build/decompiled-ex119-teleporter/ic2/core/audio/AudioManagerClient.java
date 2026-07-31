/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.audio.Library
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.sounds.SoundEngine
 *  net.minecraft.client.sounds.SoundEngineExecutor
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.audio;

import com.mojang.blaze3d.audio.Library;
import ic2.core.IC2;
import ic2.core.audio.AudioEngine;
import ic2.core.audio.AudioManager;
import ic2.core.audio.AudioPosition;
import ic2.core.audio.AudioSource;
import ic2.core.audio.AudioSourceClient;
import ic2.core.audio.AudioSourceSingle;
import ic2.core.audio.PositionSpec;
import ic2.core.audio.SingleSound;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundEngineExecutor;
import net.minecraft.world.entity.player.Player;

public final class AudioManagerClient
extends AudioManager {
    public float fadingDistance = 16.0f;
    boolean enabled = true;
    private boolean wasPaused = false;
    private int maxSourceCount = 32;
    private final int streamingSourceCount = 4;
    private SoundEngine soundSystem;
    private AudioEngine audioEngine;
    private static final Field soundSystemStarted = ReflectionUtil.getField(SoundEngine.class, Boolean.TYPE);
    float masterVolume = 0.5f;
    private int nextId = 0;
    private final Map<WeakObject, List<AudioSourceClient>> objectToAudioSourceMap = new HashMap<WeakObject, List<AudioSourceClient>>();
    private final Queue<AudioSource> validAudioSources = new PriorityQueue<AudioSource>();
    private final Queue<AudioSourceSingle> singleSoundQueue = new ArrayDeque<AudioSourceSingle>();

    @Override
    public void initialize() {
        this.enabled = ConfigUtil.getBool(MainConfig.get(), "audio/enabled");
        this.masterVolume = ConfigUtil.getFloat(MainConfig.get(), "audio/volume");
        this.fadingDistance = ConfigUtil.getFloat(MainConfig.get(), "audio/fadeDistance");
        this.maxSourceCount = ConfigUtil.getInt(MainConfig.get(), "audio/maxSourceCount");
        if (this.maxSourceCount <= 6) {
            IC2.log.info(LogCategory.Audio, "The audio source limit is too low to enable IC2 sounds.");
            this.enabled = false;
        }
        if (!this.enabled) {
            IC2.log.debug(LogCategory.Audio, "Sounds disabled.");
            return;
        }
        IC2.log.debug(LogCategory.Audio, "Using %d audio sources.", this.maxSourceCount);
        if (soundSystemStarted == null) {
            IC2.log.warn(LogCategory.Audio, "Can't find SoundSystem.started, IC2 audio disabled.");
            this.enabled = false;
            return;
        }
    }

    public void onSoundSetup() {
        if (!this.enabled) {
            return;
        }
        for (List<AudioSourceClient> list : this.objectToAudioSourceMap.values()) {
            for (AudioSourceClient audioSourceClient : list) {
                if (!audioSourceClient.isValid()) continue;
                audioSourceClient.remove();
            }
        }
        this.objectToAudioSourceMap.clear();
        this.singleSoundQueue.clear();
        this.soundSystem = (SoundEngine)ReflectionUtil.getValue(Minecraft.m_91087_().m_91106_(), SoundEngine.class);
        if (this.soundSystem == null) {
            IC2.log.warn(LogCategory.Audio, "Can't find SoundSystem, IC2 audio disabled.");
            this.enabled = false;
            return;
        }
        if (!this.valid()) {
            IC2.log.warn(LogCategory.Audio, "IC2 audio unavailable.");
            this.enabled = false;
            return;
        }
        Library library = (Library)ReflectionUtil.getValue(this.soundSystem, Library.class);
        if (this.audioEngine != null) {
            this.audioEngine.close();
        }
        this.audioEngine = new AudioEngine(library, (SoundEngineExecutor)ReflectionUtil.getValue(this.soundSystem, SoundEngineExecutor.class), this.maxSourceCount);
        IC2.log.debug(LogCategory.Audio, "IC2 audio ready.");
    }

    @Override
    public void onTick() {
        if (!this.enabled || !this.valid()) {
            return;
        }
        assert (IC2.sideProxy.isRendering());
        Player player = IC2.sideProxy.getPlayerInstance();
        if (player == null) {
            for (List<AudioSourceClient> list : this.objectToAudioSourceMap.values()) {
                AudioManagerClient.removeSources(list);
            }
            this.objectToAudioSourceMap.clear();
            this.singleSoundQueue.clear();
        } else {
            Iterator<Map.Entry<WeakObject, List<AudioSourceClient>>> iterator;
            boolean bl = Minecraft.m_91087_().m_91104_();
            if (!bl && !this.singleSoundQueue.isEmpty()) {
                iterator = this.singleSoundQueue.iterator();
                while (iterator.hasNext()) {
                    AudioSourceSingle entry2 = (AudioSourceSingle)((Object)iterator.next());
                    if (!entry2.isPlaying()) {
                        entry2.onFinish();
                        iterator.remove();
                        continue;
                    }
                    if (!entry2.isCancelled()) continue;
                    entry2.remove();
                    iterator.remove();
                }
            }
            iterator = this.objectToAudioSourceMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<WeakObject, List<AudioSourceClient>> entry = iterator.next();
                if (entry.getKey().get() == null) {
                    iterator.remove();
                    AudioManagerClient.removeSources(entry.getValue());
                    continue;
                }
                for (AudioSource audioSource : entry.getValue()) {
                    if (!this.wasPaused) {
                        audioSource.updateVolume(player);
                    }
                    if (!(audioSource.getRealVolume() > 0.0f)) continue;
                    this.validAudioSources.add(audioSource);
                }
            }
            if (!bl) {
                if (this.wasPaused) {
                    for (AudioSource audioSource : this.validAudioSources) {
                        audioSource.play();
                    }
                    this.wasPaused = false;
                }
                int n = 0;
                while (!this.validAudioSources.isEmpty()) {
                    AudioSource audioSource = this.validAudioSources.poll();
                    if (n < this.maxSourceCount) {
                        audioSource.activate();
                    } else {
                        audioSource.cull();
                    }
                    ++n;
                }
            } else if (bl != this.wasPaused) {
                this.wasPaused = true;
                while (!this.validAudioSources.isEmpty()) {
                    this.validAudioSources.poll().pause();
                }
            } else {
                assert (bl);
                assert (this.wasPaused);
                this.validAudioSources.clear();
            }
        }
    }

    @Override
    public AudioSource createSource(Object object, String string) {
        return this.createSource(object, PositionSpec.Center, string, false, false, this.getDefaultVolume());
    }

    @Override
    public AudioSource createSource(Object object, PositionSpec positionSpec, String string, boolean bl, boolean bl2, float f) {
        if (!this.enabled) {
            return null;
        }
        if (!this.valid()) {
            return null;
        }
        assert (IC2.sideProxy.isRendering());
        String string2 = AudioManagerClient.getSourceName(this.nextId);
        ++this.nextId;
        AudioSourceClient audioSourceClient = new AudioSourceClient(this.audioEngine, string2, object, positionSpec, string, bl, bl2, f);
        audioSourceClient.setup();
        WeakObject weakObject = new WeakObject(object);
        List<AudioSourceClient> list = this.objectToAudioSourceMap.get(weakObject);
        if (list == null) {
            list = new ArrayList<AudioSourceClient>();
            this.objectToAudioSourceMap.put(weakObject, list);
        }
        list.add(audioSourceClient);
        return audioSourceClient;
    }

    static URL getSourceURL(String string) {
        int n = string.indexOf(58);
        if (n > -1) {
            return AudioSource.class.getClassLoader().getResource("assets/" + string.substring(0, n) + "/sounds/" + string.substring(++n));
        }
        return AudioSource.class.getClassLoader().getResource("ic2/sounds/" + string);
    }

    @Override
    public void removeSources(Object object) {
        if (!this.valid()) {
            return;
        }
        assert (IC2.sideProxy.isRendering());
        WeakObject weakObject = object instanceof WeakObject ? (WeakObject)object : new WeakObject(object);
        List<AudioSourceClient> list = this.objectToAudioSourceMap.remove(weakObject);
        if (list == null) {
            return;
        }
        AudioManagerClient.removeSources(list);
    }

    private static void removeSources(List<AudioSourceClient> list) {
        for (AudioSourceClient audioSourceClient : list) {
            audioSourceClient.remove();
        }
    }

    @Override
    public SingleSound playOnce(Object object, String string) {
        return this.playOnce(object, PositionSpec.Center, string, true, this.getDefaultVolume());
    }

    @Override
    public SingleSound playOnce(Object object, PositionSpec positionSpec, String string, boolean bl, float f) {
        if (!this.enabled) {
            return null;
        }
        if (!this.valid()) {
            return null;
        }
        assert (IC2.sideProxy.isRendering());
        AudioPosition audioPosition = AudioPosition.getFrom(object, positionSpec);
        if (audioPosition == null) {
            return null;
        }
        AudioSourceSingle audioSourceSingle = new AudioSourceSingle(this.audioEngine, object, positionSpec, string, false, bl, f);
        audioSourceSingle.setup();
        audioSourceSingle.setVolume(this.masterVolume * Math.min(f, 1.0f));
        audioSourceSingle.play();
        this.singleSoundQueue.add(audioSourceSingle);
        this.objectToAudioSourceMap.computeIfAbsent(new WeakObject(object), weakObject -> new ArrayList()).add(audioSourceSingle);
        return audioSourceSingle;
    }

    @Override
    public float getDefaultVolume() {
        return 1.2f;
    }

    @Override
    public float getMasterVolume() {
        return this.masterVolume;
    }

    @Override
    protected boolean valid() {
        try {
            return this.soundSystem != null && this.soundSystem != null && soundSystemStarted.getBoolean(this.soundSystem);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static String getSourceName(int n) {
        return "asm_snd" + n;
    }

    public static class WeakObject
    extends WeakReference<Object> {
        public WeakObject(Object object) {
            super(object);
        }

        public boolean equals(Object object) {
            if (object instanceof WeakObject) {
                return ((WeakObject)object).get() == this.get();
            }
            return this.get() == object;
        }

        public int hashCode() {
            Object t = this.get();
            if (t == null) {
                return 0;
            }
            return t.hashCode();
        }
    }
}

