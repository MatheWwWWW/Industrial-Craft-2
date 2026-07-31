/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package ic2.core.sound;

import ic2.core.sound.EntityTrackingSoundInstance;
import ic2.core.sound.ListenableSoundInstance;
import ic2.core.sound.PositionedSoundInstance;
import ic2.core.sound.RepeatablePositionedSoundInstance;
import ic2.core.sound.Sound;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SoundClient
extends Sound {
    private RepeatablePositionedSoundInstance repeatInstance = null;
    private PositionedSoundInstance onceInstance = null;
    private EntityTrackingSoundInstance entityTrackingInstance = null;
    private final List<SoundInstance> startedSoundList = new ArrayList<SoundInstance>();
    private boolean isStarted = false;
    public SoundManager vanillaManager = Minecraft.m_91087_().m_91106_();

    protected SoundClient() {
    }

    public SoundClient(SoundEvent soundEvent, SoundSource soundSource, BlockPos blockPos, float f, float f2) {
        this();
        this.repeatInstance = new RepeatablePositionedSoundInstance(soundEvent, soundSource, f, f2, blockPos);
        this.onceInstance = new PositionedSoundInstance(soundEvent, soundSource, f, f2, blockPos);
    }

    public SoundClient(SoundEvent soundEvent, SoundSource soundSource, LivingEntity livingEntity, float f, float f2) {
        this();
        this.entityTrackingInstance = new EntityTrackingSoundInstance(soundEvent, soundSource, f, f2, (Entity)livingEntity);
    }

    @Override
    public void play() {
        super.play();
        if (this.repeatInstance != null && !this.vanillaManager.m_120403_((SoundInstance)this.repeatInstance)) {
            if (this.vanillaManager.m_120403_((SoundInstance)this.onceInstance)) {
                this.vanillaManager.m_120399_((SoundInstance)this.onceInstance);
            }
            this.vanillaManager.m_120367_((SoundInstance)this.repeatInstance);
            this.startedSoundList.add((SoundInstance)this.repeatInstance);
        }
        if (this.entityTrackingInstance != null && !this.vanillaManager.m_120403_((SoundInstance)this.entityTrackingInstance)) {
            this.vanillaManager.m_120367_((SoundInstance)this.entityTrackingInstance);
            this.startedSoundList.add((SoundInstance)this.entityTrackingInstance);
        }
        this.isStarted = true;
    }

    @Override
    public void playOnce() {
        super.playOnce();
        if (this.onceInstance != null) {
            this.vanillaManager.m_120367_((SoundInstance)this.onceInstance);
            this.startedSoundList.add((SoundInstance)this.onceInstance);
        }
        if (this.entityTrackingInstance != null) {
            this.entityTrackingInstance.playOnce();
            this.startedSoundList.add((SoundInstance)this.entityTrackingInstance);
        }
        this.isStarted = true;
    }

    @Override
    public void stop() {
        super.stop();
        this.isStarted = false;
        this.vanillaManager.m_120399_((SoundInstance)this.repeatInstance);
        this.vanillaManager.m_120399_((SoundInstance)this.onceInstance);
        this.vanillaManager.m_120399_((SoundInstance)this.entityTrackingInstance);
    }

    private boolean isPlayingSound(SoundInstance soundInstance) {
        return soundInstance != null && this.vanillaManager.m_120403_(soundInstance);
    }

    @Override
    public boolean isPlaying() {
        return this.isPlayingSound((SoundInstance)this.onceInstance) || this.isPlayingSound((SoundInstance)this.repeatInstance) || this.isPlayingSound((SoundInstance)this.entityTrackingInstance);
    }

    private void onFinishSound(ListenableSoundInstance listenableSoundInstance, Runnable runnable) {
        if (listenableSoundInstance != null) {
            listenableSoundInstance.onFinish(runnable);
        }
    }

    private void checkSoundFinished(SoundInstance soundInstance) {
        if (!(soundInstance instanceof ListenableSoundInstance)) {
            return;
        }
        ListenableSoundInstance listenableSoundInstance = (ListenableSoundInstance)soundInstance;
        if (!this.vanillaManager.m_120403_(soundInstance) && this.startedSoundList.contains(soundInstance)) {
            listenableSoundInstance.finish();
            this.startedSoundList.remove(soundInstance);
        }
    }

    @Override
    public void onFinish(Runnable runnable) {
        this.onFinishSound(this.onceInstance, runnable);
        this.onFinishSound(this.repeatInstance, runnable);
        this.onFinishSound(this.entityTrackingInstance, runnable);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isStarted) {
            return;
        }
        this.checkSoundFinished((SoundInstance)this.onceInstance);
        this.checkSoundFinished((SoundInstance)this.repeatInstance);
        this.checkSoundFinished((SoundInstance)this.entityTrackingInstance);
        if (this.startedSoundList.isEmpty()) {
            this.isStarted = false;
        }
    }
}

