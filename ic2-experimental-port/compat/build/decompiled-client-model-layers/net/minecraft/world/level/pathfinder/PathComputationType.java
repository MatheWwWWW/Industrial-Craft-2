/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.pathfinder;

public final class PathComputationType
extends Enum<PathComputationType> {
    public static final /* enum */ PathComputationType LAND = new PathComputationType();
    public static final /* enum */ PathComputationType WATER = new PathComputationType();
    public static final /* enum */ PathComputationType AIR = new PathComputationType();
    private static final /* synthetic */ PathComputationType[] $VALUES;

    public static PathComputationType[] values() {
        return (PathComputationType[])$VALUES.clone();
    }

    public static PathComputationType valueOf(String p_77418_) {
        return Enum.valueOf(PathComputationType.class, p_77418_);
    }

    private static /* synthetic */ PathComputationType[] m_164713_() {
        return new PathComputationType[]{LAND, WATER, AIR};
    }

    static {
        $VALUES = PathComputationType.m_164713_();
    }
}

