/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure;

public final class StructureCheckResult
extends Enum<StructureCheckResult> {
    public static final /* enum */ StructureCheckResult START_PRESENT = new StructureCheckResult();
    public static final /* enum */ StructureCheckResult START_NOT_PRESENT = new StructureCheckResult();
    public static final /* enum */ StructureCheckResult CHUNK_LOAD_NEEDED = new StructureCheckResult();
    private static final /* synthetic */ StructureCheckResult[] $VALUES;

    public static StructureCheckResult[] values() {
        return (StructureCheckResult[])$VALUES.clone();
    }

    public static StructureCheckResult valueOf(String p_197323_) {
        return Enum.valueOf(StructureCheckResult.class, p_197323_);
    }

    private static /* synthetic */ StructureCheckResult[] m_197321_() {
        return new StructureCheckResult[]{START_PRESENT, START_NOT_PRESENT, CHUNK_LOAD_NEEDED};
    }

    static {
        $VALUES = StructureCheckResult.m_197321_();
    }
}

