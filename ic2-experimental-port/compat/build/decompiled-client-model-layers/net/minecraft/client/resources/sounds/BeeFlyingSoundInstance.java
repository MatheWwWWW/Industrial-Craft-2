/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.BeeAggressiveSoundInstance;
import net.minecraft.client.resources.sounds.BeeSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Bee;

public class BeeFlyingSoundInstance
extends BeeSoundInstance {
    public BeeFlyingSoundInstance(Bee p_119615_) {
        super(p_119615_, SoundEvents.f_11691_, SoundSource.NEUTRAL);
    }

    @Override
    protected AbstractTickableSoundInstance m_5958_() {
        return new BeeAggressiveSoundInstance(this.f_119618_);
    }

    @Override
    protected boolean m_7774_() {
        return this.f_119618_.m_21660_();
    }
}

