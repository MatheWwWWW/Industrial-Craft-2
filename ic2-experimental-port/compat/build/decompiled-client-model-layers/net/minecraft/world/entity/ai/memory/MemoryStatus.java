/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.memory;

public final class MemoryStatus
extends Enum<MemoryStatus> {
    public static final /* enum */ MemoryStatus VALUE_PRESENT = new MemoryStatus();
    public static final /* enum */ MemoryStatus VALUE_ABSENT = new MemoryStatus();
    public static final /* enum */ MemoryStatus REGISTERED = new MemoryStatus();
    private static final /* synthetic */ MemoryStatus[] $VALUES;

    public static MemoryStatus[] values() {
        return (MemoryStatus[])$VALUES.clone();
    }

    public static MemoryStatus valueOf(String p_26403_) {
        return Enum.valueOf(MemoryStatus.class, p_26403_);
    }

    private static /* synthetic */ MemoryStatus[] m_148207_() {
        return new MemoryStatus[]{VALUE_PRESENT, VALUE_ABSENT, REGISTERED};
    }

    static {
        $VALUES = MemoryStatus.m_148207_();
    }
}

