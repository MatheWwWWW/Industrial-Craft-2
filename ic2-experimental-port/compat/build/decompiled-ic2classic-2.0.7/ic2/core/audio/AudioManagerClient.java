/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.audio.Library$Pool
 *  com.mojang.blaze3d.audio.SoundBuffer
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectCollection
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.sounds.Sound
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.ChannelAccess
 *  net.minecraft.client.sounds.ChannelAccess$ChannelHandle
 *  net.minecraft.client.sounds.SoundBufferLibrary
 *  net.minecraft.client.sounds.SoundEngine
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.client.sounds.WeighedSoundEvents
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.EntityJoinLevelEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.audio;

import com.mojang.blaze3d.audio.Library;
import com.mojang.blaze3d.audio.SoundBuffer;
import ic2.core.IC2;
import ic2.core.audio.AudioEffect;
import ic2.core.audio.AudioManager;
import ic2.core.audio.AudioSourceClient;
import ic2.core.audio.IAudioPosition;
import ic2.core.audio.IAudioSource;
import ic2.core.audio.ISoundProvider;
import ic2.core.audio.providers.ProviderEntity;
import ic2.core.audio.providers.ProviderTileEntity;
import ic2.core.audio.providers.SimpleAudioProvider;
import ic2.core.audio.providers.SimplePosition;
import ic2.core.audio.tracker.BatteryTracker;
import ic2.core.audio.tracker.ChainsawTracker;
import ic2.core.audio.tracker.IAudioTracker;
import ic2.core.audio.tracker.JetpackTracker;
import ic2.core.audio.tracker.NanosaberTracker;
import ic2.core.platform.corehacks.mixins.client.audio.SoundEngineMixin;
import ic2.core.platform.corehacks.mixins.client.audio.SoundHandlerMixin;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(value=Dist.CLIENT)
public class AudioManagerClient
extends AudioManager {
    static final Function<ISoundProvider, List<AudioSourceClient>> CREATOR = T -> CollectionUtils.createList();
    ChannelAccess manager;
    SoundBufferLibrary loader;
    boolean enabled = true;
    boolean markForRemoval = false;
    List<IAudioTracker> trackers = CollectionUtils.createList();
    Long2ObjectMap<EnumMap<AudioManager.SoundType, AudioEffect>> modifiers = new Long2ObjectLinkedOpenHashMap();
    Object2ObjectMap<ISoundProvider, List<AudioSourceClient>> providers = CollectionUtils.createLinkedMap();
    RandomSource random = SoundInstance.m_235150_();
    boolean dirtyModifiers = false;

    @Override
    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public void onEntitySpawn(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player)entity;
            this.addTracker(new BatteryTracker(player));
            this.addTracker(new JetpackTracker(player));
            this.addTracker(new ChainsawTracker(player, IC2Items.CHAINSAW));
            this.addTracker(new ChainsawTracker(player, IC2Items.CHAINSAW_ADVANCED));
            this.addTracker(new NanosaberTracker(player));
        }
    }

    @Override
    public void addTracker(IAudioTracker tracker) {
        this.trackers.add(tracker);
    }

    @Override
    public void addModifier(BlockPos pos, EnumMap<AudioManager.SoundType, AudioEffect> modifiers) {
        if (modifiers.isEmpty()) {
            return;
        }
        this.modifiers.put(pos.m_121878_(), modifiers);
        this.dirtyModifiers = true;
    }

    @Override
    public void removeModifier(BlockPos pos) {
        this.modifiers.remove(pos.m_121878_());
        this.dirtyModifiers = true;
    }

    public boolean didModifiersChange() {
        return this.dirtyModifiers;
    }

    public float getModifier(AudioManager.SoundType type, Vec3 currentPosition) {
        float modifier = 1.0f;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(this.modifiers)) {
            AudioEffect effect = (AudioEffect)((EnumMap)entry.getValue()).get(type);
            if (effect == null) continue;
            mutable.m_122188_(entry.getLongKey());
            if (!(Math.sqrt(mutable.m_203193_((Position)currentPosition)) <= (double)(effect.getRadius() + 0.5f)) || !((modifier *= effect.getEffect()) <= 0.0f)) continue;
            return 0.0f;
        }
        return modifier;
    }

    public boolean isValid() {
        return this.enabled && this.manager != null && this.loader != null;
    }

    @Override
    public IAudioSource createSource(Object obj, ResourceLocation location) {
        return this.createSource(obj, location, AudioManager.SoundType.STATIC, 1.0f, true, false);
    }

    @Override
    public IAudioSource createSource(Object obj, ResourceLocation location, AudioManager.SoundType type, float volume, boolean loop, boolean priority) {
        if (!this.enabled || location == null || Math.min(this.getMasterVolume(), Math.min(this.getVolumeForType(type), volume)) <= 0.0f) {
            return null;
        }
        if (!this.isValid()) {
            this.getSoundSystem();
            if (!this.isValid()) {
                return null;
            }
        }
        location = this.convertVanilla(location);
        if (Minecraft.m_91087_().m_91098_().m_213713_(location).isEmpty()) {
            IC2.LOGGER.info("Couldn't find the Sound File: " + location);
            return null;
        }
        ISoundProvider provider = this.createProvider(obj, type);
        if (provider == null) {
            IC2.LOGGER.info("Couldn't create a Provider: " + obj);
            return null;
        }
        AudioSourceClient sound = new AudioSourceClient(this, location, provider.getPosition(), type, volume, 1.0f, loop, priority);
        ((List)this.providers.computeIfAbsent((Object)provider, CREATOR)).add(sound);
        return sound;
    }

    @Override
    public void playSound(Object obj, ResourceLocation location) {
        this.playSound(obj, location, AudioManager.SoundType.STATIC);
    }

    @Override
    public void playSound(Object obj, ResourceLocation location, AudioManager.SoundType type) {
        this.playSound(obj, location, type, 1.0f, 1.0f);
    }

    @Override
    public void playSound(Object obj, ResourceLocation location, AudioManager.SoundType type, float volume, float pitch) {
        if (!this.enabled || location == null || Math.min(this.getMasterVolume(), Math.min(this.getVolumeForType(type), volume)) <= 0.0f) {
            return;
        }
        if (!this.isValid()) {
            this.getSoundSystem();
            if (!this.isValid()) {
                return;
            }
        }
        volume *= this.getMasterVolume();
        volume *= this.getVolumeForType(type);
        ResourceLocation final_location = this.convertVanilla(location);
        if (Minecraft.m_91087_().m_91098_().m_213713_(final_location).isEmpty()) {
            IC2.LOGGER.info("Couldn't find the Sound File: " + location);
            return;
        }
        ISoundProvider provider = this.createProvider(obj, type);
        if (provider == null) {
            IC2.LOGGER.info("Couldn't create a Provider: " + obj);
            return;
        }
        float finalVolume = volume *= this.getModifier(type, provider.getPosition().getPosition());
        this.createEntry().thenAccept(T -> T.m_120154_(source -> {
            source.m_83656_(this.createBuffer(final_location));
            source.m_83663_(false);
            source.m_83673_(16.0f * Math.max(0.2f, finalVolume));
            source.m_83670_(false);
            source.m_83654_(provider.getPosition().getPosition());
            source.m_83666_(finalVolume);
            source.m_83650_(pitch);
            source.m_83672_();
        }));
    }

    public ResourceLocation convertVanilla(ResourceLocation location) {
        WeighedSoundEvents event = Minecraft.m_91087_().m_91106_().m_120384_(location);
        if (event == null) {
            return location;
        }
        Sound sound = event.m_213718_(this.random);
        return sound == SoundManager.f_120344_ ? location : sound.m_119790_();
    }

    private ISoundProvider createProvider(Object obj, AudioManager.SoundType spec) {
        if (obj instanceof ISoundProvider) {
            return (ISoundProvider)obj;
        }
        if (obj instanceof SimplePosition) {
            return new SimpleAudioProvider((IAudioPosition)obj);
        }
        if (obj instanceof BlockEntity) {
            return new ProviderTileEntity((BlockEntity)obj);
        }
        if (obj instanceof Entity) {
            return new ProviderEntity((Entity)obj, spec);
        }
        return null;
    }

    @Override
    public float getMasterVolume() {
        return (float)IC2.CONFIG.masterVolume.get();
    }

    @Override
    public float getVolumeForType(AudioManager.SoundType type) {
        switch (type) {
            case BACKPACK: {
                return (float)IC2.CONFIG.backVolume.get();
            }
            case ITEM: {
                return (float)IC2.CONFIG.itemVolume.get();
            }
            case STATIC: {
                return (float)IC2.CONFIG.blockVolume.get();
            }
        }
        return 1.0f;
    }

    @Override
    public void removeSource(Object obj) {
        if (!this.isValid()) {
            return;
        }
        List clients = (List)this.providers.remove((Object)this.createProvider(obj, AudioManager.SoundType.STATIC));
        if (clients == null) {
            return;
        }
        for (AudioSourceClient client : clients) {
            client.remove();
        }
    }

    @Override
    public void resetAll() {
        this.markForRemoval = true;
    }

    public void onTick() {
        Minecraft minecraft = Minecraft.m_91087_();
        ClientLevel world = minecraft.f_91073_;
        LocalPlayer player = minecraft.f_91074_;
        if (!this.isValid()) {
            return;
        }
        if (this.markForRemoval || world == null || player == null) {
            for (ISoundProvider prov : new ObjectArrayList((ObjectCollection)this.providers.keySet())) {
                this.removeSource(prov);
            }
            this.markForRemoval = false;
            return;
        }
        ObjectArrayList toRemove = new ObjectArrayList();
        ObjectArrayList sounds = new ObjectArrayList();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.providers)) {
            if (!((ISoundProvider)entry.getKey()).isValid((Level)world) || ((List)entry.getValue()).isEmpty()) {
                toRemove.add((ISoundProvider)entry.getKey());
                continue;
            }
            Iterator iter = ((List)entry.getValue()).iterator();
            while (iter.hasNext()) {
                AudioSourceClient sound = (AudioSourceClient)iter.next();
                if (!sound.isValid()) {
                    iter.remove();
                    continue;
                }
                sound.updatePosition();
                sound.updateVolume((Player)player);
                if (!sound.isEnabled() || !sound.isPlaying() && !sound.isPaused() || !sound.getPosition().isInSameWorld((Level)world)) continue;
                sounds.add(sound);
            }
        }
        this.dirtyModifiers = false;
        for (ISoundProvider prov : toRemove) {
            this.removeSource(prov);
        }
        sounds.sort(AudioSorter.SORTER);
        int index = 0;
        for (AudioSourceClient client : sounds) {
            if (index < 20) {
                client.ensureValid();
            } else {
                client.disable();
            }
            ++index;
        }
    }

    public void onPostTick() {
        ClientLevel world = Minecraft.m_91087_().f_91073_;
        Iterator<IAudioTracker> iter = this.trackers.iterator();
        while (iter.hasNext()) {
            IAudioTracker tracker = iter.next();
            if (tracker.isValid((Level)world)) {
                tracker.onTick();
                continue;
            }
            iter.remove();
        }
    }

    public void getSoundSystem() {
        SoundEngine engine = ((SoundHandlerMixin)Minecraft.m_91087_().m_91106_()).getSoundEngine();
        if (engine == null) {
            return;
        }
        this.manager = ((SoundEngineMixin)engine).getChannels();
        this.loader = ((SoundEngineMixin)engine).getAudioBuffers();
    }

    protected CompletableFuture<ChannelAccess.ChannelHandle> createEntry() {
        return this.manager.m_120128_(Library.Pool.STATIC);
    }

    protected SoundBuffer createBuffer(ResourceLocation location) {
        return (SoundBuffer)this.loader.m_120202_(location).join();
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class AudioSorter
    implements Comparator<AudioSourceClient> {
        public static final AudioSorter SORTER = new AudioSorter();

        @Override
        public int compare(AudioSourceClient o1, AudioSourceClient o2) {
            int result = Boolean.compare(o2.isPriority(), o1.isPriority());
            return result != 0 ? result : Float.compare(o2.getRealVolume(), o1.getRealVolume());
        }
    }
}

