/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

public final class ClickAction
extends Enum<ClickAction> {
    public static final /* enum */ ClickAction PRIMARY = new ClickAction();
    public static final /* enum */ ClickAction SECONDARY = new ClickAction();
    private static final /* synthetic */ ClickAction[] $VALUES;

    public static ClickAction[] values() {
        return (ClickAction[])$VALUES.clone();
    }

    public static ClickAction valueOf(String p_150521_) {
        return Enum.valueOf(ClickAction.class, p_150521_);
    }

    private static /* synthetic */ ClickAction[] m_150519_() {
        return new ClickAction[]{PRIMARY, SECONDARY};
    }

    static {
        $VALUES = ClickAction.m_150519_();
    }
}

