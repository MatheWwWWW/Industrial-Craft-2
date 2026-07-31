/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.sounds;

import javax.annotation.Nullable;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.Weighted;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.SampledFloat;

public class Sound
implements Weighted<Sound> {
    private final ResourceLocation f_119770_;
    private final SampledFloat f_119771_;
    private final SampledFloat f_119772_;
    private final int f_119773_;
    private final Type f_119774_;
    private final boolean f_119775_;
    private final boolean f_119776_;
    private final int f_119777_;

    public Sound(String p_235134_, SampledFloat p_235135_, SampledFloat p_235136_, int p_235137_, Type p_235138_, boolean p_235139_, boolean p_235140_, int p_235141_) {
        this.f_119770_ = new ResourceLocation(p_235134_);
        this.f_119771_ = p_235135_;
        this.f_119772_ = p_235136_;
        this.f_119773_ = p_235137_;
        this.f_119774_ = p_235138_;
        this.f_119775_ = p_235139_;
        this.f_119776_ = p_235140_;
        this.f_119777_ = p_235141_;
    }

    public ResourceLocation m_119787_() {
        return this.f_119770_;
    }

    public ResourceLocation m_119790_() {
        return new ResourceLocation(this.f_119770_.m_135827_(), "sounds/" + this.f_119770_.m_135815_() + ".ogg");
    }

    public SampledFloat m_235146_() {
        return this.f_119771_;
    }

    public SampledFloat m_235147_() {
        return this.f_119772_;
    }

    @Override
    public int m_7789_() {
        return this.f_119773_;
    }

    @Override
    public Sound m_213718_(RandomSource p_235143_) {
        return this;
    }

    @Override
    public void m_8054_(SoundEngine p_119789_) {
        if (this.f_119776_) {
            p_119789_.m_120272_(this);
        }
    }

    public Type m_119795_() {
        return this.f_119774_;
    }

    public boolean m_119796_() {
        return this.f_119775_;
    }

    public boolean m_119797_() {
        return this.f_119776_;
    }

    public int m_119798_() {
        return this.f_119777_;
    }

    public String toString() {
        return "Sound[" + this.f_119770_ + "]";
    }

    @Override
    public /* synthetic */ Object m_213718_(RandomSource randomSource) {
        return this.m_213718_(randomSource);
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type FILE = new Type("file");
        public static final /* enum */ Type SOUND_EVENT = new Type("event");
        private final String f_119803_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_119813_) {
            return Enum.valueOf(Type.class, p_119813_);
        }

        private Type(String p_119809_) {
            this.f_119803_ = p_119809_;
        }

        @Nullable
        public static Type m_119810_(String p_119811_) {
            for (Type $$1 : Type.values()) {
                if (!$$1.f_119803_.equals(p_119811_)) continue;
                return $$1;
            }
            return null;
        }

        private static /* synthetic */ Type[] m_174943_() {
            return new Type[]{FILE, SOUND_EVENT};
        }

        static {
            $VALUES = Type.m_174943_();
        }
    }
}

