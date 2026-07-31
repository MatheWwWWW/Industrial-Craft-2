/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

public final class RenderShape
extends Enum<RenderShape> {
    public static final /* enum */ RenderShape INVISIBLE = new RenderShape();
    public static final /* enum */ RenderShape ENTITYBLOCK_ANIMATED = new RenderShape();
    public static final /* enum */ RenderShape MODEL = new RenderShape();
    private static final /* synthetic */ RenderShape[] $VALUES;

    public static RenderShape[] values() {
        return (RenderShape[])$VALUES.clone();
    }

    public static RenderShape valueOf(String p_55795_) {
        return Enum.valueOf(RenderShape.class, p_55795_);
    }

    private static /* synthetic */ RenderShape[] m_154329_() {
        return new RenderShape[]{INVISIBLE, ENTITYBLOCK_ANIMATED, MODEL};
    }

    static {
        $VALUES = RenderShape.m_154329_();
    }
}

