/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.sounds;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.sounds.SoundEvent;

public class Music {
    public static final Codec<Music> f_11620_ = RecordCodecBuilder.create(p_11635_ -> p_11635_.group((App)SoundEvent.f_11655_.fieldOf("sound").forGetter(p_144041_ -> p_144041_.f_11621_), (App)Codec.INT.fieldOf("min_delay").forGetter(p_144039_ -> p_144039_.f_11622_), (App)Codec.INT.fieldOf("max_delay").forGetter(p_144037_ -> p_144037_.f_11623_), (App)Codec.BOOL.fieldOf("replace_current_music").forGetter(p_144035_ -> p_144035_.f_11624_)).apply((Applicative)p_11635_, Music::new));
    private final SoundEvent f_11621_;
    private final int f_11622_;
    private final int f_11623_;
    private final boolean f_11624_;

    public Music(SoundEvent p_11627_, int p_11628_, int p_11629_, boolean p_11630_) {
        this.f_11621_ = p_11627_;
        this.f_11622_ = p_11628_;
        this.f_11623_ = p_11629_;
        this.f_11624_ = p_11630_;
    }

    public SoundEvent m_11631_() {
        return this.f_11621_;
    }

    public int m_11636_() {
        return this.f_11622_;
    }

    public int m_11639_() {
        return this.f_11623_;
    }

    public boolean m_11642_() {
        return this.f_11624_;
    }
}

