/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.sounds;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public class SoundEvent {
    public static final Codec<SoundEvent> f_11655_ = ResourceLocation.f_135803_.xmap(SoundEvent::new, p_11662_ -> p_11662_.f_11656_);
    private final ResourceLocation f_11656_;
    private final float f_215659_;
    private final boolean f_215660_;

    public SoundEvent(ResourceLocation p_11659_) {
        this(p_11659_, 16.0f, false);
    }

    public SoundEvent(ResourceLocation p_215662_, float p_215663_) {
        this(p_215662_, p_215663_, true);
    }

    private SoundEvent(ResourceLocation p_215665_, float p_215666_, boolean p_215667_) {
        this.f_11656_ = p_215665_;
        this.f_215659_ = p_215666_;
        this.f_215660_ = p_215667_;
    }

    public ResourceLocation m_11660_() {
        return this.f_11656_;
    }

    public float m_215668_(float p_215669_) {
        if (this.f_215660_) {
            return this.f_215659_;
        }
        return p_215669_ > 1.0f ? 16.0f * p_215669_ : 16.0f;
    }
}

