/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster.warden;

import java.util.Arrays;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public final class AngerLevel
extends Enum<AngerLevel> {
    public static final /* enum */ AngerLevel CALM = new AngerLevel(0, SoundEvents.f_215776_, SoundEvents.f_215764_);
    public static final /* enum */ AngerLevel AGITATED = new AngerLevel(40, SoundEvents.f_215775_, SoundEvents.f_215765_);
    public static final /* enum */ AngerLevel ANGRY = new AngerLevel(80, SoundEvents.f_215777_, SoundEvents.f_215765_);
    private static final AngerLevel[] f_219214_;
    private final int f_219215_;
    private final SoundEvent f_219216_;
    private final SoundEvent f_219217_;
    private static final /* synthetic */ AngerLevel[] $VALUES;

    public static AngerLevel[] values() {
        return (AngerLevel[])$VALUES.clone();
    }

    public static AngerLevel valueOf(String p_219239_) {
        return Enum.valueOf(AngerLevel.class, p_219239_);
    }

    private AngerLevel(int p_219223_, SoundEvent p_219224_, SoundEvent p_219225_) {
        this.f_219215_ = p_219223_;
        this.f_219216_ = p_219224_;
        this.f_219217_ = p_219225_;
    }

    public int m_219226_() {
        return this.f_219215_;
    }

    public SoundEvent m_219234_() {
        return this.f_219216_;
    }

    public SoundEvent m_219235_() {
        return this.f_219217_;
    }

    public static AngerLevel m_219227_(int p_219228_) {
        for (AngerLevel $$1 : f_219214_) {
            if (p_219228_ < $$1.f_219215_) continue;
            return $$1;
        }
        return CALM;
    }

    public boolean m_219236_() {
        return this == ANGRY;
    }

    private static /* synthetic */ AngerLevel[] m_219237_() {
        return new AngerLevel[]{CALM, AGITATED, ANGRY};
    }

    static {
        $VALUES = AngerLevel.m_219237_();
        f_219214_ = Util.m_137469_(AngerLevel.values(), p_219233_ -> Arrays.sort(p_219233_, (p_219230_, p_219231_) -> Integer.compare(p_219231_.f_219215_, p_219230_.f_219215_)));
    }
}

