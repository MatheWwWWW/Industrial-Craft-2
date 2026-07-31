/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.EntityBoundSoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 */
package ic2.core.sound;

import ic2.core.IC2;
import ic2.core.sound.ListenableSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class EntityTrackingSoundInstance
extends EntityBoundSoundInstance
implements ListenableSoundInstance {
    protected Entity entity;
    protected SoundEvent soundEvent;
    protected SoundManager vanillaManager = Minecraft.m_91087_().m_91106_();
    private Runnable onFinish = null;

    public EntityTrackingSoundInstance(SoundEvent soundEvent, SoundSource soundSource, float f, float f2, Entity entity) {
        super(soundEvent, soundSource, f, f2, entity, IC2.random.m_188505_());
        this.f_119578_ = true;
        this.entity = entity;
        this.soundEvent = soundEvent;
    }

    public void playOnce() {
        this.entity.m_5496_(this.soundEvent, this.f_119573_, this.f_119574_);
    }

    @Override
    public void onFinish(Runnable runnable) {
        this.onFinish = this.onFinish == null ? runnable : () -> {
            this.onFinish.run();
            runnable.run();
        };
    }

    @Override
    public void finish() {
        if (this.onFinish != null) {
            this.onFinish.run();
        }
    }
}

