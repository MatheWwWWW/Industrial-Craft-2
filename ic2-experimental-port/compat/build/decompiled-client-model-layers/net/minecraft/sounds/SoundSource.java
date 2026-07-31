/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.sounds;

public final class SoundSource
extends Enum<SoundSource> {
    public static final /* enum */ SoundSource MASTER = new SoundSource("master");
    public static final /* enum */ SoundSource MUSIC = new SoundSource("music");
    public static final /* enum */ SoundSource RECORDS = new SoundSource("record");
    public static final /* enum */ SoundSource WEATHER = new SoundSource("weather");
    public static final /* enum */ SoundSource BLOCKS = new SoundSource("block");
    public static final /* enum */ SoundSource HOSTILE = new SoundSource("hostile");
    public static final /* enum */ SoundSource NEUTRAL = new SoundSource("neutral");
    public static final /* enum */ SoundSource PLAYERS = new SoundSource("player");
    public static final /* enum */ SoundSource AMBIENT = new SoundSource("ambient");
    public static final /* enum */ SoundSource VOICE = new SoundSource("voice");
    private final String f_12669_;
    private static final /* synthetic */ SoundSource[] $VALUES;

    public static SoundSource[] values() {
        return (SoundSource[])$VALUES.clone();
    }

    public static SoundSource valueOf(String p_12678_) {
        return Enum.valueOf(SoundSource.class, p_12678_);
    }

    private SoundSource(String p_12675_) {
        this.f_12669_ = p_12675_;
    }

    public String m_12676_() {
        return this.f_12669_;
    }

    private static /* synthetic */ SoundSource[] m_144247_() {
        return new SoundSource[]{MASTER, MUSIC, RECORDS, WEATHER, BLOCKS, HOSTILE, NEUTRAL, PLAYERS, AMBIENT, VOICE};
    }

    static {
        $VALUES = SoundSource.m_144247_();
    }
}

