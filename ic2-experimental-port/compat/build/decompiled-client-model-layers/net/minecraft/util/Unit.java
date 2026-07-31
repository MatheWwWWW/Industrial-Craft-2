/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

public final class Unit
extends Enum<Unit> {
    public static final /* enum */ Unit INSTANCE = new Unit();
    private static final /* synthetic */ Unit[] $VALUES;

    public static Unit[] values() {
        return (Unit[])$VALUES.clone();
    }

    public static Unit valueOf(String p_14468_) {
        return Enum.valueOf(Unit.class, p_14468_);
    }

    private static /* synthetic */ Unit[] m_145027_() {
        return new Unit[]{INSTANCE};
    }

    static {
        $VALUES = Unit.m_145027_();
    }
}

