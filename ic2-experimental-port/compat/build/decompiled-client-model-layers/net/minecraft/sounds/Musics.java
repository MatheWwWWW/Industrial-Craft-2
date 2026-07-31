/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.sounds;

import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class Musics {
    private static final int f_144042_ = 20;
    private static final int f_144043_ = 600;
    private static final int f_144044_ = 12000;
    private static final int f_144045_ = 24000;
    private static final int f_144046_ = 6000;
    public static final Music f_11645_ = new Music(SoundEvents.f_12153_, 20, 600, true);
    public static final Music f_11646_ = new Music(SoundEvents.f_12082_, 12000, 24000, false);
    public static final Music f_11647_ = new Music(SoundEvents.f_12083_, 0, 0, true);
    public static final Music f_11648_ = new Music(SoundEvents.f_12150_, 0, 0, true);
    public static final Music f_11649_ = new Music(SoundEvents.f_12151_, 6000, 24000, true);
    public static final Music f_11650_ = Musics.m_11653_(SoundEvents.f_12159_);
    public static final Music f_11651_ = Musics.m_11653_(SoundEvents.f_12152_);

    public static Music m_11653_(SoundEvent p_11654_) {
        return new Music(p_11654_, 12000, 24000, false);
    }
}

