/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.SoundHandler
 *  net.minecraft.client.audio.SoundManager
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.sound.PlaySoundEvent
 *  net.minecraftforge.client.event.sound.SoundLoadEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  paulscode.sound.SoundSystem
 *  paulscode.sound.SoundSystemConfig
 */
package ic2.core.audio;

import ic2.core.IC2;
import ic2.core.IHitSoundOverride;
import ic2.core.audio.AudioConfigHandler;
import ic2.core.audio.AudioManager;
import ic2.core.audio.AudioPosition;
import ic2.core.audio.AudioSource;
import ic2.core.audio.AudioSourceClient;
import ic2.core.audio.FutureSound;
import ic2.core.audio.PositionSpec;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

@SideOnly(value=Side.CLIENT)
public final class AudioManagerClient
extends AudioManager {
    public float fadingDistance = 16.0f;
    private boolean enabled = true;
    private boolean wasPaused = false;
    private int maxSourceCount = 32;
    private final int streamingSourceCount = 4;
    private SoundManager soundManager;
    private Field soundManagerLoaded;
    private volatile Thread initThread;
    private SoundSystem soundSystem = null;
    float masterVolume = 0.5f;
    private int nextId = 0;
    private final Map<WeakObject, List<AudioSourceClient>> objectToAudioSourceMap = new HashMap<WeakObject, List<AudioSourceClient>>();
    private final Queue<AudioSource> validAudioSources = new PriorityQueue<AudioSource>();
    private final Map<String, FutureSound> singleSoundQueue = new HashMap<String, FutureSound>();

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
        if (this.maxSourceCount < 6) {
            this.enabled = false;
            return;
        }
        IC2.log.debug(LogCategory.Audio, "Using %d audio sources.", this.maxSourceCount);
        SoundSystemConfig.setNumberStreamingChannels((int)4);
        SoundSystemConfig.setNumberNormalChannels((int)(this.maxSourceCount - 4));
        this.soundManagerLoaded = ReflectionUtil.getField(SoundManager.class, Boolean.TYPE);
        if (this.soundManagerLoaded == null) {
            IC2.log.warn(LogCategory.Audio, "Can't find SoundManager.loaded, IC2 audio disabled.");
            this.enabled = false;
            return;
        }
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.register(AudioConfigHandler.class);
    }

    @SubscribeEvent
    public void onSoundSetup(SoundLoadEvent event) {
        if (!this.enabled) {
            return;
        }
        for (List<AudioSourceClient> sources : this.objectToAudioSourceMap.values()) {
            for (AudioSourceClient source : sources) {
                if (!source.isValid()) continue;
                source.setInvalid();
            }
        }
        this.objectToAudioSourceMap.clear();
        Thread thread = this.initThread;
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        IC2.log.debug(LogCategory.Audio, "IC2 audio starting.");
        this.soundSystem = null;
        this.soundManager = AudioManagerClient.getSoundManager();
        this.initThread = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        boolean loaded;
                        try {
                            loaded = AudioManagerClient.this.soundManagerLoaded.getBoolean(AudioManagerClient.this.soundManager);
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        if (loaded) {
                            AudioManagerClient.this.soundSystem = AudioManagerClient.getSoundSystem(AudioManagerClient.this.soundManager);
                            if (AudioManagerClient.this.soundSystem == null) {
                                IC2.log.warn(LogCategory.Audio, "IC2 audio unavailable.");
                                AudioManagerClient.this.enabled = false;
                            } else {
                                IC2.log.debug(LogCategory.Audio, "IC2 audio ready.");
                            }
                            break;
                        }
                        Thread.sleep(100L);
                    }
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                AudioManagerClient.this.initThread = null;
            }
        }, "IC2 audio init thread");
        this.initThread.setDaemon(true);
        this.initThread.start();
    }

    private static SoundManager getSoundManager() {
        SoundHandler handler = Minecraft.func_71410_x().func_147118_V();
        return (SoundManager)ReflectionUtil.getValue(handler, SoundManager.class);
    }

    private static SoundSystem getSoundSystem(SoundManager soundManager) {
        try {
            return (SoundSystem)ReflectionUtil.getValueRecursive(soundManager, SoundSystem.class, false);
        }
        catch (NoSuchFieldException e) {
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onTick() {
        if (!this.enabled || !this.valid()) {
            return;
        }
        assert (IC2.platform.isRendering());
        IC2.platform.profilerStartSection("UpdateSourceVolume");
        EntityPlayer player = IC2.platform.getPlayerInstance();
        if (player == null) {
            for (List<AudioSourceClient> sources : this.objectToAudioSourceMap.values()) {
                AudioManagerClient.removeSources(sources);
            }
            this.objectToAudioSourceMap.clear();
            this.singleSoundQueue.clear();
        } else {
            Map.Entry<Object, Object> entry;
            Iterator it;
            boolean isPaused = Minecraft.func_71410_x().func_147113_T();
            if (!isPaused && !this.singleSoundQueue.isEmpty()) {
                IC2.platform.profilerStartSection("SoundQueuing");
                it = this.singleSoundQueue.entrySet().iterator();
                while (it.hasNext()) {
                    entry = it.next();
                    if (!this.soundSystem.playing((String)entry.getKey())) {
                        ((FutureSound)entry.getValue()).onFinish();
                        it.remove();
                        continue;
                    }
                    if (!((FutureSound)entry.getValue()).isCancelled()) continue;
                    this.removeSource((String)entry.getKey());
                    it.remove();
                }
                it = SoundSystemConfig.THREAD_SYNC;
                synchronized (it) {
                    IC2.platform.profilerEndSection();
                }
            }
            it = this.objectToAudioSourceMap.entrySet().iterator();
            while (it.hasNext()) {
                entry = it.next();
                if (entry.getKey().get() == null) {
                    it.remove();
                    AudioManagerClient.removeSources((List)entry.getValue());
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
            IC2.platform.profilerEndStartSection("Culling");
            if (!isPaused) {
                if (this.wasPaused) {
                    for (AudioSource source : this.validAudioSources) {
                        source.play();
                    }
                    this.wasPaused = false;
                }
                int i = 0;
                while (!this.validAudioSources.isEmpty()) {
                    AudioSource source;
                    source = this.validAudioSources.poll();
                    if (i < this.maxSourceCount) {
                        source.activate();
                    } else {
                        source.cull();
                    }
                    ++i;
                }
            } else if (isPaused != this.wasPaused) {
                this.wasPaused = true;
                while (!this.validAudioSources.isEmpty()) {
                    this.validAudioSources.poll().pause();
                }
            } else {
                assert (isPaused);
                assert (this.wasPaused);
                this.validAudioSources.clear();
            }
        }
        IC2.platform.profilerEndSection();
    }

    @Override
    public AudioSource createSource(Object obj, String initialSoundFile) {
        return this.createSource(obj, PositionSpec.Center, initialSoundFile, false, false, this.getDefaultVolume());
    }

    @Override
    public AudioSource createSource(Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, float volume) {
        if (!this.enabled) {
            return null;
        }
        if (!this.valid()) {
            return null;
        }
        assert (IC2.platform.isRendering());
        String sourceName = AudioManagerClient.getSourceName(this.nextId);
        ++this.nextId;
        AudioSourceClient audioSource = new AudioSourceClient(this.soundSystem, sourceName, obj, positionSpec, initialSoundFile, loop, priorized, volume);
        audioSource.setup();
        WeakObject key = new WeakObject(obj);
        List<AudioSourceClient> sources = this.objectToAudioSourceMap.get(key);
        if (sources == null) {
            sources = new ArrayList<AudioSourceClient>();
            this.objectToAudioSourceMap.put(key, sources);
        }
        sources.add(audioSource);
        return audioSource;
    }

    static URL getSourceURL(String soundFile) {
        int colonIndex = soundFile.indexOf(58);
        if (colonIndex > -1) {
            return AudioSource.class.getClassLoader().getResource("assets/" + soundFile.substring(0, colonIndex) + "/sounds/" + soundFile.substring(++colonIndex));
        }
        return AudioSource.class.getClassLoader().getResource("ic2/sounds/" + soundFile);
    }

    @Override
    public void removeSources(Object obj) {
        if (!this.valid()) {
            return;
        }
        assert (IC2.platform.isRendering());
        WeakObject key = obj instanceof WeakObject ? (WeakObject)obj : new WeakObject(obj);
        List<AudioSourceClient> sources = this.objectToAudioSourceMap.remove(key);
        if (sources == null) {
            return;
        }
        AudioManagerClient.removeSources(sources);
    }

    private static void removeSources(List<AudioSourceClient> sources) {
        for (AudioSourceClient audioSource : sources) {
            audioSource.remove();
        }
    }

    @Override
    public void playOnce(Object obj, String soundFile) {
        this.playOnce(obj, PositionSpec.Center, soundFile, true, this.getDefaultVolume());
    }

    @Override
    public String playOnce(Object obj, PositionSpec positionSpec, String soundFile, boolean priorized, float volume) {
        if (!this.enabled) {
            return null;
        }
        if (!this.valid()) {
            return null;
        }
        assert (IC2.platform.isRendering());
        AudioPosition position = AudioPosition.getFrom(obj, positionSpec);
        if (position == null) {
            return null;
        }
        URL url = AudioManagerClient.getSourceURL(soundFile);
        if (url == null) {
            IC2.log.warn(LogCategory.Audio, "Invalid sound file: %s.", soundFile);
            return null;
        }
        String sourceName = this.soundSystem.quickPlay(priorized, url, soundFile, false, position.x, position.y, position.z, 2, this.fadingDistance * Math.max(volume, 1.0f));
        this.soundSystem.setVolume(sourceName, this.masterVolume * Math.min(volume, 1.0f));
        return sourceName;
    }

    @Override
    public void chainSource(String source, FutureSound onFinish) {
        this.singleSoundQueue.put(source, onFinish);
    }

    @Override
    public void removeSource(String source) {
        if (source != null) {
            this.soundSystem.stop(source);
            this.soundSystem.removeSource(source);
        }
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
            return this.soundSystem != null && this.soundManager != null && this.soundManagerLoaded.getBoolean(this.soundManager);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public void onSoundPlayed(PlaySoundEvent event) {
        SoundCategory category = event.getSound().func_184365_d();
        if (category == SoundCategory.NEUTRAL && event.getName().endsWith(".hit") || category == SoundCategory.BLOCKS && event.getName().endsWith(".break")) {
            EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
            ItemStack stack = player.field_71071_by.func_70448_g();
            if (stack != null && stack.func_77973_b() instanceof IHitSoundOverride) {
                String replace;
                World world = player.func_130014_f_();
                RayTraceResult mop = AudioManagerClient.getMovingObjectPositionFromPlayer(world, (EntityPlayer)player, false);
                BlockPos pos = new BlockPos((double)event.getSound().func_147649_g(), (double)event.getSound().func_147654_h(), (double)event.getSound().func_147651_i());
                if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK && pos.equals((Object)mop.func_178782_a()) && (replace = event.getSound().func_184365_d() == SoundCategory.NEUTRAL ? ((IHitSoundOverride)stack.func_77973_b()).getHitSoundForBlock(player, world, pos, stack) : ((IHitSoundOverride)stack.func_77973_b()).getBreakSoundForBlock(player, world, pos, stack)) != null) {
                    event.setResultSound(null);
                    if (!replace.isEmpty()) {
                        IC2.platform.playSoundSp(replace, 1.0f, 1.0f);
                    }
                }
            }
        }
    }

    private static RayTraceResult getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
        float f = playerIn.field_70125_A;
        float f1 = playerIn.field_70177_z;
        double d0 = playerIn.field_70165_t;
        double d1 = playerIn.field_70163_u + (double)playerIn.func_70047_e();
        double d2 = playerIn.field_70161_v;
        Vec3d vec3 = new Vec3d(d0, d1, d2);
        float f2 = MathHelper.func_76134_b((float)(-f1 * ((float)Math.PI / 180) - (float)Math.PI));
        float f3 = MathHelper.func_76126_a((float)(-f1 * ((float)Math.PI / 180) - (float)Math.PI));
        float f4 = -MathHelper.func_76134_b((float)(-f * ((float)Math.PI / 180)));
        float f5 = MathHelper.func_76126_a((float)(-f * ((float)Math.PI / 180)));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d3 = 5.0;
        Vec3d vec31 = vec3.func_72441_c((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
        return worldIn.func_147447_a(vec3, vec31, useLiquids, !useLiquids, false);
    }

    private static String getSourceName(int id) {
        return "asm_snd" + id;
    }

    public static class WeakObject
    extends WeakReference<Object> {
        public WeakObject(Object referent) {
            super(referent);
        }

        public boolean equals(Object object) {
            if (object instanceof WeakObject) {
                return ((WeakObject)object).get() == this.get();
            }
            return this.get() == object;
        }

        public int hashCode() {
            Object object = this.get();
            if (object == null) {
                return 0;
            }
            return object.hashCode();
        }
    }
}

