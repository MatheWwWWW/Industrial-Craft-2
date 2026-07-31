/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 */
package ic2.core.sound;

import ic2.core.IC2;
import ic2.core.sound.ListenableSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class PositionedSoundInstance
extends SimpleSoundInstance
implements ListenableSoundInstance {
    private Runnable onFinish = null;

    public PositionedSoundInstance(SoundEvent soundEvent, SoundSource soundSource, float f, float f2, BlockPos blockPos) {
        super(soundEvent, soundSource, f, f2, IC2.random, blockPos);
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

