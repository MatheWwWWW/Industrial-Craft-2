/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.LivingEntity
 */
package ic2.core.sound;

import ic2.core.sound.Sound;
import ic2.core.sound.SoundManagerClient;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public class SoundManager {
    public Sound createSound(Object object, SoundEvent soundEvent, SoundSource soundSource, LivingEntity livingEntity, float f, float f2) {
        return null;
    }

    public Sound createSound(Object object, SoundEvent soundEvent, SoundSource soundSource, BlockPos blockPos, float f, float f2) {
        return null;
    }

    public void playOnce(SoundEvent soundEvent, SoundSource soundSource, float f, float f2, LivingEntity livingEntity) {
    }

    public void pauseAll() {
    }

    public void resumeAll() {
    }

    public void stopAll() {
    }

    public SoundManagerClient.WeakObject stopAll(Object object) {
        return null;
    }

    public void removeAllSound(Object object) {
    }

    public void removeSound(Object object, Sound sound) {
    }

    public void tick() {
    }
}

