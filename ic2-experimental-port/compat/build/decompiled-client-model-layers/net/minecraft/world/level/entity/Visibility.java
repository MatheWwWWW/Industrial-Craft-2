/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.entity;

import net.minecraft.server.level.ChunkHolder;

public final class Visibility
extends Enum<Visibility> {
    public static final /* enum */ Visibility HIDDEN = new Visibility(false, false);
    public static final /* enum */ Visibility TRACKED = new Visibility(true, false);
    public static final /* enum */ Visibility TICKING = new Visibility(true, true);
    private final boolean f_157682_;
    private final boolean f_157683_;
    private static final /* synthetic */ Visibility[] $VALUES;

    public static Visibility[] values() {
        return (Visibility[])$VALUES.clone();
    }

    public static Visibility valueOf(String p_157697_) {
        return Enum.valueOf(Visibility.class, p_157697_);
    }

    private Visibility(boolean p_157689_, boolean p_157690_) {
        this.f_157682_ = p_157689_;
        this.f_157683_ = p_157690_;
    }

    public boolean m_157691_() {
        return this.f_157683_;
    }

    public boolean m_157694_() {
        return this.f_157682_;
    }

    public static Visibility m_157692_(ChunkHolder.FullChunkStatus p_157693_) {
        if (p_157693_.m_140114_(ChunkHolder.FullChunkStatus.ENTITY_TICKING)) {
            return TICKING;
        }
        if (p_157693_.m_140114_(ChunkHolder.FullChunkStatus.BORDER)) {
            return TRACKED;
        }
        return HIDDEN;
    }

    private static /* synthetic */ Visibility[] m_157695_() {
        return new Visibility[]{HIDDEN, TRACKED, TICKING};
    }

    static {
        $VALUES = Visibility.m_157695_();
    }
}

