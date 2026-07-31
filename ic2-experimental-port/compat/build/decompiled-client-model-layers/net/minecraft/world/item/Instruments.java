/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Instrument;

public interface Instruments {
    public static final int f_220137_ = 256;
    public static final int f_220138_ = 140;
    public static final ResourceKey<Instrument> f_220139_ = Instruments.m_220150_("ponder_goat_horn");
    public static final ResourceKey<Instrument> f_220140_ = Instruments.m_220150_("sing_goat_horn");
    public static final ResourceKey<Instrument> f_220141_ = Instruments.m_220150_("seek_goat_horn");
    public static final ResourceKey<Instrument> f_220142_ = Instruments.m_220150_("feel_goat_horn");
    public static final ResourceKey<Instrument> f_220143_ = Instruments.m_220150_("admire_goat_horn");
    public static final ResourceKey<Instrument> f_220144_ = Instruments.m_220150_("call_goat_horn");
    public static final ResourceKey<Instrument> f_220145_ = Instruments.m_220150_("yearn_goat_horn");
    public static final ResourceKey<Instrument> f_220146_ = Instruments.m_220150_("dream_goat_horn");

    private static ResourceKey<Instrument> m_220150_(String p_220151_) {
        return ResourceKey.m_135785_(Registry.f_235737_, new ResourceLocation(p_220151_));
    }

    public static Instrument m_220148_(Registry<Instrument> p_220149_) {
        Registry.m_194579_(p_220149_, f_220139_, new Instrument((SoundEvent)SoundEvents.f_215702_.get(0), 140, 256.0f));
        Registry.m_194579_(p_220149_, f_220140_, new Instrument((SoundEvent)SoundEvents.f_215702_.get(1), 140, 256.0f));
        Registry.m_194579_(p_220149_, f_220141_, new Instrument((SoundEvent)SoundEvents.f_215702_.get(2), 140, 256.0f));
        Registry.m_194579_(p_220149_, f_220142_, new Instrument((SoundEvent)SoundEvents.f_215702_.get(3), 140, 256.0f));
        Registry.m_194579_(p_220149_, f_220143_, new Instrument((SoundEvent)SoundEvents.f_215702_.get(4), 140, 256.0f));
        Registry.m_194579_(p_220149_, f_220144_, new Instrument((SoundEvent)SoundEvents.f_215702_.get(5), 140, 256.0f));
        Registry.m_194579_(p_220149_, f_220145_, new Instrument((SoundEvent)SoundEvents.f_215702_.get(6), 140, 256.0f));
        return Registry.m_194579_(p_220149_, f_220146_, new Instrument((SoundEvent)SoundEvents.f_215702_.get(7), 140, 256.0f));
    }
}

