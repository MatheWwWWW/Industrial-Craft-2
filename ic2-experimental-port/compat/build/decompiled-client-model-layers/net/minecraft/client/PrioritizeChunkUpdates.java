/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;

public final class PrioritizeChunkUpdates
extends Enum<PrioritizeChunkUpdates>
implements OptionEnum {
    public static final /* enum */ PrioritizeChunkUpdates NONE = new PrioritizeChunkUpdates(0, "options.prioritizeChunkUpdates.none");
    public static final /* enum */ PrioritizeChunkUpdates PLAYER_AFFECTED = new PrioritizeChunkUpdates(1, "options.prioritizeChunkUpdates.byPlayer");
    public static final /* enum */ PrioritizeChunkUpdates NEARBY = new PrioritizeChunkUpdates(2, "options.prioritizeChunkUpdates.nearby");
    private static final PrioritizeChunkUpdates[] f_193776_;
    private final int f_193777_;
    private final String f_193778_;
    private static final /* synthetic */ PrioritizeChunkUpdates[] $VALUES;

    public static PrioritizeChunkUpdates[] values() {
        return (PrioritizeChunkUpdates[])$VALUES.clone();
    }

    public static PrioritizeChunkUpdates valueOf(String p_193794_) {
        return Enum.valueOf(PrioritizeChunkUpdates.class, p_193794_);
    }

    private PrioritizeChunkUpdates(int p_193784_, String p_193785_) {
        this.f_193777_ = p_193784_;
        this.f_193778_ = p_193785_;
    }

    @Override
    public int m_35965_() {
        return this.f_193777_;
    }

    @Override
    public String m_35968_() {
        return this.f_193778_;
    }

    public static PrioritizeChunkUpdates m_193787_(int p_193788_) {
        return f_193776_[Mth.m_14100_(p_193788_, f_193776_.length)];
    }

    private static /* synthetic */ PrioritizeChunkUpdates[] m_193792_() {
        return new PrioritizeChunkUpdates[]{NONE, PLAYER_AFFECTED, NEARBY};
    }

    static {
        $VALUES = PrioritizeChunkUpdates.m_193792_();
        f_193776_ = (PrioritizeChunkUpdates[])Arrays.stream(PrioritizeChunkUpdates.values()).sorted(Comparator.comparingInt(PrioritizeChunkUpdates::m_35965_)).toArray(PrioritizeChunkUpdates[]::new);
    }
}

