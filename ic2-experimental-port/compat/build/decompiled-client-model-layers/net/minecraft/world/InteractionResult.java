/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

public final class InteractionResult
extends Enum<InteractionResult> {
    public static final /* enum */ InteractionResult SUCCESS = new InteractionResult();
    public static final /* enum */ InteractionResult CONSUME = new InteractionResult();
    public static final /* enum */ InteractionResult CONSUME_PARTIAL = new InteractionResult();
    public static final /* enum */ InteractionResult PASS = new InteractionResult();
    public static final /* enum */ InteractionResult FAIL = new InteractionResult();
    private static final /* synthetic */ InteractionResult[] $VALUES;

    public static InteractionResult[] values() {
        return (InteractionResult[])$VALUES.clone();
    }

    public static InteractionResult valueOf(String p_19082_) {
        return Enum.valueOf(InteractionResult.class, p_19082_);
    }

    public boolean m_19077_() {
        return this == SUCCESS || this == CONSUME || this == CONSUME_PARTIAL;
    }

    public boolean m_19080_() {
        return this == SUCCESS;
    }

    public boolean m_146666_() {
        return this == SUCCESS || this == CONSUME;
    }

    public static InteractionResult m_19078_(boolean p_19079_) {
        return p_19079_ ? SUCCESS : CONSUME;
    }

    private static /* synthetic */ InteractionResult[] m_146667_() {
        return new InteractionResult[]{SUCCESS, CONSUME, CONSUME_PARTIAL, PASS, FAIL};
    }

    static {
        $VALUES = InteractionResult.m_146667_();
    }
}

