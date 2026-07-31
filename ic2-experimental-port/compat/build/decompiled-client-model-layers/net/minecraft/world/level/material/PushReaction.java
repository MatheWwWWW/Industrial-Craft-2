/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.material;

public final class PushReaction
extends Enum<PushReaction> {
    public static final /* enum */ PushReaction NORMAL = new PushReaction();
    public static final /* enum */ PushReaction DESTROY = new PushReaction();
    public static final /* enum */ PushReaction BLOCK = new PushReaction();
    public static final /* enum */ PushReaction IGNORE = new PushReaction();
    public static final /* enum */ PushReaction PUSH_ONLY = new PushReaction();
    private static final /* synthetic */ PushReaction[] $VALUES;

    public static PushReaction[] values() {
        return (PushReaction[])$VALUES.clone();
    }

    public static PushReaction valueOf(String p_76440_) {
        return Enum.valueOf(PushReaction.class, p_76440_);
    }

    private static /* synthetic */ PushReaction[] m_164537_() {
        return new PushReaction[]{NORMAL, DESTROY, BLOCK, IGNORE, PUSH_ONLY};
    }

    static {
        $VALUES = PushReaction.m_164537_();
    }
}

