/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 */
package ic2.core.sound;

import ic2.core.sound.PositionedSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class RepeatablePositionedSoundInstance
extends PositionedSoundInstance {
    public RepeatablePositionedSoundInstance(SoundEvent soundEvent, SoundSource soundSource, float f, float f2, BlockPos blockPos) {
        super(soundEvent, soundSource, f, f2, blockPos);
        this.f_119578_ = true;
    }
}

