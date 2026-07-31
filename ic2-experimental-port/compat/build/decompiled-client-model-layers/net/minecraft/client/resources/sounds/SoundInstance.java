/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.sounds;

import javax.annotation.Nullable;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public interface SoundInstance {
    public ResourceLocation m_7904_();

    @Nullable
    public WeighedSoundEvents m_6775_(SoundManager var1);

    public Sound m_5891_();

    public SoundSource m_8070_();

    public boolean m_7775_();

    public boolean m_7796_();

    public int m_7766_();

    public float m_7769_();

    public float m_7783_();

    public double m_7772_();

    public double m_7780_();

    public double m_7778_();

    public Attenuation m_7438_();

    default public boolean m_7784_() {
        return false;
    }

    default public boolean m_7767_() {
        return true;
    }

    public static RandomSource m_235150_() {
        return RandomSource.m_216327_();
    }

    public static final class Attenuation
    extends Enum<Attenuation> {
        public static final /* enum */ Attenuation NONE = new Attenuation();
        public static final /* enum */ Attenuation LINEAR = new Attenuation();
        private static final /* synthetic */ Attenuation[] $VALUES;

        public static Attenuation[] values() {
            return (Attenuation[])$VALUES.clone();
        }

        public static Attenuation valueOf(String p_119850_) {
            return Enum.valueOf(Attenuation.class, p_119850_);
        }

        private static /* synthetic */ Attenuation[] m_174956_() {
            return new Attenuation[]{NONE, LINEAR};
        }

        static {
            $VALUES = Attenuation.m_174956_();
        }
    }
}

