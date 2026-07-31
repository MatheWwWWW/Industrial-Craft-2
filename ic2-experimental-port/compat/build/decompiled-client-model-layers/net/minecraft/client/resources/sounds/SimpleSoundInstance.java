/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class SimpleSoundInstance
extends AbstractSoundInstance {
    public SimpleSoundInstance(SoundEvent p_235109_, SoundSource p_235110_, float p_235111_, float p_235112_, RandomSource p_235113_, BlockPos p_235114_) {
        this(p_235109_, p_235110_, p_235111_, p_235112_, p_235113_, (double)p_235114_.m_123341_() + 0.5, (double)p_235114_.m_123342_() + 0.5, (double)p_235114_.m_123343_() + 0.5);
    }

    public static SimpleSoundInstance m_119752_(SoundEvent p_119753_, float p_119754_) {
        return SimpleSoundInstance.m_119755_(p_119753_, p_119754_, 0.25f);
    }

    public static SimpleSoundInstance m_119755_(SoundEvent p_119756_, float p_119757_, float p_119758_) {
        return new SimpleSoundInstance(p_119756_.m_11660_(), SoundSource.MASTER, p_119758_, p_119757_, SoundInstance.m_235150_(), false, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, true);
    }

    public static SimpleSoundInstance m_119745_(SoundEvent p_119746_) {
        return new SimpleSoundInstance(p_119746_.m_11660_(), SoundSource.MUSIC, 1.0f, 1.0f, SoundInstance.m_235150_(), false, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, true);
    }

    public static SimpleSoundInstance m_119747_(SoundEvent p_119748_, double p_119749_, double p_119750_, double p_119751_) {
        return new SimpleSoundInstance(p_119748_, SoundSource.RECORDS, 4.0f, 1.0f, SoundInstance.m_235150_(), false, 0, SoundInstance.Attenuation.LINEAR, p_119749_, p_119750_, p_119751_);
    }

    public static SimpleSoundInstance m_119766_(SoundEvent p_119767_, float p_119768_, float p_119769_) {
        return new SimpleSoundInstance(p_119767_.m_11660_(), SoundSource.AMBIENT, p_119769_, p_119768_, SoundInstance.m_235150_(), false, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, true);
    }

    public static SimpleSoundInstance m_119759_(SoundEvent p_119760_) {
        return SimpleSoundInstance.m_119766_(p_119760_, 1.0f, 1.0f);
    }

    public static SimpleSoundInstance m_235127_(SoundEvent p_235128_, RandomSource p_235129_, double p_235130_, double p_235131_, double p_235132_) {
        return new SimpleSoundInstance(p_235128_, SoundSource.AMBIENT, 1.0f, 1.0f, p_235129_, false, 0, SoundInstance.Attenuation.LINEAR, p_235130_, p_235131_, p_235132_);
    }

    public SimpleSoundInstance(SoundEvent p_235100_, SoundSource p_235101_, float p_235102_, float p_235103_, RandomSource p_235104_, double p_235105_, double p_235106_, double p_235107_) {
        this(p_235100_, p_235101_, p_235102_, p_235103_, p_235104_, false, 0, SoundInstance.Attenuation.LINEAR, p_235105_, p_235106_, p_235107_);
    }

    private SimpleSoundInstance(SoundEvent p_235116_, SoundSource p_235117_, float p_235118_, float p_235119_, RandomSource p_235120_, boolean p_235121_, int p_235122_, SoundInstance.Attenuation p_235123_, double p_235124_, double p_235125_, double p_235126_) {
        this(p_235116_.m_11660_(), p_235117_, p_235118_, p_235119_, p_235120_, p_235121_, p_235122_, p_235123_, p_235124_, p_235125_, p_235126_, false);
    }

    public SimpleSoundInstance(ResourceLocation p_235087_, SoundSource p_235088_, float p_235089_, float p_235090_, RandomSource p_235091_, boolean p_235092_, int p_235093_, SoundInstance.Attenuation p_235094_, double p_235095_, double p_235096_, double p_235097_, boolean p_235098_) {
        super(p_235087_, p_235088_, p_235091_);
        this.f_119573_ = p_235089_;
        this.f_119574_ = p_235090_;
        this.f_119575_ = p_235095_;
        this.f_119576_ = p_235096_;
        this.f_119577_ = p_235097_;
        this.f_119578_ = p_235092_;
        this.f_119579_ = p_235093_;
        this.f_119580_ = p_235094_;
        this.f_119582_ = p_235098_;
    }
}

